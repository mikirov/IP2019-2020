package com.example.demo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletListener implements ServletContextListener {

    @Override
    public void contextInitialized (ServletContextEvent sce) {
        System.out.println("from ServletContextListener: " +
                " context initialized");
    }

    @Override
    public void contextDestroyed (ServletContextEvent sce) {
    }
}
