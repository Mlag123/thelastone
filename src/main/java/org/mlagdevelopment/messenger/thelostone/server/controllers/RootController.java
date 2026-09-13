package org.mlagdevelopment.messenger.thelostone.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RootController {


    @GetMapping("/")
    public String root(){
        return String.valueOf(new Date().getTime());
    }


}
