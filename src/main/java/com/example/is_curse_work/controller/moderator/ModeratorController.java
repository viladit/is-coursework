package com.example.is_curse_work.controller.moderator;

import com.example.is_curse_work.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private final ReportService reports;

    public ModeratorController(ReportService reports) {
        this.reports = reports;
    }

    @GetMapping
    public String index() {
        return "moderator/index";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("summary", reports.buildSummary());
        return "moderator/reports";
    }
}
