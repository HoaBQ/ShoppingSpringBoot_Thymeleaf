package com.shopping.controller;

import com.shopping.entity.User;
import com.shopping.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/user")
public class ThymeleafUserController {
    @Autowired private UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "1") int page, Model model) {
        var pageData = userService.searchAndPaginate(keyword, page, 5);
        model.addAttribute("users", pageData.getContent());
        model.addAttribute("userPage", pageData);
        model.addAttribute("currentPage", pageData.getNumber() + 1);
        model.addAttribute("keyword", keyword);
        return "user/list";
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("formTitle", "Thêm người dùng");
        return "user/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        User user = userService.findById(id);
        if (user == null) {
            ra.addFlashAttribute("error", "Không tìm thấy người dùng.");
            return "redirect:/admin/user/list";
        }
        model.addAttribute("user", user);
        model.addAttribute("formTitle", "Chỉnh sửa người dùng");
        return "user/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", user.getId() == 0 ? "Thêm người dùng" : "Chỉnh sửa người dùng");
            return "user/form";
        }
        try {
            userService.save(user);
            ra.addFlashAttribute("success", "Lưu người dùng thành công.");
            return "redirect:/admin/user/list";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("formTitle", user.getId() == 0 ? "Thêm người dùng" : "Chỉnh sửa người dùng");
            return "user/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            userService.delete(id);
            ra.addFlashAttribute("success", "Xóa người dùng thành công.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Không thể xóa người dùng: " + ex.getMessage());
        }
        return "redirect:/admin/user/list";
    }
}
