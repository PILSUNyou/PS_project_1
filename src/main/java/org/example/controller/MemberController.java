package org.example.controller;

import jdk.dynalink.support.AbstractRelinkableCallSite;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller{
    private  Scanner sc;
    private List<Member> members;
    private  String cmd;
    private  String actionMethodName;
    public MemberController(Scanner sc){
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd,String actionMethodName){
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ){
            case "join":
                doJoin();
                break;
        }
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
}
