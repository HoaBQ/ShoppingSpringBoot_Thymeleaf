package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.entity.User;
import com.shopping.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.findByUsername(username);
        // TODO: đang so sánh mật khẩu dạng plaintext để test nhanh.
        // Khi lên production, đổi lại thành passwordEncoder.matches(password, user.getPassword())
        // và đảm bảo mật khẩu trong DB đã được mã hoá bằng BCrypt.
        if (user != null && user.getStatus() == 1 && password.equals(user.getPassword())) {
            if (user.getRole() != 1) {
                model.addAttribute("error", "Tài khoản không có quyền truy cập trang quản trị!");
                return "login";
            }
            session.setAttribute("loggedUser", user);
            return "redirect:/admin/category/list";
        }
        model.addAttribute("error", "Sai thông tin hoặc tài khoản bị khóa!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}