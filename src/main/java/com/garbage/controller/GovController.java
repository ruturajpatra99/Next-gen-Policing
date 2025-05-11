package com.garbage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.garbage.model.Government;
import com.garbage.model.WorkAssign;
import com.garbage.service.GovernmentService;
import com.garbage.service.WorkAssignService;

@Controller
public class GovController {
    @Autowired
    private GovernmentService governmentService;

    @Autowired
    private WorkAssignService workAssignService;

    // Show login page
    @GetMapping("/government-login")
    public String showLoginPage() {
        return "government-login";  // The login page
    }

    // Handle login action
    @PostMapping("/loging")
    public String login(@RequestParam Integer id, @RequestParam String password, Model model) {
        Government government = governmentService.validateGovernment(id, password);

        if (government != null) {
            // Fetch work assignments after successful login
            List<WorkAssign> workAssigns = workAssignService.getAllWorkAssignments();
            model.addAttribute("workAssigns", workAssigns);
            return "workassign-table"; // Thymeleaf page showing work assignments
        } else {
            model.addAttribute("error", "Invalid credentials.");
            return "government-login"; // Stay on login page if invalid
        }
    }
}
