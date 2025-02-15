package com.example.Enterprise.Resource.Suite.ERS.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchDTO {

    @JsonProperty("criteria")
    private CriteriaDTO criteriaDTO;

    public CriteriaDTO getCriteriaDTO() {
        return criteriaDTO;
    }

    public void setCriteriaDTO(CriteriaDTO criteriaDTO) {
        this.criteriaDTO = criteriaDTO;
    }
}
