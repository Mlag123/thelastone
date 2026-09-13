package org.mlagdevelopment.messenger.thelostone.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RootController {


    @GetMapping("/")
    public ResponseEntity<String> root(){
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("/api")
    public ResponseEntity<String> rootApi(){
        return ResponseEntity.ok("What are u doing?");
    }
    @GetMapping("/furry")
    public ResponseEntity<String> furryEasterEgg(){
        String html = """
        <!DOCTYPE html>
        <html>
        <head><title>Furry</title></head>
        <body style="background:#111; color:#eee; text-align:center; font-family:sans-serif;">
                <h1 class="rainbow">Developer</h1>
                
                                           <style>
                                           .rainbow {
                                               font-size: 2rem;
                                               font-weight: bold;
                                               background: linear-gradient(
                                                   90deg,
                                                   #ff0000, #ff7f00, #ffff00, #00ff00,
                                                   #0000ff, #4b0082, #9400d3, #ff0000
                                               );
                                               background-size: 200% 100%;
                                               -webkit-background-clip: text;
                                               background-clip: text;
                                               -webkit-text-fill-color: transparent;
                                               color: transparent;
                                               animation: rainbow-slide 3s linear infinite;
                                           }
                
                                           @keyframes rainbow-slide {
                                               0%   { background-position: 0% 50%; }
                                               100% { background-position: 200% 50%; }
                                           }
                                           </style>            <img src="/images/furry.gif" alt="furry" style="max-width:80vw; border-radius:16px;">
        </body>
        </html>
        """;

        return ResponseEntity.ok(html);
    }


}
