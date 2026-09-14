package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/thymeleaf/categories")
public class ThymeleafLegacyRedirectController {

    @GetMapping({"", "/", "/new", "/{id}/edit"})
    public RedirectView redirectToCategories() {
        return new RedirectView("/admin/category/list", true);
    }
}
