package ru.bikbaev.moneytransferapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableRequest implements Serializable {
    private Pageable pageable;
    private int pageNumber;
    private int pageSize;
}
