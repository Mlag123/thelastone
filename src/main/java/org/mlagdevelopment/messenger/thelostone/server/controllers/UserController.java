package org.mlagdevelopment.messenger.thelostone.server.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {



    @GetMapping("/renameUI")
    public ResponseEntity<String> renameUserUiName(){
        return null;
    }

}
