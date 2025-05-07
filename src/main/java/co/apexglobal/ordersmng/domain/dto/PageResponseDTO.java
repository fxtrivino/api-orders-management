package co.apexglobal.ordersmng.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}

