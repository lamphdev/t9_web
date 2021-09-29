package lamph11.web.centrerapi.common.exception;

import lamph11.web.centrerapi.common.KeyValue;

import java.util.Collection;

public class ResourceNotFoundException extends LphException {

    public ResourceNotFoundException(Collection<KeyValue> params) {
        this("RESOURCE_NOT_FOUND", params);
    }

    public ResourceNotFoundException(String message, Collection<KeyValue> params) {
        super(message);
        setParams(params);
    }
}
