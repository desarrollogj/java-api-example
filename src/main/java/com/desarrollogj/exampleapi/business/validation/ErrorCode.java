package com.desarrollogj.exampleapi.business.validation;

import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
  USER_NOT_FOUND("User with id %d not found"),
  USER_BY_REFERENCE_NOT_FOUND("User with reference %s not found"),
  USER_COULD_NOT_BE_DELETED("User with id %d could not be deleted");

  private final String description;

  public String getCode() {
    return this.name();
  }

  public String getDescription(Object... params) {
    return format(description, params);
  }
}
