package com.garbage.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.garbage.model.Police;
import com.garbage.repository.WorkAssignRepository;
import com.garbage.repository.PoliceRepository;

import com.garbage.service.WorkAssignService;

@Controller
public class PoliceController {
    @Autowired
    private PoliceRepository WorkerRepository;

    @Autowired
    private WorkAssignService workAssignService;



    @GetMapping("/Worker_login")
    public String showWorkerlogin() {
        return "Police_login"; // The name of the HTML file in the templates folder
    }

    @PostMapping("/wlogin")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        Optional<Police> workerUser = WorkerRepository.findByemailAndPassword(email, password);
        if (workerUser.isPresent()) {
            Police user = workerUser.get();

            // Fetch work status counts from the work_assign table
            long pendingWorkCount = workAssignRepository.countByPoliceIdAndStatus(user.getPolice_id(), "Pending");
            long completedWorkCount = workAssignRepository.countByPoliceIdAndStatus(user.getPolice_id(), "Completed");

            // Add worker details to the model
            model.addAttribute("worker_id", user.getPolice_id());
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("dob", user.getDob());
            model.addAttribute("gender", user.getGender());
            model.addAttribute("pendingWorkCount", pendingWorkCount); // Add pending work count
            model.addAttribute("completedWorkCount", completedWorkCount); // Add completed work count

            return "Police_dashboard"; // Redirect to the dashboard view
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Police_login"; // Redirect back to login page
        }
    }

    // Method to view assigned clean work
    /*
     * @GetMapping("/viewCleanWork")
     * public String viewCleanWork(@RequestParam("workerId") Integer workerId, Model
     * model) {
     * List<WorkAssign> assignments =
     * workAssignService.getWorkAssignmentsByWorkerId(workerId);
     * model.addAttribute("assignments", assignments);
     * return "view_clean_work"; // View template for displaying work assignments
     * }
     */

    @Autowired
    private WorkAssignRepository workAssignRepository;

    @GetMapping("/viewCleanWork")
    public String viewCleanWork(@RequestParam("workerId") Integer workerId, Model model) {
        List<Object[]> assignedWork = workAssignRepository.findAssignedWorkByPoliceId(workerId);

        // Map data for the view
        List<Map<String, String>> workDetails = assignedWork.stream().map(row -> {
            Map<String, String> map = new HashMap<>();
            map.put("garbageId", row[0].toString());
            map.put("garbageAddress", row[1].toString());
            map.put("longitude", row[2].toString());
            map.put("latitude", row[3].toString());
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("workDetails", workDetails);
        model.addAttribute("workerId", workerId);
        return "view_request_work"; // Name of the Thymeleaf HTML file
    }

    @PostMapping("/uploadImage")
public String uploadImage(
        @RequestParam("garbageId") Integer garbageId,
        @RequestParam("workerId") Integer workerId, // Add workerId as a parameter
        @RequestParam("cleanedImage") MultipartFile cleanedImage,
        Model model) {
    try {
        if (!cleanedImage.isEmpty()) {
            // Get original file name
            String originalFileName = cleanedImage.getOriginalFilename();
            
            // Generate a unique file name using UUID and current timestamp
            String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "_" + originalFileName;

            // Define the directory to save the file
            String uploadDir = "src/main/resources/static/images/";

            // Create the full path for saving the file
            java.nio.file.Path path = Paths.get(uploadDir + uniqueFileName);

            // Save the file locally
            Files.copy(cleanedImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Update the database with the cleaned image path and status
            String imagePath = "/images/" + uniqueFileName; // Path relative to static folder
            workAssignService.updateInvestigatedImage(garbageId, imagePath);

            model.addAttribute("success", "Image uploaded successfully!");
        } else {
            model.addAttribute("error", "Please select an image to upload.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        model.addAttribute("error", "Failed to upload image. Try again!");
    }

    // Redirect to the view page with workerId to reload pending tasks
    return "redirect:/viewCleanWork?workerId=" + workerId;
}

}
