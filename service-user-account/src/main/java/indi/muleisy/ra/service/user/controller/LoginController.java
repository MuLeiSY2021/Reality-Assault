package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestParam String identifier, @RequestParam String password) {
        return Result.success(loginService.loginByIdentifier(identifier, password));
    }

    @PostMapping("/oauth2/{provider}")
    public Result loginByOAuth2(@PathVariable String provider, @RequestParam String token) {
        return Result.success(loginService.loginByOAuth2(provider, token));
    }
}
