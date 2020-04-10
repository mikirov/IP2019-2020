package org.elsys.fileshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

public class RegistrationController {

    @Autowired
    private UserService service;

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @GetMapping("/user/login")
    public String showLoginForm(WebRequest request, Model model){
        return  "login";

    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid User accountDto,
            BindingResult result,
            WebRequest request,
            Errors errors) {

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        }
        else {
            return new ModelAndView("successRegister", "user", accountDto);
        }
    }
    private User createUserAccount(User accountDto, BindingResult result) {
        User registered = null;
        try {
            registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
//        catch (UserAlreadyExistException uaeEx) {
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
//            return null;
//        }

        return registered;
    }
}
