package com.hspedu.tankgame6;

import java.lang.ref.SoftReference;
import java.util.Scanner;

/**
 * @author Susie
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        for (int i=0;i<8; i++) {
            byte[] b = new byte[1024 * 1024 * 1024];
            SoftReference softReference = new SoftReference<>(b);
        }

        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
}
