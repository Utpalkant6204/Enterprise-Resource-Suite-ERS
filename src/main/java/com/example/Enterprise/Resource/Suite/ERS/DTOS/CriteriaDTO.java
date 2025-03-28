package com.example.Enterprise.Resource.Suite.ERS.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class CriteriaDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("role")
    private List<String> role;

    @JsonProperty("searchText")
    private String searchText;

    @JsonProperty("sortBy")
    private String sortByAsc;

    @JsonProperty("pagination")
    private PaginationDTO pagination;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSortByAsc() {
        return sortByAsc;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public void setSortByAsc(String sortByAsc) {
        this.sortByAsc = sortByAsc;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
