package org.example.controller;

import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private List<Member> members;
    private String cmd;
    private String actionMethodName;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
            default:
                System.out.printf("존재하지 않는 명령어 입니다.");
                break;
        }
    }

    public void makeTestData() {
    System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
        members.add(new Member(1,Util.getNowDateStr(), "admin","admin","관리자"));
        members.add(new Member(2,Util.getNowDateStr(), "user1","user1","홍길동"));
        members.add(new Member(3,Util.getNowDateStr(), "user2","user2","홍길순"));
    }
    public void doJoin() {
        int id = members.size() +1;
        String regDate = Util.getNowDateStr();
        String loginId = null;
        while (true){
            System.out.printf("사용할 ID를 입력하세요 : ");
            loginId = sc.nextLine();

            if (isJoinableLoginId(loginId) == false){
                System.out.printf("%s(은)는 이미 사용중인 아이디 입니다.\n", loginId);
                continue;
            }
            break;
        }

        String loginPw= null;
        String loginPwConfirm = null;

        while (true){
            System.out.print("패스워드를 입력하세요 : ");
            loginPw = sc.nextLine();
            System.out.print("패스워드 확인 : ");
            loginPwConfirm = sc.nextLine();

            if (loginPw.equals(loginPwConfirm) == false) {
                System.out.println("패스워드가 일치하지 않습니다. 다시 작성해 주세요.");
                continue;
            }
            break;
        }

        System.out.printf("이름을 입력하세요 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate,loginId, loginPw, name);
        members.add(member);
        System.out.printf("%s님 %d번 회원이 생성 되었습니다.\n",member.name, id);
    }
    public void doLogin(){
        if (isLogined()){
            System.out.println("이미 로그인 되어 있습니다.");
            return;
        }

        System.out.printf("ID : ");
        String loginId = sc.nextLine();
        System.out.printf("PassWord : ");
        String loginPw = sc.nextLine();

        // 입력받은 아이디에 해당하는 회원이 존재하는지 확인
        Member member = getMemberByLoginId(loginId);

        if (member == null){
            System.out.println("해당 회원은 존재하지 않습니다.");
            return;
        }
        if (member.loginPw.equals(loginPw) == false){
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }
        loginedMember = member;
        System.out.printf("로그인 성공! %s님 환영합니다.\n",loginedMember.name);
    }

    private void doLogout() {
        if(isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다.");
        }

        loginedMember = null;
        System.out.printf("로그아웃 되었습니다.\n");
    }
    private boolean isJoinableLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if(index == -1){
            return true;
        }
        return false;
    }
    private int getMemberIndexByLoginId(String loginId) {
        int i = 0;
        for ( Member member : members){
            if (member.loginId.equals(loginId)){
                return i;
            }
            i++;
        }
        return -1;
    }
    private Member getMemberByLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if (index == -1){
            return null;
        }

        return members.get(index);
    }
}
