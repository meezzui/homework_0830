package com.koreait.day3.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminApiResponse {
    private Long id;        // 일렬번호
    private String userid;  // 아이디
    private String userpw;  // 비밀번호
    private String name;    // 이름
    private String status;  // 상태
    private LocalDateTime lastLoginAt;  // 마지막 접속시간
    private LocalDateTime regDate;      // 가입 날짜

}
