package lamph11.web.centrerapi.common.exception;

import lamph11.web.centrerapi.common.KeyValue;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LphException extends Throwable{

    private String errorCode;
    private Collection<KeyValue> params;
    private Set<ViolationDetail> violationDetails;


    public LphException(String message) {
        this(message, message);
    }


    private LphException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public LphException(String errorCode, String message, Set<ConstraintViolation> violations) {
        this(errorCode, message);
        if (null == violations || violations.isEmpty())
            return;

        this.violationDetails = new HashSet<>();
        violations.forEach(violation -> {
            ViolationDetail violationDetail = ViolationDetail.builder()
                    .path(violation.getPropertyPath().toString())
                    .errorCode(violation.getMessageTemplate())
                    .message(violation.getMessage())
                    .build();
            this.violationDetails.add(violationDetail);
        });
    }
}
