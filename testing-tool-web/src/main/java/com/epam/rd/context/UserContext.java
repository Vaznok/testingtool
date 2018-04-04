package com.epam.rd.context;

import com.epam.rd.data.access.domain.User;

public interface UserContext {

    String getCurrentUserEmail();

    void setCurrentUser(User user);

}
