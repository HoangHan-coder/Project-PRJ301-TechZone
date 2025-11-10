/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 *
 * @author acer
 */
public class ImageNameHashToken {

    public static String randomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
    public static String hashImg(String img) {
        System.out.println(img);
            int dotIndex = img.lastIndexOf(".");
            String ext = img.substring(dotIndex);
            String tokenRandom = randomString(10);
            String imgHashToken = tokenRandom + ext;
            return imgHashToken;
    }
}
