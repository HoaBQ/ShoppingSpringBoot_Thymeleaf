package com.shopping.controller;

import com.shopping.entity.Product;
import com.shopping.service.CategoryService;
import com.shopping.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
public class ThymeleafProductController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;
    @Value("${app.upload.dir}") private String uploadDir;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "1") int page, Model model) {
        var pageData = productService.searchAndPaginate(keyword, page, 5);
        model.addAttribute("products", pageData.getContent());
        model.addAttribute("productPage", pageData);
        model.addAttribute("currentPage", pageData.getNumber() + 1);
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        prepareForm(model, new Product(), "Thêm sản phẩm");
        return "product/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        Product product = productService.findById(id);
        if (product == null) {
            ra.addFlashAttribute("error", "Không tìm thấy sản phẩm.");
            return "redirect:/admin/product/list";
        }
        prepareForm(model, product, "Chỉnh sửa sản phẩm");
        return "product/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product,
                       BindingResult result,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "existingImage", required = false) String existingImage,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            prepareForm(model, product, product.getId() == 0 ? "Thêm sản phẩm" : "Chỉnh sửa sản phẩm");
            return "product/form";
        }
        try {
            String imageName = saveImage(imageFile);
            product.setImage(imageName != null ? imageName : existingImage);
            productService.save(product);
            ra.addFlashAttribute("success", "Lưu sản phẩm thành công.");
            return "redirect:/admin/product/list";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            prepareForm(model, product, product.getId() == 0 ? "Thêm sản phẩm" : "Chỉnh sửa sản phẩm");
            return "product/form";
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
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
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            productService.delete(id);
            ra.addFlashAttribute("success", "Xóa sản phẩm thành công.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Không thể xóa sản phẩm: " + ex.getMessage());
        }
        return "redirect:/admin/product/list";
    }

    private void prepareForm(Model model, Product product, String title) {
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.searchAndPaginate("", 1, 100).getContent());
        model.addAttribute("formTitle", title);
    }
}
