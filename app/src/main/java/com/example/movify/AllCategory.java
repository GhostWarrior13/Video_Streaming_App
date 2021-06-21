package com.example.movify;

import java.util.List;

public class AllCategory {

    String categoryTitle;
    Integer categoryID;
    private List<CategoryItem> categoryItemList = null;

    public AllCategory( Integer categoryID,String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryID = categoryID;
        this.categoryItemList = categoryItemList;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }


}
