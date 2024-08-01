package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.user.service.RegistrationService;
import indi.muleisy.ra.service.user.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/phone")
    public Result registerByPhone(@RequestParam String phone, @RequestParam String code, @RequestParam String password) {
        if(verificationService.verifyPhoneCode(phone,code)) {
            return Result.success(registrationService.registerByPhone(phone,password));
        }
        return Result.failure(ResultCode.USER_VERIFY_ERROR);
    }

    @PostMapping("/email")
    public Result registerByEmail(@RequestParam String email, @RequestParam String code, @RequestParam String password) {
        if(verificationService.verifyEmailCode(email,code)) {
            return Result.success(registrationService.registerByEmail(email,password));
        }
        return Result.failure(ResultCode.USER_VERIFY_ERROR);
    }

    @PostMapping("/oauth2/{provider}")
    public Result registerByOAuth2(@PathVariable String provider, @RequestParam String token, @RequestParam String password) {
        return Result.success(registrationService.registerByOAuth2(provider, token, password));
    }
}
