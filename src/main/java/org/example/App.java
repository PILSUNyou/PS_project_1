package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;

import java.util.Scanner;

public class App {

    public void start() {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);
        // 테스트 데이터 실행
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
            String[] cmdBits = cmd.split(" ");
            String controllerName = cmdBits[0];
            String actionMethodName = cmdBits[1];
            Controller controller = null;

            if (controllerName.equals("article")){
                controller = articleController;
            }
            else if(controllerName.equals("member")){
                controller = memberController;
            }
            else {
                System.out.println("존재하지 않는 명령어입니다.");
                continue;
            }
            controller.doAction(cmd, actionMethodName);

        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }

}