package lamph11.web.centrerapi.common.io;

import lamph11.web.centrerapi.common.utils.PageUtils;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageableRequest {

    protected int pageNumber;
    protected int pageSize;
    protected String sort;

    public Pageable getPageable() {
        return PageUtils.buildPageable(pageNumber, pageSize, sort);
    }
}
