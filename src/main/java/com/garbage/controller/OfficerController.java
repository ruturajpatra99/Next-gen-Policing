package com.garbage.controller;

import com.garbage.model.Officer;
import com.garbage.repository.RequestRepository;
import com.garbage.repository.OfficerRepository;
import com.garbage.repository.WorkAssignRepository;
//import com.garbage.repository.WorkAssignRepository;
import com.garbage.service.WorkAssignService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OfficerController {
    @Autowired
    private OfficerRepository OfficerRepository;

    @Autowired
    private RequestRepository garbageRepository;

    @Autowired
    private WorkAssignRepository WorkAssignRepository;// Repository to access garbage records

    @GetMapping("/Officer_login")
    public String showOfficerLoginPage() {
        return "Officer_login"; // The name of the HTML file in the templates folder
    }

    @PostMapping("/ologin")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        Optional<Officer> OfficerUser = OfficerRepository.findByemailAndPassword(email, password);
        if (OfficerUser.isPresent()) {
            Officer user = OfficerUser.get();
            model.addAttribute("officer_id", user.getofficer_id());
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getemail());
            model.addAttribute("dob", user.getDob());
            model.addAttribute("gender", user.getGender());
            long completedWorkCount = WorkAssignRepository.countByStatus("completed");

            // Count the total number of garbage records
            long totalGarbageCount = garbageRepository.count();

            // The pending work count will be total garbage count minus completed work count
            long pendingWorkCount = totalGarbageCount - completedWorkCount;

            // Add the data to the model
            model.addAttribute("completedWorkCount", completedWorkCount);
            model.addAttribute("pendingWorkCount", pendingWorkCount);
            return "Officer_dashboard"; // View name
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Officer_login"; // Redirect back to login page
        }
    }

    @Autowired
    private WorkAssignService workAssignService;

    @GetMapping("/viewCleanSts")
    public String viewCompletedWork(Model model) {
        // Fetch all completed work
        List<Map<String, Object>> completedWork = workAssignService.getCompletedWorkDetails();

        // Add data to the model
        model.addAttribute("completedWork", completedWork);

        return "View_request_sts"; // Name of the Thymeleaf template
    }

    // @GetMapping("/logout")
    // public String logout() {
    // return "redirect:/";
    // }
}
