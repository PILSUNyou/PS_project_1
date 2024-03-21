package org.example;

import org.example.dto.Article;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Article> articles;
    App(){
        articles = new ArrayList<>();
    }

    public void start() {
        System.out.println("== 프로그램 시작 ==");
        // 테스트 데이터 실행
        makeTestData();
        Scanner sc = new Scanner(System.in);
        List<Integer> number = new ArrayList<>();
        // 게시물 프로그램 실행
        while (true) {
            System.out.print("명령어 입력 : ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();
            // cmd에 명령어를 입력하여 게시물 관리
            if (cmd.length()==0){
                System.out.print("명령어를 입력하세요.");
                continue;
            }
            // exit를 입력하면 게시물 관리를 끝내고 실행 종료
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
            // 새로운 게시물 작성하기
            else if (cmd.equals("article write")){
                int id = articles.size() + 1;
                String regDate = Util.getNowDateStr();
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate,title, body);
                articles.add(article);
                System.out.printf("%d번 글이 생성되었습니다.\n", id);
            }
            // 저장된 게시물 확인하기
            else if (cmd.startsWith("article list")){
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }
                String searchKeyword = cmd.substring("article list".length()).trim();

                List<Article> forListArticles = articles;

                if(searchKeyword.length() > 0){
                    forListArticles = new ArrayList<>();

                    for (Article article : articles){
                        if(article.title.contains(searchKeyword)){
                            forListArticles.add(article);
                        }
                    }

                    if (articles.size() == 0){
                        System.out.println("검색 결과가 존재하지 않습니다.");
                        continue;
                    }
                }

                System.out.println("번호 | 조희 | 제목");
                for (int i = 0; i<forListArticles.size(); i++) {
                    Article article = forListArticles.get(i);
                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
            }
            // 게시물의 상세 내용 보기
            else if (cmd.startsWith("article detail ")) {

                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);

                Article foundArticle = getAritcleById(id);

                if (foundArticle == null) {
                    System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }
                foundArticle.increaseHit();
                System.out.printf("번호 : %s\n", foundArticle.id);
                System.out.printf("날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
                System.out.printf("조회 : %s\n", foundArticle.hit);

            }
            // 게시물 수정하기
            else if (cmd.startsWith("article modify ")) {
                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);

                Article foundArticle = getAritcleById(id);

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
            // 게시물 삭제하기
            else if (cmd.startsWith("article delete ")){
                String cmdBits = cmd.split(" ")[2];
                int id = Integer.parseInt(cmdBits);

                int foundIndex = getAritcleIndexById(id);

                if (foundIndex == -1){
                    System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }
                articles.remove(foundIndex);
                System.out.printf("%s번 게시물이 삭제되었습니다.\n", id);
            }
            // 잘못 입력할 경우 해당 문구 출력하기
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }

    private int getAritcleIndexById(int id) {
        int i = 0;
        for ( Article article : articles){
            if (article.id == id){
                return i;
            }
            i++;
        }
        return -1;
    }

    private Article getAritcleById(int id){
        int index = getAritcleIndexById(id);

        if (index != -1){
            return articles.get(index);
        }
        return null;
    }
    private void makeTestData(){
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
        articles.add(new Article(1, Util.getNowDateStr(), "제목 1", "내용 1",12));
        articles.add(new Article(2, Util.getNowDateStr(), "제목 2", "내용 2",101));
        articles.add(new Article(3, Util.getNowDateStr(), "제목 3", "내용 3",3));
    }
}
