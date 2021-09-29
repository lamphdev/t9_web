package lamph11.web.centrerapi.common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
