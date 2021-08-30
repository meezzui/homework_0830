package com.koreait.day3.service;

import com.koreait.day3.controller.CrudController;
import com.koreait.day3.ifs.CrudInterface;
import com.koreait.day3.model.entity.Category;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.Pagination;
import com.koreait.day3.model.network.request.CategoryApiRequest;
import com.koreait.day3.model.network.request.UserApiRequest;
import com.koreait.day3.model.network.response.CategoryApiResponse;
import com.koreait.day3.model.network.response.ItemApiResponse;
import com.koreait.day3.model.network.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryApiLogicService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category> {


    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {

        CategoryApiRequest categoryApiRequest = request.getData();

        Category category = Category.builder()
                .id(categoryApiRequest.getId())
                .type(categoryApiRequest.getType())
                .title(categoryApiRequest.getTitle())
                .regDate(categoryApiRequest.getRegDate())

                .build();
        Category category1 = baseRepository.save(category);
        return Header.OK(response(category1));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(category -> response(category))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest categoryApiRequest = request.getData();
        Optional<Category> optional = baseRepository.findById(categoryApiRequest.getId());
        return optional.map(category -> {
            category.setId(categoryApiRequest.getId());
            category.setRegDate(categoryApiRequest.getRegDate());
            category.setType(categoryApiRequest.getType());
            category.setTitle(categoryApiRequest.getTitle());

            return category;
        }).map(category -> baseRepository.save(category))
                .map(category -> response(category))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Category> optional = baseRepository.findById(id);
        return optional.map(category -> {
            baseRepository.delete(category);
            return  Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public CategoryApiResponse response(Category category){
        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .regDate(category.getRegDate())
                .title(category.getTitle())
                .type(category.getType())
                .build();
        return body;
    }

    public Header<List<CategoryApiResponse>> search(Pageable pageable){
        Page<Category> category = baseRepository.findAll(pageable);
        List<CategoryApiResponse> categoryApiResponseList = category.stream()
                .map(categorys -> response(categorys))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(category.getTotalPages())
                .totalElements(category.getTotalElements())
                .currentPage(category.getNumber())
                .currentElements(category.getNumberOfElements())
                .build();
        return Header.OK(categoryApiResponseList, pagination);
    }
}
