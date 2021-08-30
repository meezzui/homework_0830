package com.koreait.day3.service;


import com.koreait.day3.model.entity.OrderGroup;
import com.koreait.day3.model.enumclass.OrderType;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.Pagination;
import com.koreait.day3.model.network.request.OrderGroupApiRequest;
import com.koreait.day3.model.network.response.OrderGroupApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest orderGroupApiRequest = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .id(orderGroupApiRequest.getId())
                .orderAt(orderGroupApiRequest.getOrderAt())
                .orderType(OrderType.ALL)
                .status(orderGroupApiRequest.getStatus())
                .revName(orderGroupApiRequest.getRevName())
                .regDate(orderGroupApiRequest.getRegDate())
                .revAddress(orderGroupApiRequest.getRevAddress())
                .arrivalDate(orderGroupApiRequest.getArrivalDate())
                .paymentType(orderGroupApiRequest.getPaymentType())
                .totalPrice(orderGroupApiRequest.getTotalPrice())
                .totalQuantity(orderGroupApiRequest.getTotalQuantity())
                .build();
        OrderGroup orderGroup1 = baseRepository.save(orderGroup);
        return Header.OK(response(orderGroup1));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> response(orderGroup))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();
        Optional<OrderGroup> optional = baseRepository.findById(orderGroupApiRequest.getId());
        return optional.map(orderGroup -> {
            orderGroup.setId(orderGroupApiRequest.getId());
            orderGroup.setOrderType(orderGroupApiRequest.getOrderType());
            orderGroup.setOrderAt(orderGroupApiRequest.getOrderAt());
            orderGroup.setOrderType(OrderType.ALL);
            orderGroup.setStatus(orderGroupApiRequest.getStatus());
            orderGroup.setRevName(orderGroupApiRequest.getRevName());
            orderGroup.setRegDate(orderGroupApiRequest.getRegDate());
            orderGroup.setRevAddress(orderGroupApiRequest.getRevAddress());
            orderGroup.setArrivalDate(orderGroupApiRequest.getArrivalDate());
            orderGroup.setPaymentType(orderGroupApiRequest.getPaymentType());
            orderGroup.setTotalPrice(orderGroupApiRequest.getTotalPrice());
            orderGroup.setTotalQuantity(orderGroupApiRequest.getTotalQuantity());

            return orderGroup;
        }).map(orderGroup -> response(orderGroup))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);
        return optional.map(orderGroup -> {
            baseRepository.delete(orderGroup);
            return Header.OK();
    }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup){
        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(OrderType.ALL)
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .build();
        return body;
    }

    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {
        Page<OrderGroup> orderGroup = baseRepository.findAll(pageable);
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroup.stream()
                .map(orderGroup1 -> response(orderGroup1))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(orderGroup.getTotalPages())
                .totalElements(orderGroup.getTotalElements())
                .currentPage(orderGroup.getNumber())
                .currentElements(orderGroup.getNumberOfElements())
                .build();
        return Header.OK(orderGroupApiResponseList, pagination);
    }
}
