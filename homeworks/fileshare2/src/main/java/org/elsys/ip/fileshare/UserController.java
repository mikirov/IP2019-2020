package org.elsys.ip.fileshare;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/register")
    public void registerUser(@RequestBody MultiValueMap<String, String> formParams){
        System.out.println("form params received:" + formParams);
        Users user = new Users();
        user.setName(formParams.getFirst("name"));
        user.setPassword(formParams.getFirst("password"));
        user.setEnabled(true); //TODO: make email verification
        usersRepository.save(user);
    }
}
