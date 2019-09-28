package com.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Date birthday;
    private String upPath;

    public User() {
    }

    public User(Integer id, String username, String password, Date birthday, String upPath) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.upPath = upPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUpPath() {
        return upPath;
    }

    public void setUpPath(String upPath) {
        this.upPath = upPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", upPath='" + upPath + '\'' +
                '}';
    }
}
