package com.fly.utils;

import java.util.Random;

/**
 * @author 游雄
 * @describe
 * @create 9:59 2018/10/7 0007
 */
public class CodeUtil {

    public static String getRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(9);
            sb.append(randomInt);
        }
        return sb.toString();
    }
}
