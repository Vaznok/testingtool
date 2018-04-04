package com.epam.rd.security;

import com.epam.rd.context.UserContext;
import com.epam.rd.data.access.domain.Permission;
import com.epam.rd.data.access.domain.User;
import com.epam.rd.data.access.domain.UserRole;
import com.epam.rd.data.access.domain.UserRoles;
import com.epam.rd.data.access.repository.AuthorityRepository;
import com.epam.rd.dto.TokenRoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.services.dao.UserService;
import com.epam.rd.services.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserContext userContext;
    /*@Autowired
    private MailService mailService;*/
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public TokenRoleDto login(final String email, final String password) throws IllegalArgumentException {
        LOGGER.debug("Tray to login user with email " + email);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " do not found!");
        }
        UserRole userRole = user.getUserRole();
        if (userRole == null){
            throw new IllegalArgumentException("User with email " + email + " do not have role!");
        }
        Set<Permission> permissions = userRole.getPermissions();
        if (permissions == null){
            throw new IllegalArgumentException("User with email " + email + " do not have permissions!");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        if (encoder.matches(password, userDetails.getPassword()))  {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            LOGGER.debug(String.format("Successfully %s auto logged in", email));
            TokenRoleDto tokenRoleDto = new TokenRoleDto();
            tokenRoleDto.setToken(tokenService.generateNewToken());
            tokenService.store(tokenRoleDto.getToken(), authenticationToken);
            tokenRoleDto.setUserRole(getUserPermission(email));
            return tokenRoleDto;
        }
        throw new IllegalArgumentException("User with email " + email + " not authenticated");
    }

    @Override
    public void registration(UserDto userDto) {

    }

    @Override
    public void logout(String token) {
        LOGGER.debug("User logout");
        tokenService.deleteToken(token);
        SecurityContextHolder.clearContext();
    }

    private String getUserPermission(String email) {
        Set<Permission> userRoles = userService.findUserByEmail(email).getUserRole().getPermissions();
        if (userRoles.isEmpty()) {
            throw new IllegalAccessError("User with email " + email + "not have role!");
        }
        return userRoles.iterator().next().getPermission();
    }



    private User registerUser(String username) {

        String oneTimePassword = generatePassword();

        User user = new User();
        user.setEmail(username);
        user.setPassword(username/*oneTimePassword*/);
        user.setEnabled(true);

        LOGGER.info("Creating user account");

        Long userId = userService.saveUserWithRole(user, UserRoles.USER);
        user.setId(userId);

        return user;
    }

    private static String generatePassword() {
        return new SimplePasswordGenerator(8).generatePassword();
    }

    /*private void sendEmail(String recipientEmail, String otpPassword) {
        LOGGER.info("Sending email to {} with generated password", recipientEmail);
        mailService.sendMailTo(recipientEmail, "Welcome to TestingTool!",
                "Dear User ,\n Your new password is: " + otpPassword +
                        ".\n Please change it at first login!");
    }*/
}
