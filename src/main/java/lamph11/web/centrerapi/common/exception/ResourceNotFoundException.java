package lamph11.web.centrerapi.common.exception;

import lamph11.web.centrerapi.common.KeyValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResourceNotFoundException extends LphException {

    public ResourceNotFoundException(Collection<KeyValue> params) {
        this("RESOURCE_NOT_FOUND", params);
    }

    public ResourceNotFoundException(String message, Collection<KeyValue> params) {
        super(message);
        setParams(params);
    }

    public ResourceNotFoundException(Class t, String id) {
        this(null);
        List<KeyValue> params = Arrays.asList(
                new KeyValue("resourceType", t.getSimpleName()),
                new KeyValue("resourceIdentify", id)
        );
        setParams(params);
    }
}
