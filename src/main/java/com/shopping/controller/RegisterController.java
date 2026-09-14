package com.shopping.controller;
import com.shopping.entity.User;
import com.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {
    @Autowired private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) { model.addAttribute("user", new User()); return "register"; }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        user.setRole(0); 
        user.setStatus(1);
        try {
            userService.save(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}