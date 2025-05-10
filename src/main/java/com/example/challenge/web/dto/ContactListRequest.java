package com.example.challenge.web.dto;

import lombok.Data;

import java.util.List;

@Data
// default setting for the request list of contact
public class ContactListRequest {
    private int page = 0 ;
    private int size = 10;
    private List<String> sortBy = List.of("firstName");
    private String sortDir = "asc";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<String> sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}