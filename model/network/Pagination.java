package com.koreait.day3.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    private Integer totalPages; // 전체 페이지 값
    private Long totalElements; // 전체 데이터
    private Integer currentPage; // 현재 페이지
    private Integer currentElements;
}