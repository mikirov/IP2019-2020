package org.elsys.fileshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(User accountDto)
            throws EmailExistsException {

        if (emailExists(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address:"  + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));
        return repository.save(user);
    }
    private boolean emailExists(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
}