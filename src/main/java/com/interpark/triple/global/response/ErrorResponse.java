package com.interpark.triple.global.response;

import lombok.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String businessCode;
  private String errorMessage;
  private List<FieldError> errors;

  public ErrorResponse(ErrorCode code, List<FieldError> fieldErrors) {
    this.errorMessage = code.getMessage();
    this.errors = fieldErrors;
    this.businessCode = code.getCode();
  }

  public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class FieldError {
    private String field;
    private String value;
    private String reason;

    public FieldError(final String field, final String value, final String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors =
          bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(
              error ->
                  new FieldError(
                      error.getField(),
                      error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                      error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }
}
