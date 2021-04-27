package com.StartupBBSR.competo.Models;

public class AllCategory {

    String categoryTitle;
    Integer categoryId;


    public AllCategory( Integer categoryId,String categoryTitle) {
    public AllCategory(String categoryTitle, Integer categoryId) {

        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
