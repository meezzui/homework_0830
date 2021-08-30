package com.koreait.day3.service;

import com.koreait.day3.model.front.AdminMenu;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminMenuService {
    public List<AdminMenu> getAdminMenu(){
        return Arrays.asList(

                AdminMenu.builder().title("고객관리").url("/pages/user").code("users").img("home").build(),
                AdminMenu.builder().title("관리자관리").url("/pages/adminuser").code("adminuser").img("favorite_border").build(),
                AdminMenu.builder().title("카테고리관리").url("/pages/category").code("category").img("star").build(),
                AdminMenu.builder().title("아이템관리").url("/pages/item").code("item").img("grade").build(),
                AdminMenu.builder().title("구매정보관리").url("/pages/ordergroup").code("ordergroup").img("face_retouching_natural").build(),
                AdminMenu.builder().title("업체관리").url("/pages/partner").code("partner").img("favorite").build()
        );
    }
}
