//package com.it.ceb.util.common;
//
//import java.io.IOException;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@WebFilter("/*")
//public class SessionTimeoutFilter implements Filter {
//
//    @Overridea
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization code, if needed
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        HttpSession session = req.getSession(false);
//
//        // Check session and avoid redirect loop for WelcomePTS
//        if (session == null || session.getAttribute("epf") == null) {
//            String loginURI = req.getContextPath() + "/WelcomePTS";
//
//            // Avoid redirect loop
//            if (!req.getRequestURI().equals(loginURI)) {
//                res.sendRedirect(loginURI);
//                return;
//            }
//        }
//
//        // Proceed with the next filter in the chain
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup code, if needed
//    }
//}
