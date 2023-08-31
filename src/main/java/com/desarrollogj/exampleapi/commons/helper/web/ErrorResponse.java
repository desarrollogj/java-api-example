package com.desarrollogj.exampleapi.commons.helper.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends GenericResponse {
    @Schema(description = "Api internal error code. Ex: USER_NOT_FOUND")
    private String errorCode;
    @Schema(description = "Api error complete description")
    private String error;

    public ErrorResponse(int status, String message) {
        super(status, message);
    }

    public ErrorResponse(int status, String message, String errorCode, String error) {
        super(status, message);
        this.errorCode = errorCode;
        this.error = error;
    }
}
