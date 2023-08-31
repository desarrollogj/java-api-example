package com.desarrollogj.exampleapi.commons.helper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ServiceException extends InternalServerErrorException {
    public ServiceException(String code, String message) {
        super(code, message);
    }
}

