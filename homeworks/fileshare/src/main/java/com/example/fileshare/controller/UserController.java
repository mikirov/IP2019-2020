package com.example.fileshare.controller;


import com.example.fileshare.service.UserService;
import com.example.fileshare.util.OnRegistrationCompleteEvent;
import com.example.fileshare.util.UserValidator;
import com.example.fileshare.model.User;
import com.example.fileshare.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult,
                               HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        String appUrl = request.getContextPath();

        userService.save(userForm);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userForm, appUrl));

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}