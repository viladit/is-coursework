package com.example.is_curse_work.controller;

import com.example.is_curse_work.security.CustomUserDetails;
import com.example.is_curse_work.service.FridgeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fridges")
public class FridgeWebController {

    private final FridgeService fridgeService;

    public FridgeWebController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("fridges", fridgeService.listFridges());
        return "fridges/list";
    }

    @PostMapping("/{id}/join")
    public String join(@AuthenticationPrincipal CustomUserDetails me, @PathVariable("id") Long id) {
        fridgeService.joinFridge(id, me.getUserId());
        return "redirect:/fridges/" + id + "/map";
    }

    @GetMapping("/{id}/join")
    public String joinGet(@AuthenticationPrincipal CustomUserDetails me, @PathVariable("id") Long id) {
        fridgeService.joinFridge(id, me.getUserId());
        return "redirect:/fridges/" + id + "/map";
    }

    @GetMapping("/{id}/map")
    public String map(@PathVariable("id") Long id, Model model) {
        model.addAttribute("map", fridgeService.getMap(id));
        model.addAttribute("fridgeId", id);
        return "fridges/map";
    }

    @GetMapping("/{id}/members")
    public String members(@PathVariable("id") Long id, Model model) {
        model.addAttribute("members", fridgeService.listMembers(id));
        model.addAttribute("fridgeId", id);
        return "fridges/members";
    }

    @PostMapping("/{id}/members/{userId}/remove")
    public String removeMember(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        fridgeService.removeMember(id, userId);
        return "redirect:/fridges/" + id + "/members";
    }
}
