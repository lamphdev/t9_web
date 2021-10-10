package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.exception.LphException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(LphException.class)
    public ResponseEntity<?> handleError(LphException exception) {
        exception.printStackTrace();
        Map<String, Object> response = new HashMap<>();
        response.put("responseCode", exception.getErrorCode());
        response.put("responseMessage", exception.getMessage());
        response.put("params", exception.getParams());
        response.put("details", exception.getViolationDetails());
        return ResponseEntity.badRequest().body(response);
    }
}
