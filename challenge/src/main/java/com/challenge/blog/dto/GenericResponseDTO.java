package com.challenge.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GenericResponseDTO {
    private String status;
    private String message;

    @JsonIgnore
    public GenericResponseDTO successResponse(String message) {
        GenericResponseDTO data = new GenericResponseDTO();
        data.setStatus("Success");
        data.setMessage(message);
        return data;
    }

    @JsonIgnore
    public GenericResponseDTO errorResponse(String message) {
        GenericResponseDTO data = new GenericResponseDTO();
        data.setStatus("Failed");
        data.setMessage(message);
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
