package com.epam.rd.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an authenticated user. The id of the user will be used as the
 * "name of the entity" for javax.security.Principal.
 * 
 * @author Richard_Pal
 */
public class AuthenticatedPrincipal implements Principal, Serializable {

  private static final long serialVersionUID = -5869599039156736247L;

  private String userId;
  private String email;
  private String displayName;

  private Map<String, Object> userDetails = new HashMap<>();

  @Override
  public String getName() {
    return userId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Map<String, Object> getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(Map<String, Object> userDetails) {
    this.email = (String) userDetails.get("email");
    this.userId = (String) userDetails.get("pmc_id");
    this.displayName = (String) userDetails.get("commonname");
    this.userDetails = userDetails;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((userDetails == null) ? 0 : userDetails.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AuthenticatedPrincipal other = (AuthenticatedPrincipal) obj;
    if (displayName == null) {
      if (other.displayName != null)
        return false;
    } else if (!displayName.equals(other.displayName))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (userDetails == null) {
      if (other.userDetails != null)
        return false;
    } else if (!userDetails.equals(other.userDetails))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AuthenticatedPrincipal [userId=" + userId + ", email=" + email + ", displayName=" + displayName
        + ", userDetails=" + userDetails + "]";
  }

}
