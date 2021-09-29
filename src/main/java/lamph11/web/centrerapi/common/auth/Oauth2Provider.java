package lamph11.web.centrerapi.common.auth;

import java.util.Arrays;
import java.util.Optional;

public enum Oauth2Provider {
    GOOGLE(
            "gg",
            "https://www.googleapis.com/oauth2/v3/userinfo",
            "sub"
    ),
    FACEBOOK(
            "fb",
            "https://graph.facebook.com/me?fields=id,name,email",
            "id"
    );

    private String name;
    private String userInfoEndpoint;
    private String nameAttribute;

    Oauth2Provider(String name, String userInfoEndpoint, String nameAttribute) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUserInfoEndpoint() {
        return userInfoEndpoint;
    }

    public String getNameAttribute() {
        return nameAttribute;
    }

    public static Optional<Oauth2Provider> fromName(String name) {
        return Arrays.stream(Oauth2Provider.class.getEnumConstants())
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
