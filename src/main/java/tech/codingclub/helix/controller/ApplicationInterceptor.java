package tech.codingclub.helix.controller;

import org.apache.log4j.Logger;
import org.apache.xpath.operations.Bool;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

public class ApplicationInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ApplicationInterceptor.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (httpServletRequest.getRequestURI().startsWith("/admin")) {
            Boolean isAdmin = ControllerUtils.isUserAdmin(httpServletRequest);
            if (isAdmin != null && isAdmin) {
                return true;
            } else if (!httpServletRequest.getRequestURI().startsWith("/admin/login")) {
                httpServletResponse.sendRedirect("/admin/login");
                return false;
            }
        }

        if (httpServletRequest.getRequestURI().equals("/login/home")) {
            Boolean isUser = ControllerUtils.isUserCodingMafia(httpServletRequest);
            if (isUser != null && isUser) {
                return true;
            } else {
                httpServletResponse.sendRedirect("/login?failed=home"); //He will be redirected to login page if not a user already
//                And Also We can specify anything after ? and it won't affect how our API works
                return false;
            }
        }

        if (httpServletRequest.getRequestURI().startsWith("/user")) {
            Boolean isUser = ControllerUtils.isUserCodingMafia(httpServletRequest);
            if (isUser != null && isUser) {
                return true;
            } else {
                if (httpServletRequest.getMethod().equals("GET")) {
                    httpServletResponse.sendRedirect("/login?failed=tweet");
                } else {
                    httpServletResponse.sendError(401); //For unauthorized request
                }
                return false;
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception exception)
            throws Exception {
    }
}