package com.example.Enterprise.Resource.Suite.ERS.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginationDTO {

    @JsonProperty("offset")
    private int pageNumber = 0;

    @JsonProperty("limit")
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
