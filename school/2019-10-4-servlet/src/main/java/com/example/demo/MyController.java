package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class MyController {

    @RequestMapping("/*")
    @ResponseBody
    public String handler () {

        //return "<h1>Hello</h1>";
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("../../resources/static/form.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        return contentBuilder.toString();


    }
}