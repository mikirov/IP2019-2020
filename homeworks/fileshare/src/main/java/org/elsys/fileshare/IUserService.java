package org.elsys.fileshare;

public interface IUserService {
    User registerNewUserAccount(User accountDto)
            throws EmailExistsException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}