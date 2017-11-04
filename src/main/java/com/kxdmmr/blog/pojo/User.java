package com.kxdmmr.blog.pojo;

/**
 * Created by kxdmmr on 2017/8/19.
 */
public class User {
    int id;
    String userName;
    String passwd;

    public User(int id, String userName, String passwd){
        this.id = id;
        this.userName = userName;
        this.passwd = passwd;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
