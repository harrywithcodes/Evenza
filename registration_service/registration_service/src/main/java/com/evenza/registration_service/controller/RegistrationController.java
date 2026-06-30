package com.evenza.registration_service.controller;

import com.evenza.registration_service.model.Registration;
import com.evenza.registration_service.repository.RegistrationRepository;
import com.evenza.registration_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistration();
    }

    @PostMapping
    public Registration createRegistration(@RequestBody Registration Reg, @RequestParam String email) {
        Registration saved = registrationService.createregistration(Reg);

        try {
            RestTemplate restTemplate = new RestTemplate();

            Map eventData = restTemplate.getForObject(
                    "http://localhost:8081/api/events/" + Reg.getEventId(),
                    Map.class
            );

            String eventTitle = (eventData != null && eventData.get("title") != null)
                    ? eventData.get("title").toString()
                    : "the event";

            String body = "Your registration for " + eventTitle + " is successful! Thank you for registering with Evenza.";
            String url = "http://localhost:8084/api/notifications/send-email?to=" + email + "&subject=Registration Confirmed&body=" + body;
            restTemplate.postForObject(url, null, String.class);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }

        return saved;
    }

    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable int id) {
        registrationService.Delete(id);
    }

    @GetMapping("/count")
    public long getRegistrationCount() {
        return registrationService.getAllRegistration().size();
    }
}
