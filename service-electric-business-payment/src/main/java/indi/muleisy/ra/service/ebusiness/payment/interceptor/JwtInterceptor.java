package indi.muleisy.ra.service.ebusiness.payment.interceptor;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.report.service.JwtService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String encryptedJwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(encryptedJwt) || !userService.validateJwt(encryptedJwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(Result.failure(ResultCode.USER_NOT_LOGGED_IN).toString());
            return false;
        }
        return true;
    }
}
