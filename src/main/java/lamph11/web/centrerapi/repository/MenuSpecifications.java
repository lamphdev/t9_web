package lamph11.web.centrerapi.repository;

import lamph11.web.centrerapi.entity.Menu;
import org.springframework.data.jpa.domain.Specification;

public class MenuSpecifications {

    public static Specification findDeepChildren(Menu menu) {
        String path = String.join("/", menu.getPath(), menu.getId());
        Specification specification = Specification.where((root, query, builder) -> builder.like(
                root.get("path"),
                String.join("", path, "%")
        ));
        return specification;
    }
}
