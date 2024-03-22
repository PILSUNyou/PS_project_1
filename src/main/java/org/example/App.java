package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    public void start() {
        System.out.println("== 프로그램 시작 ==");
        // 테스트 데이터 실행

        List<Integer> number = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);
        articleController.makeTestData();
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
            // 회원가입
            else if (cmd.equals("member join")){
                memberController.doJoin();
            }
            // 새로운 게시물 작성하기
            else if (cmd.equals("article write")){
                articleController.doWrite();
            }
            // 저장된 게시물 확인하기
            else if (cmd.startsWith("article list")){
                articleController.showList(cmd);
            }
            // 게시물의 상세 내용 보기
            else if (cmd.startsWith("article detail ")) {
                articleController.showDetail(cmd);
            }
            // 게시물 수정하기
            else if (cmd.startsWith("article modify ")) {
                articleController.doModify(cmd);
            }
            // 게시물 삭제하기
            else if (cmd.startsWith("article delete ")){
                articleController.doDelete(cmd);
            }
            // 잘못 입력할 경우 해당 문구 출력하기
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }

}
