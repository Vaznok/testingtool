package com.epam.rd.data.access.domain;

import java.util.NoSuchElementException;

public enum UserRoles {
    ADMIN, USER;

    public static UserRoles of(String name){
        for (UserRoles userRole: UserRoles.values()){
            if (userRole.name().equals(name)){
                return userRole;
            }
        }
        throw new NoSuchElementException("UserRole with name "+ name + " not found");
    }
}
