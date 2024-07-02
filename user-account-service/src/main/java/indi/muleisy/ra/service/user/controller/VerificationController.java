package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.service.user.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/phone")
    public String sendPhoneVerification(@RequestParam String phone) {
        return verificationService.sendPhoneVerification(phone);
    }

    @PostMapping("/email")
    public String sendEmailVerification(@RequestParam String email) {
        return verificationService.sendEmailVerification(email);
    }

    @GetMapping("/oauth2/callback/qq")
    public String handleQQCallback(Authentication authentication, Model model) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        model.addAttribute("name", oauthToken.getPrincipal().getAttribute("nickname"));
        model.addAttribute("avatar", oauthToken.getPrincipal().getAttribute("figureurl_qq_1"));
        return "welcome"; // return a view name, e.g., welcome.html
    }
}
