package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.service.user.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/phone")
    public String registerByPhone(@RequestParam String phone, @RequestParam String code, @RequestParam String password) {
        return registrationService.registerByPhone(phone, code, password);
    }

    @PostMapping("/email")
    public String registerByEmail(@RequestParam String email, @RequestParam String code, @RequestParam String password) {
        return registrationService.registerByEmail(email, code, password);
    }

    @PostMapping("/oauth2/{provider}")
    public String registerByOAuth2(@PathVariable String provider, @RequestParam String token, @RequestParam String password) {
        return registrationService.registerByOAuth2(provider, token, password);
    }
}
