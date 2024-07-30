package indi.muleisy.ra.service.user.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userService;

    @PutMapping("/nickname")
    public Result updateNickname(@RequestParam String id, @RequestParam String newNickname) {
        boolean success = userService.updateNickname(id, newNickname);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/avatar")
    public Result updateAvatar(@RequestParam String id, @RequestParam String newAvatarUrl) {
        boolean success = userService.updateAvatar(id, newAvatarUrl);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/gender")
    public Result updateGender(@RequestParam String id, @RequestParam boolean gender) {
        boolean success = userService.updateGender(id, gender);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/birthdate")
    public Result updateBirthdate(@RequestParam String id, @RequestParam Date birthdate) {
        boolean success = userService.updateBirthdate(id, birthdate);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/email")
    public Result updateEmail(@RequestParam String id, @RequestParam String newEmail, @RequestParam String verificationCode) {
        boolean success = userService.updateEmail(id, newEmail, verificationCode);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/phone")
    public Result updatePhone(@RequestParam String id, @RequestParam String newPhone, @RequestParam String verificationCode) {
        boolean success = userService.updatePhone(id, newPhone, verificationCode);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @PutMapping("/third-party/{provider}")
    public Result updateThirdPartyToken(@PathVariable String provider, @RequestParam String id, @RequestParam String token) {
        boolean success = userService.updateThirdPartyToken(id, provider, token);
        if (success) {
            return Result.success();
        }
        return Result.failure(ResultCode.USER_NOT_EXISTED);
    }

    @DeleteMapping("/deleteContactInfo")
    public Result deleteContactInfo(@RequestParam String id, @RequestParam String type) {
        boolean success = userService.deleteContactInfo(id, type);
        if (success) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.PARAM_IS_INVALID, "Failed to delete contact info.");
        }
    }
}
