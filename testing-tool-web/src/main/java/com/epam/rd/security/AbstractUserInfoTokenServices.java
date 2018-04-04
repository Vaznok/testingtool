package com.epam.rd.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.List;
import java.util.Map;

public abstract class AbstractUserInfoTokenServices implements ResourceServerTokenServices {
  protected final Log logger = LogFactory.getLog(getClass());

  /**
   * Describes the Authentication client and the related provider configuration.
   */
  protected Oauth2ClientResources oauth2ClientResources;

  public AbstractUserInfoTokenServices(Oauth2ClientResources oauth2ClientResources) {
    this.oauth2ClientResources = oauth2ClientResources;
  }

  @Override
  public OAuth2Authentication loadAuthentication(String accessToken)
      throws AuthenticationException, InvalidTokenException {

    final Map<String, Object> principalDetailsMap = getPrincipalDetails(accessToken);
    final AuthenticatedPrincipal principal = buildAuthenticatedPrincipalFrom(principalDetailsMap);
    final List<GrantedAuthority> grantedAuthorities = determineGrantedAuthoritiesFor(principal);
    final OAuth2Request request = new OAuth2Request(null, oauth2ClientResources.getClient().getClientId(), null, true,
        null, null, null, null, null);
    final UsernamePasswordAuthenticationToken finalToken = new UsernamePasswordAuthenticationToken(principal, "N/A",
        grantedAuthorities);
    logger.debug("finalToken: " + finalToken);

    return new OAuth2Authentication(request, finalToken);

  }

  protected abstract Map<String, Object> getPrincipalDetails(String accessToken);

  @Override
  public OAuth2AccessToken readAccessToken(String accessToken) {
    throw new UnsupportedOperationException("Not supported: read access token");
  }

  /**
   * Skeleton implementation for building Principal object. In subclassed
   * implementations the provided user details map should be evaluated and the
   * AuthenticatedPrincipal object's fields must be filled properly.
   * 
   * @param principalDetailsMap
   * @return
   */
  protected AuthenticatedPrincipal buildAuthenticatedPrincipalFrom(Map<String, Object> principalDetailsMap) {

    final AuthenticatedPrincipal principal = new AuthenticatedPrincipal();
    principal.setUserDetails(principalDetailsMap);

    return principal;
  }

  protected List<GrantedAuthority> determineGrantedAuthoritiesFor(AuthenticatedPrincipal principal) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
    return grantedAuthorities;
  }

  protected String extractAndAssertMandatoryUserDetailValue(String tokenAttributeName, Map<String, Object> map) {
    Object value = map.get(tokenAttributeName);
    String v = null;
    if (value instanceof String) {
      v = (String) map.get(tokenAttributeName);
      assertMandatoryAttributeValueExists(v, tokenAttributeName);
    } else if (value instanceof Long || value instanceof Integer) {
      // support GitHub userId (as Integer)
      v = value.toString();
    } else {
      throw new MissingMandatoryTokenAttributeException(tokenAttributeName + " found object: " + value);
    }
    return v;
  }

  private void assertMandatoryAttributeValueExists(Object tokenValue, String tokenAttributeName) {
    if (tokenValue == null) {
      throw new MissingMandatoryTokenAttributeException(tokenAttributeName);
    }
  }

  /**
   * Builds an internal id of the authenticated principal, by using the provided
   * and the name of the authentication provider.
   */
  protected String buildPrincipalInternalId(String externalId) {
    String oauthProviderName = oauth2ClientResources.getClient().getId();
    return oauthProviderName + ":" + externalId;
  }

  class MissingMandatoryTokenAttributeException extends RuntimeException {
    private static final long serialVersionUID = -7117868490802333273L;

    public MissingMandatoryTokenAttributeException(String attributeName) {
      super("Missing User Details attribute: " + attributeName);
    }
  }
}
