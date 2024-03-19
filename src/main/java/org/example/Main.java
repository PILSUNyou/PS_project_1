package org.example;

public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 2;
        funtion(a,b);
    }
    public static int funtion(int a, int b){
        try {
            throw new Exception("그냥 에러 띄우기");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

