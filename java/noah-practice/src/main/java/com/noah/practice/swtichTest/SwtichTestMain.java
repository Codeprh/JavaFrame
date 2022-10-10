package com.noah.practice.swtichTest;

public class SwtichTestMain {

    public static void main(String[] args) {
        soutSwitch();
    }

    public static void soutSwitch() {

        int a = 1;

        switch (2) {
            case 2:
                System.out.println("2");
            case 1:
                System.out.println("1");
            default:
                System.out.println("default");
            case 3:
                System.out.println("3");
        }
    }
}
