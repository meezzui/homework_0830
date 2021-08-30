package com.koreait.day3.model.network.request;

import com.koreait.day3.model.network.response.UserApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderInfoApiRequest {
    private UserApiResponse userApiResponse;
}
