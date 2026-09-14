package com.shopping.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.entity.Category;
import com.shopping.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/legacy/admin/category")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        var pageData = categoryService.searchAndPaginate(keyword, page, 5);
        model.addAttribute("categories", pageData.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("keyword", keyword);
        return render(model, "list-category.jsp", "Danh mục");
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return render(model, "add-category.jsp", "Form danh mục");
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return render(model, "add-category.jsp", "Form danh mục");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult bindingResult,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "existingImage", required = false) String existingImage,
                       RedirectAttributes ra,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return render(model, "add-category.jsp", "Form danh mục");
        }

        try {
            String savedFileName = saveImageIfPresent(imageFile);
            category.setImage(savedFileName != null ? savedFileName : existingImage);
            categoryService.save(category);
            ra.addFlashAttribute("success", "Lưu danh mục thành công!");
            return "redirect:/admin/category/list";
        } catch (Exception e) {
            model.addAttribute("category", category);
            model.addAttribute("error", e.getMessage());
            return render(model, "add-category.jsp", "Form danh mục");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes ra) {
        try {
            categoryService.delete(id);
            ra.addFlashAttribute("success", "Xóa danh mục thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/category/list";
    }

    private String saveImageIfPresent(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String original = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String fileName = UUID.randomUUID() + "_" + original;
        Path target = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), target);
        return fileName;
    }

    private String render(Model model, String view, String title) {
        model.addAttribute("contentView", "/WEB-INF/views/admin/" + view);
        model.addAttribute("pageTitle", title);
        return "admin-layout";
    }
}
