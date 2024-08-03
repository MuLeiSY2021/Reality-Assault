package indi.muleisy.ra.service.user.config;

import indi.muleisy.ra.service.user.intercptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/user/**")
                .excludePathPatterns("/api/login/*")
                .excludePathPatterns("/api/verify/*")
                .excludePathPatterns("/api/register/*")
        ;
    }
}
