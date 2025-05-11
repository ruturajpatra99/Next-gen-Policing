package com.garbage.controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.garbage.model.Request;
import com.garbage.model.Police;
import com.garbage.repository.RequestRepository;
import com.garbage.service.RequestService;
import com.garbage.service.PoliceService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private RequestRepository garbageRepository;

    @Autowired
    private RequestService garbageService;

    @Autowired
    private PoliceService workerService;

    /**
     * Show form for submitting garbage request
     * 
     * @param model
     * @return the name of the form view
     */
    @GetMapping("/garbage_request")
    public String showCleanRequestForm(Model model) {
        return "Complaint_request"; // Corresponding Thymeleaf template
    }

    /**
     * Handles submission of garbage request, including image upload
     * 
     * @param publicId
     * @param publicName
     * @param garbageAddress
     * @param garbageImage
     * @param latitude
     * @param longitude
     * @param model
     * @return redirect to a different page after submission
     */
    @PostMapping("/garbage/submit")
    public String submitGarbageRequest(
            @RequestParam("public_id") String publicId,
            @RequestParam("public_name") String publicName,
            @RequestParam("garbage_address") String garbageAddress,
            @RequestParam("garbage_image") MultipartFile garbageImage,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            Model model) {

        try {
            // Save image to the 'garbage_img' folder inside the static directory
            String uploadDir = "src/main/resources/static/images/";
            String fileName = System.currentTimeMillis() + "_" + garbageImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, garbageImage.getBytes());

            // Save details to the database
            Request garbage = new Request();
            garbage.setPublicId(publicId);
            garbage.setPublicName(publicName);
            garbage.setRequestAddress(garbageAddress);
            garbage.setRequestImage("/images/" + fileName); // Relative path for viewing
            garbage.setLatitude(latitude);
            garbage.setLongitude(longitude);

            garbageRepository.save(garbage);
            model.addAttribute("message", "Request submitted successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload the image. Please try again.");
        }

        return "go_back"; // Redirect to the public dashboard after submission
    }
    
     
    

    /**
     * Displays the garbage requests that are available for assignment
     *
     * @param model
     * @return the view for showing the list of garbage requests
     */
    @GetMapping("/viewGarbageRequests")
    public String viewGarbageRequests(Model model) {
        List<Request> garbageRequests = garbageService.getRequests();
        List<Police> workers = workerService.getAllPolice();
        model.addAttribute("garbageRequests", garbageRequests);
        model.addAttribute("workers", workers);
        return "view_complaint_request"; // Returns the name of the Thymeleaf template
    }

    /**
     * Assign a police officer to handle a specific garbage request
     * 
     * @param requestId the request ID
     * @param policeId  the police officer ID
     * @return redirect to the garbage requests view
     */
    @PostMapping("/assignWorker")
    public String assignWorker(@RequestParam int requestId, @RequestParam int policeId) {
        garbageService.assignPoliceToRequest(requestId, policeId);
        return "redirect:/viewGarbageRequests";
    }
}
