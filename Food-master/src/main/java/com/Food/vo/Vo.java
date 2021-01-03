package com.Food.vo;

import com.Food.Entity.User;
public class Vo {
    private User user;
    private String message;

    public Vo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
