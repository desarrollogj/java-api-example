package com.desarrollogj.exampleapi.commons.helper.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    @Schema(description = "Error HTTP status code")
    private int status;
    @Schema(description = "Error generic message")
    private String message;
}

