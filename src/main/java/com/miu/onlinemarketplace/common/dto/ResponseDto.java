package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseDto implements Serializable {
    private boolean isSuccess;
    private String message;
    private HttpStatus status;
    private Object body;

}
