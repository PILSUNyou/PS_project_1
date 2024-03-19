package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        System.out.print("명령어 입력 : ");
        String cmd = sc.next();
        System.out.printf("입력된 명령어 : %s\n", cmd);

        System.out.print("정수 입력 : ");
        int num = sc.nextInt();
        System.out.printf("입력된 명령어 : %d\n", num);

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}