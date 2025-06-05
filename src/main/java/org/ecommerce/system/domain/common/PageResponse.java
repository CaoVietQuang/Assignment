package org.ecommerce.system.domain.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PageResponse<T> implements Serializable {
    private int currentPage;
    private long currentTotalElementsCount;
    private int pageSize;
    private int pagesCount;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<T> content;
}
