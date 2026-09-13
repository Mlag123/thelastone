package org.mlagdevelopment.messenger.thelostone.server.command;

import org.mlagdevelopment.messenger.thelostone.client.service.UserAuthentication;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Command {

    private static final Logger log = LoggerFactory.getLogger(Command.class);
    private Scanner scanner = new Scanner(System.in);


    public void commandStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
        /*        log.info("CLI Starting...");
                log.info("CLI Work");
                UserAuthentication userAuthentication = new UserAuthentication();
                String[] userfield = new String[3];
                log.info("enter username");
                userfield[0] = scanner.nextLine();
                log.info("enter uiname");
                userfield[1] = scanner.nextLine();

                log.info("enter pass");
                userfield[2] = scanner.nextLine();

                log.info(userAuthentication.register(new RegisterRequest(userfield[0], userfield[1], userfield[2])).getBody());
*/
            }
        });
    }

}
