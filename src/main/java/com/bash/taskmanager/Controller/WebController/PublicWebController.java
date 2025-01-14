package com.bash.taskmanager.Controller.WebController;

import com.bash.taskmanager.Data.DTO.LoginDTO;
import com.bash.taskmanager.Data.DTO.LoginResponseDTO;
import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class PublicWebController {

    AuthenticationService authenticationService;

    public PublicWebController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint to display the sign-up page.
     * @param model model to hold data to be passed to the web view.
     * @return return name of the view to be displayed
     */
    @GetMapping("/signUp")
    public String signUp(Model model){
        return "signUp";
    }

    /**
     * Endpoint to register new users.
     * @param body Registration DTO that contains username, password, and an optional email
     * @return a redirect to success page.
     */
    @PostMapping("/signUp")
    public String createUser(@ModelAttribute RegistrationDTO body){
        authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail());
        return "redirect:/web/success";
    }

    /**
     * Endpoint to display a page when a user registers.
     * @return success view
     */
    @GetMapping("/success")
    public String success(){
        return "success";
    }

    /**
     * Endpoint to display a login page.
     * @param model model to hold data to be passed to the web view.
     * @return
     */
    @GetMapping("/signIn")
    public String signIn(Model model){
        return "signIn";
    }

    /**
     * Endpoint handle login requests.
     * @param body a login DTO
     * @param model model to hold data to be passed to the web view.
     * @return renders tokenDisplay page which contains the users JWT token
     */
    @PostMapping("/signIn")
    public String logIn(@ModelAttribute LoginDTO body, Model model){
        LoginResponseDTO loginResponse = authenticationService.
                loginUser(body.getUsername(), body.getPassword());

        model.addAttribute("token", loginResponse.getJwt() + "none");
        return "tokenDisplay";
    }
}
