package com.example.is_curse_work.controller;

import com.example.is_curse_work.dto.*;
import com.example.is_curse_work.repository.CategoryRepository;
import com.example.is_curse_work.repository.ZoneRepository;
import com.example.is_curse_work.security.CustomUserDetails;
import com.example.is_curse_work.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductWebController {

    private final ProductService productService;
    private final CategoryRepository categories;
    private final ZoneRepository zones;

    public ProductWebController(ProductService productService, CategoryRepository categories, ZoneRepository zones) {
        this.productService = productService;
        this.categories = categories;
        this.zones = zones;
    }

    @GetMapping("/my")
    public String my(@AuthenticationPrincipal CustomUserDetails me, Model model) {
        model.addAttribute("products", productService.getMyProducts(me.getUserId()));
        return "products/my";
    }

    @GetMapping("/add")
    public String addForm(@RequestParam(name = "fridgeId", required = false) Long fridgeId, Model model) {
        model.addAttribute("form", new AddProductForm());
        model.addAttribute("categories", categories.findAll());
        if (fridgeId != null) model.addAttribute("zones", zones.findByFridge_FridgeIdOrderBySortOrderAsc(fridgeId));
        model.addAttribute("fridgeId", fridgeId);
        return "products/add";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal CustomUserDetails me,
                      @Valid @ModelAttribute("form") AddProductForm form,
                      BindingResult br,
                      @RequestParam(name = "fridgeId", required = false) Long fridgeId,
                      Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categories.findAll());
            if (fridgeId != null) model.addAttribute("zones", zones.findByFridge_FridgeIdOrderBySortOrderAsc(fridgeId));
            model.addAttribute("fridgeId", fridgeId);
            return "products/add";
        }
        try {
            productService.addProduct(me.getUserId(), form);
            return "redirect:/products/my";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categories.findAll());
            if (fridgeId != null) model.addAttribute("zones", zones.findByFridge_FridgeIdOrderBySortOrderAsc(fridgeId));
            model.addAttribute("fridgeId", fridgeId);
            return "products/add";
        }
    }

    @GetMapping("/{id}")
    public String detail(@AuthenticationPrincipal CustomUserDetails me, @PathVariable Long id, Model model) {
        model.addAttribute("p", productService.getProductDetail(me.getUserId(), id));
        model.addAttribute("moveForm", new MoveProductForm());
        model.addAttribute("extendForm", new ExtendProductForm());
        model.addAttribute("statusForm", new SetStatusForm());
        return "products/detail";
    }

    @PostMapping("/{id}/move")
    public String move(@AuthenticationPrincipal CustomUserDetails me, @PathVariable Long id,
                       @Valid @ModelAttribute("moveForm") MoveProductForm form, BindingResult br) {
        if (br.hasErrors()) return "redirect:/products/" + id;
        productService.move(me.getUserId(), id, form);
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/extend")
    public String extend(@AuthenticationPrincipal CustomUserDetails me, @PathVariable Long id,
                         @Valid @ModelAttribute("extendForm") ExtendProductForm form, BindingResult br) {
        if (br.hasErrors()) return "redirect:/products/" + id;
        productService.extend(me.getUserId(), id, form);
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/status")
    public String status(@AuthenticationPrincipal CustomUserDetails me, @PathVariable Long id,
                         @Valid @ModelAttribute("statusForm") SetStatusForm form, BindingResult br) {
        if (br.hasErrors()) return "redirect:/products/" + id;
        productService.setStatus(me.getUserId(), id, form);
        return "redirect:/products/" + id;
    }
}

