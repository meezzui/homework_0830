package com.koreait.day3.service;

import com.koreait.day3.ifs.CrudInterface;
import com.koreait.day3.model.entity.Item;
import com.koreait.day3.model.entity.OrderGroup;
import com.koreait.day3.model.enumclass.ItemStatus;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.Pagination;
import com.koreait.day3.model.network.request.ItemApiRequest;
import com.koreait.day3.model.network.request.UserApiRequest;
import com.koreait.day3.model.network.response.ItemApiResponse;
import com.koreait.day3.model.network.response.OrderGroupApiResponse;
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
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {
    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .id(itemApiRequest.getId())
                .name(itemApiRequest.getName())
                .status(ItemStatus.REGISTERED)
                .title(itemApiRequest.getTitle())
                .content(itemApiRequest.getContent())
                .price(itemApiRequest.getPrice())
                .regDate(itemApiRequest.getRegDate())
                .updateDate(itemApiRequest.getRegDate())
                .build();
        Item item1 = baseRepository.save(item);
        return Header.OK(response(item1));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(item -> response(item))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();
        Optional<Item> optional = baseRepository.findById(itemApiRequest.getId());
        return optional.map(item -> {
            item.setId(itemApiRequest.getId());
            item.setName(itemApiRequest.getName());
            item.setStatus(ItemStatus.REGISTERED);
            item.setTitle(itemApiRequest.getTitle());
            item.setContent(itemApiRequest.getContent());
            item.setPrice(itemApiRequest.getPrice());
            item.setRegDate(itemApiRequest.getRegDate());
            item.setUpdateDate(itemApiRequest.getRegDate());

            return item;

        }).map(item -> baseRepository.save(item))
                .map(item -> response(item))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        Optional<Item> optional = baseRepository.findById(id);
        return optional.map(item ->{
            baseRepository.delete(item);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public ItemApiResponse response(Item item){
        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .status(ItemStatus.REGISTERED)
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .regDate(item.getRegDate())
                .updateDate(item.getUpdateDate())
                .build();
        return body;
    }

    public Header<List<ItemApiResponse>> search(Pageable pageable){
        Page<Item> item = baseRepository.findAll(pageable);
        List<ItemApiResponse> itemApiResponseList = item.stream()
                .map(items -> response(items))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(item.getTotalPages())
                .totalElements(item.getTotalElements())
                .currentPage(item.getNumber())
                .currentElements(item.getNumberOfElements())
                .build();
        return Header.OK(itemApiResponseList, pagination);

    }
}
