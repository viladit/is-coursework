package com.example.is_curse_work.controller;

import com.example.is_curse_work.security.CustomUserDetails;
import com.example.is_curse_work.service.FridgeService;
import com.example.is_curse_work.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ProductService productService;
    private final FridgeService fridgeService;

    public DashboardController(ProductService productService, FridgeService fridgeService) {
        this.productService = productService;
        this.fridgeService = fridgeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails me, Model model) {
        model.addAttribute("myProducts", productService.getMyProducts(me.getUserId()));
        model.addAttribute("fridges", fridgeService.listFridges());
        return "dashboard";
    }
}

