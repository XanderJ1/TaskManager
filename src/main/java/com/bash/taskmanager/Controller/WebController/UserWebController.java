package com.bash.taskmanager.Controller.WebController;

import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;

@Slf4j
@RequestMapping("/")
@Controller
public class UserWebController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/signUp")
    public String signUp(Model model){
        return "signUp";
    }
    @PostMapping("/signUp")
    public String createUser(@ModelAttribute("user")RegistrationDTO registrationDTO, BindingResult result){
        if (result.hasErrors()){
            return "signUp";
        }
        userService.addUser(registrationDTO);
        return "redirect:/success";
    }

}
