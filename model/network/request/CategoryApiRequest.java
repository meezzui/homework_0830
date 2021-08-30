package com.koreait.day3.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryApiRequest {
    private Long id;
    private String type;
    private String title;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String updateBy;
}
