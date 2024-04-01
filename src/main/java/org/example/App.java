package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.ExportController;
import org.example.controller.MemberController;
import org.example.service.ExportService;

import java.util.Scanner;

public class App {

    public void start() {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController(sc);
        Controller articleController = new ArticleController(sc);
        ExportController exportController = new ExportController(sc);
        // 테스트 데이터 실행 및 회원 생성
        articleController.makeTestData();
        memberController.makeTestData();

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

            if(cmdBits.length == 1){
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }
                String controllerName = cmdBits[0];
                String actionMethodName = cmdBits[1];
                Controller controller = null;

            if (controllerName.equals("article")){
                controller = articleController;
            }
            else if(controllerName.equals("member")){
                controller = memberController;
            }
            else if(controllerName.equals("export")){
                controller = exportController;
            }
            else {
                System.out.println("존재하지 않는 명령어입니다.");
                continue;
            }

            String actionName = controllerName + "/" + actionMethodName;
            switch (actionName){
                case "article/write":
                case "article/delete":
                case "article/modify":
                case "member/logout":
                    if(controller.isLogined() == false){
                        System.out.println("로그인 후 이용해주세요.");
                        continue;
                    }
                    break;
            }

            switch (actionName){
                case "member/login":
                case "member/join":
                    if(controller.isLogined()){
                        System.out.println("로그아웃 후 이용해주세요.");
                        continue;
                    }
                    break;
            }
            controller.doAction(cmd, actionMethodName);

        }
        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}
