package com.example.is_curse_work.controller;

import com.example.is_curse_work.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notifications")
public class NotificationWebController {

    private final NotificationService notificationService;

    public NotificationWebController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/run")
    public String run(@RequestParam("fridgeId") Long fridgeId, @RequestParam("days") int days) {
        notificationService.runExpiryBatch(fridgeId, days);
        return "redirect:/dashboard";
    }
}
