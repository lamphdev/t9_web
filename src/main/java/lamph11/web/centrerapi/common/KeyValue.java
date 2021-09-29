package lamph11.web.centrerapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue {

    private String key;
    private Object value;
}
