package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.KeyValue;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ViolationDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidParams(MethodArgumentNotValidException e) {
        List<ViolationDetail> details = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(field -> {
            ViolationDetail detail = ViolationDetail.builder()
                    .path(field.getField())
                    .errorCode(field.getCode())
                    .message(field.getDefaultMessage())
                    .params(
                            Arrays.asList(
                                    new KeyValue("value", field.getRejectedValue()),
                                    new KeyValue("params", field.getArguments())
                            )
                    ).build();
            details.add(detail);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("responseCode", "400");
        response.put("responseMessage", e.getMessage());
        response.put("params", null);
        response.put("details", details);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationHandler(ConstraintViolationException e) {
        List<ViolationDetail> details = new ArrayList<>();
        e.getConstraintViolations().forEach(violation -> {
            ViolationDetail detail = ViolationDetail.builder()
                    .path(violation.getPropertyPath().toString())
                    .errorCode(violation.getMessageTemplate())
                    .message(violation.getMessage())
                    .params(
                            Arrays.asList(
                                    new KeyValue("value", violation.getInvalidValue()),
                                    new KeyValue("params", violation.getConstraintDescriptor().getAttributes())
                            )
                    ).build();
            details.add(detail);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("responseCode", "400");
        response.put("responseMessage", e.getMessage());
        response.put("params", null);
        response.put("details", details);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(LphException.class)
    public ResponseEntity<?> handleError(LphException exception) {
        log.error(exception.getErrorCode(), exception);
        Map<String, Object> response = new HashMap<>();
        response.put("responseCode", exception.getErrorCode());
        response.put("responseMessage", exception.getMessage());
        response.put("params", exception.getParams());
        response.put("details", exception.getViolationDetails());
        return ResponseEntity.badRequest().body(response);
    }
}
