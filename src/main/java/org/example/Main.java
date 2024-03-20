package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        int lastArticleId = 0;

        while (true) {
            System.out.println("article write : 게시글 쓰기 \narticle list : 게시물 보기 \nexit : 나가기");
            System.out.print("명령어 입력 : ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            if (cmd.length()==0){
                continue;
            }

            if (cmd.equals("exit")){
                break;
            }

            if (cmd.equals("article write")){
                int id = lastArticleId + 1;
                lastArticleId = id;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                System.out.printf("%d번 글이 생성되었습니다.", id);

            }

            else if (cmd.equals("article list")){
                System.out.println("게시물이 없습니다.");
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}