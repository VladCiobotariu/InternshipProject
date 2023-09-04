package com.ozius.internship.project.controller;

public class ApiPaginationResponse<T> {

    private int page;
    private int itemsPerPage;
    private T data;

    public ApiPaginationResponse(int page, int itemsPerPage, T data) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public T getData() {
        return data;
    }
}
