package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.service.ArticleService;
import org.example.service.MemberService;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private String cmd;
    private String actionMethodName;
    private ArticleService articleService;
    private MemberService memberService;
    public ArticleController(Scanner sc){
        this.sc = sc;
        articleService = Container.articleService;
        memberService = Container.memberService;
    }

    public void doAction(String cmd, String actionMethodName){
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ){
            case "write":
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
                System.out.printf("존재하지 않는 명령어 입니다.\n");
                break;
        }

    }
    public void makeTestData(){
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
        articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1 ,"제목 1", "내용 1",12));
        articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2 ,"제목 2","내용 2",101));
        articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2 ,"제목 3","내용 3",3));
    }
    public void doWrite() {
        int id = articleService.getNewId();
        String regDate = Util.getNowDateStr();
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, loginedMember.id, title, body);
        articleService.write(article);
        System.out.printf("%d번 글이 생성되었습니다.\n", id);
    }

    public void showList() {
        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

        if (forPrintArticles.size() == 0) {
            System.out.println("검색결과가 존재하지 않습니다.");
        }
        System.out.println("번호 | 작성자 | 조희 | 제목");
        for (int i = 0; i<forPrintArticles.size(); i++) {
            Article article = forPrintArticles.get(i);
            String writerName = memberService.getMemberNameById(article.memberId);
            System.out.printf("%4d | %s | %4d | %s\n", article.id, writerName, article.hit, article.title);
        }
    }

    public  void showDetail() {
        String cmdBits = cmd.split(" ")[2];
        int id = Integer.parseInt(cmdBits);

        Article foundArticle = articleService.getAritcleById(id);

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

        Article foundArticle = articleService.getAritcleById(id);

        if (foundArticle == null) {
            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        if (foundArticle.memberId != loginedMember.id){
            System.out.printf("권한이 없습니다.\n");
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

       Article foundArticle = articleService.getAritcleById(id);

        if (foundArticle == null){
            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        if (foundArticle.memberId != loginedMember.id){
            System.out.printf("권한이 없습니다.\n");
            return;
        }

        articleService.remove(foundArticle);
        System.out.printf("%s번 게시물이 삭제되었습니다.\n", id);
    }


}

