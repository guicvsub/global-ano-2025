package com.fiap.api.dto;

import org.springframework.data.domain.Page;
import lombok.Data;
import java.util.List;

@Data
public class PageableDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;

    public static <T> PageableDTO<T> fromPage(Page<T> page) {
        PageableDTO<T> dto = new PageableDTO<>();
        dto.setContent(page.getContent());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalElements(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setLast(page.isLast());
        dto.setFirst(page.isFirst());
        return dto;
    }
} 