package lamph11.web.centrerapi.common.exception;

import lamph11.web.centrerapi.common.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ViolationDetail {

    private String path;
    private String errorCode;
    private String message;
    private List<KeyValue> params;
}
