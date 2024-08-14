package org.example.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PagedStockItemsResponse {

    private Integer totalPages;

    private Long totalElements;

    private Integer pageNumber;

    private Integer pageSize;

    private List<StockItemResponse> content;

    public PagedStockItemsResponse(Page<StockItemResponse> stockItemResponses) {
        this.totalPages = stockItemResponses.getTotalPages();
        this.totalElements = stockItemResponses.getTotalElements();
        this.pageNumber = stockItemResponses.getNumber();
        this.pageSize = stockItemResponses.getSize();
        this.content = stockItemResponses.getContent();
    }
}
