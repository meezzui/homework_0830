package com.koreait.day3.service;

import com.koreait.day3.model.entity.AdminUser;
import com.koreait.day3.model.entity.Item;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.Pagination;
import com.koreait.day3.model.network.request.AdminApiRequest;
import com.koreait.day3.model.network.response.AdminApiResponse;
import com.koreait.day3.model.network.response.ItemApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserApiLogicService extends BaseService<AdminApiRequest, AdminApiResponse, AdminUser>{
    @Override
    public Header<AdminApiResponse> create(Header<AdminApiRequest> request) {
        return null;
    }

    @Override
    public Header<AdminApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<AdminApiResponse> update(Header<AdminApiRequest> request) {
        return null;
    }

    @Override
    public Header<AdminApiResponse> delete(Long id) {
        return null;
    }
    public AdminApiResponse response(AdminUser adminUser){
        AdminApiResponse body = AdminApiResponse.builder()
                .id(adminUser.getId())
                .userid(adminUser.getUserid())
                .userpw(adminUser.getUserpw())
                .name(adminUser.getName())
                .status(adminUser.getStatus())
                .lastLoginAt(adminUser.getLastLoginAt())
                .regDate(adminUser.getRegDate())
                .build();
        return body;
    }
    public Header<List<AdminApiResponse>> search(Pageable pageable){
        Page<AdminUser> adminUser = baseRepository.findAll(pageable);
        List<AdminApiResponse> itemApiResponseList = adminUser.stream()
                .map(adminUser1 -> response(adminUser1))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(adminUser.getTotalPages())
                .totalElements(adminUser.getTotalElements())
                .currentPage(adminUser.getNumber())
                .currentElements(adminUser.getNumberOfElements())
                .build();
        return Header.OK(itemApiResponseList, pagination);

    }
}
