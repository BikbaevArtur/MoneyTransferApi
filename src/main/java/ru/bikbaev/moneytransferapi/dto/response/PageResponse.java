package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> implements Serializable {
    private List<T> content;

    private int pageNumber;        // Номер страницы (0-based)
    private int pageSize;          // Размер страницы
    private boolean last;          // true = это последняя страница
    private int totalPages;        // Всего страниц
    private long totalElements;    // Всего элементов
    private int numberOfElements;  // Количество элементов на этой странице
}