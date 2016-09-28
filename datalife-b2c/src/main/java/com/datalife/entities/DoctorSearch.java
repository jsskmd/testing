package com.datalife.entities;

/**
 * Created by DATASCRIBE on 5/20/2015.
 */

public class DoctorSearch {

    private Object userId;
    private Object name;
    private Object categories;
    public DoctorSearch() {
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getCategories() {
        return categories;
    }

    public void setCategories(Object categories) {
        this.categories = categories;
    }
}
