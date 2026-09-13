package org.mlagdevelopment.messenger.thelostone;

import org.mlagdevelopment.messenger.thelostone.server.command.Command;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@SpringBootApplication
public class ThelostoneApplication {

    //public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(ThelostoneApplication.class, args);
        new Command().commandStart();
    }

}
