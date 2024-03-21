package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        List<Integer> number = new ArrayList<>();
        List<Article> articles = new ArrayList<>();

        int lastArticleId = 0;
//        int count = 0;

        while (true) {
//            if(count == 0) {
//                PrintSet();
//                count++;
//            }
            System.out.print("명령어 입력 : ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            if (cmd.length()==0){
                System.out.print("명령어를 입력하세요.");
                continue;
            }

            if (cmd.equals("exit")){
                break;
            }
            // 정수 입력하여 숫자 저장하기
            else if(cmd.equals("num")){
                System.out.print("정수를 입력 : ");
                int num = sc.nextInt();
                sc.nextLine();

                number.add(num);
                System.out.println("저장 완료");
            }
            // 저장한 정수 출력하기
            else if (cmd.equals("nums")){
                System.out.printf("현재 저장된 수는 %s 입니다.\n", number.toString());

            }

            else if (cmd.equals("article write")){
                int id = lastArticleId + 1;
                lastArticleId = id;
                String regDate = util.getNowDateStr();
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate,title, body);
                articles.add(article);
                System.out.printf("%d번 글이 생성되었습니다.\n", id);
            }

            else if (cmd.equals("article list")){
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }
                else {
                    System.out.println("번호 | 제목");
                    for (int i = 0; i<articles.size(); i++) {
                        Article article = articles.get(i);

                        System.out.printf("%d    | %s\n", article.id, article.title);
                    }
                }
            }
            else if (cmd.startsWith("article detail ")) {

                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);
                Article foundArticle = null;

                for( int i = 0; i <articles.size();i++){
                    Article article = articles.get(i);

                    if (article.id == id){
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null) {
                    System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }
                System.out.printf("번호: %s\n날짜 : %s\n제목 : %s\n내용 : %s\n", foundArticle.id,foundArticle.regDate, foundArticle.title, foundArticle.body);
            }
            else if (cmd.startsWith("article modify ")) {
                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);
                Article foundArticle = null;

                for( int i = 0; i <articles.size();i++){
                    Article article = articles.get(i);

                    if (article.id == id){
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null) {
                    System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                foundArticle.title = title;
                foundArticle.body = body;
                System.out.printf("%s 게시물이 수정 되었습니다.\n",id);
            }
            else if (cmd.startsWith("article delete ")){
                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);
                int foundIndex = -1;

                for (int i =0; i < articles.size(); i++){
                    Article article = articles.get(i);
                    if(article.id == id) {
                        foundIndex = i;
                        break;
                    }
                }

                if (foundIndex == -1){
                    System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }
                articles.remove(foundIndex);
                System.out.printf("%s번 게시물이 삭제되었습니다.\n", id);

                for (int i = foundIndex; i<articles.size(); i++){
                    Article article = articles.get(id);
                    article.set(i,i+1);
                }
                lastArticleId--;
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
//    public static void PrintSet(){
//        System.out.println("=== 입력 가능한 명령어 ===");
//        System.out.println("num -> 숫자 입력");
//        System.out.println("nums -> 숫자 확인");
//        System.out.println("article write -> 게시글 쓰기");
//        System.out.println("article list -> 게시물 보기");
//        System.out.println("exit -> 나가기");
//        System.out.println("=== 입력 가능한 명령어 ===");
//    }
}

class Article {
    int id;
    String regDate;
    String title;
    String body;

    public Article(int id, String regDate,String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.title = title;
        this.body = body;
    }
}