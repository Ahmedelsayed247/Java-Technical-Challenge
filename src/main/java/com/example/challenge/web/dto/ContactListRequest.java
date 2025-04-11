package com.example.challenge.web.dto;

import lombok.Data;

import java.util.List;

@Data
// default setting for the request list of contact
public class ContactListRequest {
    private int page ;
    private int size ;
    private List<String> sortBy = List.of("firstName");
    private String sortDir = "asc";
}