(function ($) {
    let indexBtn = [];
    let pagination = {
        total_pages : 0,
        total_elements :0,
        current_page :0,
        current_elements: 0
    };

    //페이지 정보
    let showPage = new Vue({
        el: "#showPage",
        data: {
            totalElements: {},
            currentPage: {}
        }
    });

    // 데이터 리스트
    let itemList = new Vue({
        el : '#itemList',
        data : {
            itemList : {}
        }
    });

    //html을 다 읽으면 동작해라(이 페이지에서 가장 먼저 실행됨)
    $(document).ready(function(){
        searchStart(0)
    });

    function searchStart(index){
        console.log("index : " + index);
        $.get("/api/partner?page="+index, function(response){
            console.dir(response);

            indexBtn = [];
            pagination = response.pagination;

            //전체 페이지
            showPage.totalElements = pagination.currentElements;
            showPage.currentPage = pagination.currentPage + 1; //page가 0부터 시작해서 1플러스 해주기

            //검색 데이터
            itemList.itemList = response.data;


        });
    }

})(jQuery);
