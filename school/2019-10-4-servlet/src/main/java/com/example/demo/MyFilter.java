package com.example.demo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String url = servletRequest instanceof HttpServletRequest ?
                ((HttpServletRequest) servletRequest).getRequestURL().toString() : "N/A";

        System.out.println("from filter, processing url: " + url);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy () {
    }
}