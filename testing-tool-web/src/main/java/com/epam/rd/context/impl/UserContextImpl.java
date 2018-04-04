package com.epam.rd.context.impl;

import com.epam.rd.context.UserContext;
import com.epam.rd.data.access.domain.User;

import com.epam.rd.data.access.domain.UserRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("userContext")
public class UserContextImpl implements UserContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContextImpl.class);

    @Override
    public void setCurrentUser(User user) {
        if (null == user) {
            LOGGER.info("User is null. Clearing security context...");
            SecurityContextHolder.clearContext();
            return;
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRoles.USER.name()));

        String login = user.getEmail();
        String password = user.getPassword();
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, password, authorities);
        LOGGER.info("Saving user info in security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        return null;
    }
}
