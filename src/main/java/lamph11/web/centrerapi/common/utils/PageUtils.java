package lamph11.web.centrerapi.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PageUtils {

    public static Pageable buildPageable(int pageNumber, int pageSize) {
        return buildPageable(pageNumber, pageSize, null);
    }

    public static Pageable buildPageable(int pageNumber, int pageSize, String sort) {
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize <= 0) pageSize = 50;

        if (null == sort || sort.isEmpty())
            return PageRequest.of(pageNumber, pageSize);

        List<Sort.Order> orders = Stream.of(sort.split(","))
                .map(sortDef -> sortDef.trim())
                .map(sortDef -> {
                            if (sortDef.startsWith("-"))
                                return Sort.Order.desc(sortDef.replaceFirst("-", ""));
                            return Sort.Order.asc(sortDef.replaceFirst("-", ""));
                        }
                ).collect(Collectors.toList());
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }
}
