package com.example.notification.notification.controller;


import com.example.notification.notification.service.EmailTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NotificationController {

    @Autowired
    private EmailTestService emailTestService;

    @GetMapping("/test")
    public String testNotification() {
        emailTestService.sendTestEmail("prav2327@gmail.com");
        return "Notification Service is up and running!";
    }

}
