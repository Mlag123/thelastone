package org.mlagdevelopment.messenger.thelostone;

import org.springframework.http.ResponseEntity;

public class MainTest {
    public static void main(String[] args) {
        RoomTest roomTest = new RoomTest();
        ResponseEntity<String> a = roomTest.test();
        System.out.println(a.getBody());
    }
}
