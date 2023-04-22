package com.miu.onlinemarketplace.service.generic.dtos;

import lombok.Data;

@Data
public class GenericResponseDTO<T> {

    private String messageCode;

    private T response;

    public GenericResponseDTO() {
    }

    public GenericResponseDTO(T response) {
        this.response = response;
    }
}
