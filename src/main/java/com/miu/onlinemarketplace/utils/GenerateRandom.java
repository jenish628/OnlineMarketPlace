package com.miu.onlinemarketplace.utils;

import java.util.Random;

public class GenerateRandom {

    public static String random() {
        Random random = new Random();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        System.out.println(formatted);
        return formatted;
    }
}
