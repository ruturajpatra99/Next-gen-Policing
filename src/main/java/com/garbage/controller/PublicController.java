package com.garbage.controller;

import com.garbage.model.Public;
import com.garbage.model.Request;
import com.garbage.model.WorkAssign;
import com.garbage.repository.PublicRepository;
import com.garbage.repository.RequestRepository;
import com.garbage.repository.WorkAssignRepository;
import com.garbage.service.PublicService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicController {

    @Autowired
    private PublicRepository publicRepository;

    @GetMapping("/public_signup")
    public String showSignupPage(Model model) {
        model.addAttribute("public", new Public());
        return "public_signup";
    }

    @PostMapping("/public_signup")
    public String handleSignup(@ModelAttribute("public") Public publicUser) {
        // Save the user to the database
        publicRepository.save(publicUser);
        return "redirect:/public_login?success=true"; // Redirect to login page with a success message
    }

    @GetMapping("/public_login")
    public String showPublicLoginPage() {
        return "public_login"; // The name of the HTML file in the templates folder
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("emailid") String emailid,
            @RequestParam("password") String password,
            Model model) {

        Optional<Public> publicUser = publicRepository.findByemailidAndPassword(emailid, password);
        if (publicUser.isPresent()) {
            Public user = publicUser.get();

            // Add user details to the model for the dashboard
            model.addAttribute("publicId", user.getPublicId());
            model.addAttribute("name", user.getName());
            model.addAttribute("emailid", user.getEmailid());
            model.addAttribute("dob", user.getDob());
            model.addAttribute("gender", user.getGender());

            return "public_dashboard"; // Show the dashboard
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "public_login"; // Redirect back to login page
        }
    }

    @GetMapping("/public_dashboard")
    public String showDashboard(
            @RequestParam("publicId") String publicId,
            @RequestParam("name") String name,
            @RequestParam("emailid") String emailid,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            Model model) {

        // Pass user details to the dashboard view
        model.addAttribute("publicId", publicId);
        model.addAttribute("name", name);
        model.addAttribute("emailid", emailid);
        model.addAttribute("dob", dob);
        model.addAttribute("gender", gender);

        return "public_dashboard"; // Render dashboard
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Redirect to the home page
    }


   
    @Autowired
    private PublicService publicService;

    // View public's request status
     // Method to view status based on publicId
    @RequestMapping("/view_status")
    public String viewRequestStatus(@RequestParam Integer publicId, Model model) {
        // Fetch work assignments for the given publicId
        List<WorkAssign> workAssigns = publicService.getWorkAssignStatusByPublicId(publicId);

        if (workAssigns != null && !workAssigns.isEmpty()) {
            model.addAttribute("workAssigns", workAssigns);
        } else {
            model.addAttribute("error", "No status found for the given public ID.");
        }

        return "view_public_sts"; // Return Thymeleaf template name
    }
}
