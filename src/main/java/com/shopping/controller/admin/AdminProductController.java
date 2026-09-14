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

import com.shopping.entity.Product;
import com.shopping.service.CategoryService;
import com.shopping.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/legacy/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        var pageData = productService.searchAndPaginate(keyword, page, 5);
        model.addAttribute("products", pageData.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("keyword", keyword);
        return render(model, "list-product.jsp", "Sản phẩm");
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.searchAndPaginate("", 1, 100).getContent());
        return render(model, "add-product.jsp", "Form sản phẩm");
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.searchAndPaginate("", 1, 100).getContent());
        return render(model, "add-product.jsp", "Form sản phẩm");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.searchAndPaginate("", 1, 100).getContent());
            return render(model, "add-product.jsp", "Form sản phẩm");
        }

        try {
            productService.save(product);
            ra.addFlashAttribute("success", "Lưu sản phẩm thành công!");
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryService.searchAndPaginate("", 1, 100).getContent());
            model.addAttribute("product", product);
            return render(model, "add-product.jsp", "Form sản phẩm");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes ra) {
        try {
            productService.delete(id);
            ra.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/product/list";
    }

    private String render(Model model, String view, String title) {
        model.addAttribute("contentView", "/WEB-INF/views/admin/" + view);
        model.addAttribute("pageTitle", title);
        return "admin-layout";
    }
}