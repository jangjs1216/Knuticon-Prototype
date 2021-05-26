package com.example.firebase1;

/*
    사용자 계정 정보 모델 클래스
*/

public class UserAccount {
    private String idToken;     // Firebase Uid (고유 토큰정보)
    private String emailId;     // 이메일 아이디
    private String password;    // 비밀번호
    private int point;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public UserAccount() {
        this.idToken = "Init";
        this.emailId = "Init";
        this.password = "Init";
        this.point = 0;
    }

    public UserAccount(String idToken, String emailId, String password, int point) {
        this.idToken = idToken;
        this.emailId = emailId;
        this.password = password;
        this.point = point;
    }
    //Firebase Realtime data 를 이용하려면 생성자를 이용해야 함.

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
