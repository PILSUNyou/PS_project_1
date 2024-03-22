package org.example.controller;

import org.example.dto.Article;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private List<Article> articles;
    private String cmd;
    private String actionMethodName;
    public ArticleController(Scanner sc){
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName){
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ){
            case "write":
                if (isLogined() == false){
                    System.out.println("로그인 후 이용해 주세요.");
                    break;
                }
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            default :
                System.out.printf("존재하지 않는 명령어 입니다.");
                break;
        }

    }
    public void makeTestData(){
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
        articles.add(new Article(1, Util.getNowDateStr(), 1 ,"제목 1", "내용 1",12));
        articles.add(new Article(2, Util.getNowDateStr(), 2 ,"제목 2","내용 2",101));
        articles.add(new Article(3, Util.getNowDateStr(), 2 ,"제목 3","내용 3",3));
    }
    public  void doWrite() {
        int id = articles.size() + 1;
        String regDate = Util.getNowDateStr();
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, loginedMember.id, title, body);
        articles.add(article);
        System.out.printf("%d번 글이 생성되었습니다.\n", id);
    }

    public void showList() {
        if(articles.size() == 0) {
            System.out.println("게시물이 없습니다.");
            return;
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
                return;
            }
        }

        System.out.println("번호 | 작성자 | 조희 | 제목");
        for (int i = 0; i<forListArticles.size(); i++) {
            Article article = forListArticles.get(i);
            System.out.printf("%4d | %6d | %4d | %s\n", article.id, article.memberId, article.hit, article.title);
        }
    }

    public  void showDetail() {
        String cmdBits = cmd.split(" ")[2];
        int id = Integer.parseInt(cmdBits);

        Article foundArticle = getAritcleById(id);

        if (foundArticle == null) {
            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
            return;
        }
        foundArticle.increaseHit();
        System.out.printf("번호 : %s\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("작성자 : %s\n", foundArticle.memberId);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %s\n", foundArticle.hit);

    }

    public  void doModify() {
        String cmdBits = cmd.split(" ")[2];
        int id = Integer.parseInt(cmdBits);

        Article foundArticle = getAritcleById(id);

        if (foundArticle == null) {
            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        foundArticle.title = title;
        foundArticle.body = body;
        System.out.printf("%s 게시물이 수정 되었습니다.\n",id);
    }

    public  void doDelete(  ) {
        String cmdBits = cmd.split(" ")[2];
        int id = Integer.parseInt(cmdBits);

        int foundIndex = getAritcleIndexById(id);

        if (foundIndex == -1){
            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
            return;
        }
        articles.remove(foundIndex);
        System.out.printf("%s번 게시물이 삭제되었습니다.\n", id);
    }

    private  int getAritcleIndexById(int id) {
        int i = 0;
        for ( Article article : articles){
            if (article.id == id){
                return i;
            }
            i++;
        }
        return -1;
    }

    private  Article getAritcleById(int id){
        int index = getAritcleIndexById(id);

        if (index != -1){
            return articles.get(index);
        }
        return null;
    }
}

