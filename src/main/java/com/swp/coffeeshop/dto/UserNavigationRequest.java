package com.swp.coffeeshop.dto;

import java.util.Map;

public class UserNavigationRequest {
    private String role;
    private String searchValue;
    private int pageNum;
    Map<String, Object> order;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Map<String, Object> getOrder() {
        return order;
    }

    public void setOrder(Map<String, Object> order) {
        this.order = order;
    }
}
