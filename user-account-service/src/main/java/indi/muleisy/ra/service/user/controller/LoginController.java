package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.rpc.Result;
import indi.muleisy.ra.service.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/jwt")
    public Result validateJwt(@RequestParam String encryptedJwt) {
        return Result.success(loginService.validateJwt(encryptedJwt));
    }

    @PostMapping
    public Result login(@RequestParam String identifier, @RequestParam String password) {
        return Result.success(loginService.loginByIdentifier(identifier, password));
    }

    @PostMapping("/oauth2/{provider}")
    public Result loginByOAuth2(@PathVariable String provider, @RequestParam String token) {
        return Result.success(loginService.loginByOAuth2(provider, token));
    }
}
