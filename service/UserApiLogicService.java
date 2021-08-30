package com.koreait.day3.service;

import com.koreait.day3.ifs.CrudInterface;
import com.koreait.day3.model.entity.OrderGroup;
import com.koreait.day3.model.entity.Users;
import com.koreait.day3.model.enumclass.UserStatus;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.Pagination;
import com.koreait.day3.model.network.request.UserApiRequest;
import com.koreait.day3.model.network.response.ItemApiResponse;
import com.koreait.day3.model.network.response.OrderGroupApiResponse;
import com.koreait.day3.model.network.response.UserApiResponse;
import com.koreait.day3.model.network.response.UserOrderInfoApiResponse;
import com.koreait.day3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // 서비스레이어, 내부에서 자바로직을 처리함
@RequiredArgsConstructor
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, Users> {

    private final OrderGroupApiLogicService orderGroupApiLogicService;
    private final ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        Users user = Users.builder()
                .userid(userApiRequest.getUserid())
                .userpw(userApiRequest.getUserpw())
                .hp(userApiRequest.getHp())
                .email(userApiRequest.getEmail())
                .status(UserStatus.REGISTERED)
                .build();
        Users newUser = baseRepository.save(user);
        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();
        Optional<Users> optional = baseRepository.findById(userApiRequest.getId());
        return optional.map(user -> {

            user.setUserid(userApiRequest.getUserid());
            user.setUserpw(userApiRequest.getUserpw());
            user.setHp(userApiRequest.getHp());
            user.setEmail(userApiRequest.getEmail());
            user.setStatus(userApiRequest.getStatus());

            return user;
        }).map(user -> baseRepository.save(user))
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Users> optional = baseRepository.findById(id);

        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private UserApiResponse response(Users user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .userid(user.getUserid())
                .userpw(user.getUserpw())
                .email(user.getEmail())
                .hp(user.getHp())
                .regDate(user.getRegDate())
                .status(user.getStatus())
                .build();
        return userApiResponse;
    }

//    public Header<UserOrderInfoApiResponse> orderInfo(Long id){
//        Users user = baseRepository.getById(id);
//        UserApiResponse userApiResponse = response(user);
//
//        List<OrderGroup> orderGroupList = user.getOrderGroupList();
//        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
//                .map(orderGroup -> {
//                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();
//                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
//                            .map(detail -> dcetail.getItem())
//                            .map(item -> itemApiLogicService.response(item).getData())
//                            .collect(Collectors.toList()); // collect는 stream의 데이터를 변형등의 처리를 하고 원하는 자료형으로 변환
//
//                        orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
//                        return orderGroupApiResponse;
//                })
//                .collect(Collectors.toList());
//
//        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);
//        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
//                .userApiResponse(userApiResponse)
//                .build();
//        return Header.OK(userOrderInfoApiResponse);
//    }

    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<Users> user = baseRepository.findAll(pageable);
        List<UserApiResponse> userApiResponseList = user.stream()
                .map(users -> response(users))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(user.getTotalPages())
                .totalElements(user.getTotalElements())
                .currentPage(user.getNumber())
                .currentElements(user.getNumberOfElements())
                .build();
        return Header.OK(userApiResponseList, pagination);
    }

}
