package com.koreait.day3.controller.api;

import com.koreait.day3.controller.CrudController;
import com.koreait.day3.model.entity.AdminUser;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.request.AdminApiRequest;
import com.koreait.day3.model.network.response.AdminApiResponse;
import com.koreait.day3.model.network.response.ItemApiResponse;
import com.koreait.day3.service.AdminUserApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adminuser")
@RequiredArgsConstructor
public class AdminApiController extends CrudController<AdminApiRequest, AdminApiResponse, AdminUser> {
    private final AdminUserApiLogicService adminUserApiLogicService;

    @Override
    @PostMapping("")
    public Header<AdminApiResponse> create(@RequestBody Header<AdminApiRequest> request) {
        return adminUserApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<AdminApiResponse> read(@PathVariable(name="id") Long id) {
        return adminUserApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<AdminApiResponse> update(@RequestBody Header<AdminApiRequest> request) {
        return adminUserApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header<AdminApiResponse> delete(@PathVariable(name="id") Long id) {
        return super.delete(id);
    }

    @GetMapping("")
    public Header<List<AdminApiResponse>> findAll(@PageableDefault(sort={"id"},direction= Sort.Direction.DESC) Pageable pageable ){
        return adminUserApiLogicService.search(pageable);
    }
}
