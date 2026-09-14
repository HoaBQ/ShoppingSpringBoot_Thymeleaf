package com.shopping.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.entity.User;
import com.shopping.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/legacy/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        var pageData = userService.searchAndPaginate(keyword, page, 5);
        model.addAttribute("users", pageData.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("keyword", keyword);
        return render(model, "list-user.jsp", "Người dùng");
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return render(model, "add-user.jsp", "Form người dùng");
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return render(model, "add-user.jsp", "Form người dùng");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       RedirectAttributes ra,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return render(model, "add-user.jsp", "Form người dùng");
        }

        try {
            userService.save(user);
            ra.addFlashAttribute("success", "Lưu người dùng thành công!");
            return "redirect:/admin/user/list";
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());
            return render(model, "add-user.jsp", "Form người dùng");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes ra) {
        try {
            userService.delete(id);
            ra.addFlashAttribute("success", "Xóa người dùng thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/user/list";
    }

    private String render(Model model, String view, String title) {
        model.addAttribute("contentView", "/WEB-INF/views/admin/" + view);
        model.addAttribute("pageTitle", title);
        return "admin-layout";
    }
}