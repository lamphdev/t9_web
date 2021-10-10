package lamph11.web.centrerapi.common.io;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private List<T> content;

    public static <C> PageResponse<C> from(Page<C> input) {
        PageResponse<C> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(input.getNumber());
        pageResponse.setPageSize(input.getSize());
        pageResponse.setContent(input.getContent());
        pageResponse.setTotalPages(input.getTotalPages());
        pageResponse.setTotalElements(input.getTotalElements());
        return pageResponse;
    }
}
