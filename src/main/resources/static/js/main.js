/*  ---------------------------------------------------
    Template Name: Ogani
    Description:  Ogani eCommerce  HTML Template
    Author: Colorlib
    Author URI: https://colorlib.com
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';

(function ($) {
    /*------------------
        Preloader
    --------------------*/

    initCategory();
    initMainShop();
    initMainRecommend();

    $("#categories").on("click", "li", function(){
        let id = $(this).attr("id");
        $.ajax({
            url: "/api/category/" + id,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                makeMainShopDiv(result);
            }
        });
    });

    let getPath = (goodsAttach) => {
        let path = "/img/" + goodsAttach.uploadPath + "/" + goodsAttach.uuid + "_" + goodsAttach.fileName;
        return path;
    }

    function initCategory() {
        $.ajax({
            url: '/api/categories',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                let categoryArray = [];
                for (let categories in result) {
                    let category = result[categories];
                    categoryArray.push(`<li id="${category.id}">${category.name}</li>`);
                }
                $("#categories").append(categoryArray);
            }
        });
    }

    function initMainShop() {
        $.ajax({
            url: '/api/main',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                makeMainShopDiv(result);
            }
        });
    }

    function initMainRecommend() {
        $.ajax({
            url: '/api/recommend',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                let shopRecommendArray = [];
                shopRecommendArray.push(`<div class="categories__slider owl-carousel">`)
                for (let recommend in result) {
                    let recommends = result[recommend];
                    let path = getPath(recommends.attachList[0]);
                    let detailUrl = "/goods/" + recommends.id;
                    shopRecommendArray.push(`
                    <div class="col-lg-3">
                        <div class="categories__item set-bg" data-setbg="${path}">
                            <img src="${path}" />
                            <h5><a href="${detailUrl}">[추천] ${recommends.name}</a></h5>
                        </div>
                    </div>
                `);
                }
                shopRecommendArray.push(`</div>`);
                $("#shopRecommend").append(shopRecommendArray);
            }
        });
    }

    function makeMainShopDiv(result) {
        let pageMaker = result.pageMaker;
        let listData = result.goodsAll;
        let shopArray = [];
        for (let shop in listData) {
            let goods = listData[shop];
            let path = getPath(goods.attachList[0]);
            let detailUrl = "/goods/" + goods.id;
            shopArray.push(`
                <div class="col-lg-3 col-md-4 col-sm-6 mix ${goods.categoryId}">
                    <div class="featured__item">
                        <div class="featured__item__pic set-bg" data-setbg="${path}">
                            <img src="${path}" />
                            <ul class="featured__item__pic__hover">
<!--                                <li><a href="#"><i class="fa fa-heart"></i></a></li>-->
                                <li>
                                    <a id="saveCartBtn" 
                                        data-id="${goods.id}"
                                        data-amount="1"
                                        ><i class="fa fa-shopping-cart"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="featured__item__text">
                            <h6><a href="${detailUrl}">${goods.name}</a></h6>
                            <h5>${goods.price}</h5>
                        </div>
                    </div>
                </div>
            `);
        }

        if (pageMaker.prev) {
            $(".pagination").append(`<li class="paginate_button previous"><a href="${pageMaker.prev - 1}">Previous</a></li>`);
        }

        for (let i = pageMaker.startPage; i < pageMaker.endPage; i++) {
            $(".pagination").append(`
                <li class="paginate_button ${pageMaker.criteria.pageNum == i ? "active" : ""}">
                    <a href="${i}" data-pagenum="${i}" data-amount="${pageMaker.criteria.amount}"> ${i} 
                    </a>
                </li>`);

        }

        if (pageMaker.next) {
            $(".pagination").append(`<li class="paginate_button next"><a href="${pageMaker.endPage + 1}">Next</a></li>`);
        }

        $("#shopMain").empty();
        $("#shopMain").append(shopArray);
    }

    $(document).on("click", ".paginate_button a", function (e) {
        e.preventDefault();
        let pageNum = $(this).data("pagenum");
        let amount = $(this).data("amount");

        $.ajax({
            url: '/api/main?pageNum=' + pageNum + "&amount=" + amount,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                $(".pagination").empty();
                makeMainShopDiv(result);
            }
        });
    })

    $(document).on("click", "#saveCartBtn", function (e) {
        let goodsId = $(this).data("id");
        let amount = $(this).data("amount") == 1 ? $(this).data("amount") : $("#amount").val();
        let userId = $("#username").data("id");
        if (!userId) {
            alert("로그인 후 시도하세요");
            return;
        }

        let data = {
            userId : userId,
            goodsId : goodsId,
            amount : amount
        };

        console.log(data);

        $.ajax({
            url: '/api/cart',
            dataType: 'text',
            contentType: 'application/json',
            data: JSON.stringify(data),
            type: 'post',
            success: function (e) {
                console.log(e);
                alert("장바구니에 담겼습니다.");
                initCartCount(userId);
            }
        });
    });

    function initCartCount(userId) {
        $.ajax({
            url: "/api/cart/count/" + userId,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                $("#cartCount").empty();
                $("#cartCount").append(`<a href="/member/cart?userId=${userId}"><i class="fa fa-shopping-bag"></i> <span>${result}</span></a>`);
            }
        });
    }

})(jQuery);