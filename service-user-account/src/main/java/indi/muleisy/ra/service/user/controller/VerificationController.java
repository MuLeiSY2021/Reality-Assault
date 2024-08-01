package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.user.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/send/phone")
    public Result sendPhoneVerification(@RequestParam String phone) {
        return Result.success(verificationService.sendPhoneVerification(phone));
    }

    @PostMapping("/send/email")
    public Result sendEmailVerification(@RequestParam String email) {
        return Result.success(verificationService.sendEmailVerification(email));
    }

}
