package com.study.recyclermvvmexample.model.vo;

public class UserDTO {

    private int id;
    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserDTO (int id_, String nickname_) {
        this.id = id_;
        this.nickname = nickname_;

    }

}
