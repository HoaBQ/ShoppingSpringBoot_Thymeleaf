package com.shopping.controller;

import com.shopping.entity.Category;
import com.shopping.service.CategoryService;
import jakarta.validation.Valid;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin/category")
public class ThymeleafCategoryController {
    private static final int PAGE_SIZE = 5;

    @Autowired
    private CategoryService categoryService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "1") int page,
                       Model model) {
        var pageData = categoryService.searchAndPaginate(keyword, page, PAGE_SIZE);
        model.addAttribute("categories", pageData.getContent());
        model.addAttribute("categoryPage", pageData);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageData.getNumber() + 1);
        return "category/list";
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("formTitle", "Thêm danh mục");
        return "category/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy danh mục.");
            return "redirect:/admin/category/list";
        }
        model.addAttribute("category", category);
        model.addAttribute("formTitle", "Chỉnh sửa danh mục");
        return "category/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult bindingResult,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "existingImage", required = false) String existingImage,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", category.getId() == 0 ? "Thêm danh mục" : "Chỉnh sửa danh mục");
            return "category/form";
        }
        try {
            String imageName = saveImage(imageFile);
            category.setImage(imageName != null ? imageName : existingImage);
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("success", "Lưu danh mục thành công.");
            return "redirect:/admin/category/list";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("formTitle", category.getId() == 0 ? "Thêm danh mục" : "Chỉnh sửa danh mục");
            return "category/form";
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IOException("Tệp tải lên phải là hình ảnh.");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IOException("Hình ảnh vượt quá 10MB. Vui lòng chọn hình ảnh khác nhẹ hơn.");
        }
        Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(directory);
        String originalName = Paths.get(file.getOriginalFilename() == null ? "image" : file.getOriginalFilename())
                .getFileName().toString();
        String fileName = UUID.randomUUID() + "_" + originalName;
        Files.copy(file.getInputStream(), directory.resolve(fileName));
        return fileName;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa danh mục thành công.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa danh mục: " + ex.getMessage());
        }
        return "redirect:/admin/category/list";
    }
}
