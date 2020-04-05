package org.elsys.fileshare;

public interface IUserService {
    User registerNewUserAccount(User accountDto)
            throws EmailExistsException;
}