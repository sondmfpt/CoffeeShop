package com.swp.coffeeshop.dto;

import com.swp.coffeeshop.models.User;

import java.util.List;

public class UserNavigationResponse {
    private List<User> users;
    private int totalPage;
    private int totalUser;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}
