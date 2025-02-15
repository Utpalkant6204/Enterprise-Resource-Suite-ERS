package com.example.Enterprise.Resource.Suite.ERS.Exceptions;

import java.util.List;

public class ValidationErrorResponse {

    private List<String> exceptions;

    public ValidationErrorResponse(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }

}
