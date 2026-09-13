package org.mlagdevelopment.messenger.thelostone.server.utils;

import org.mlagdevelopment.messenger.thelostone.server.service.JwtService;

//testcode!
public class ControllerUtils {

    private final JwtService jwtService;

    public ControllerUtils(JwtService jwtService) {
        this.jwtService = jwtService;
    }
}
