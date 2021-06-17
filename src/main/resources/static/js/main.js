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
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");

        /*------------------
            Gallery filter
        --------------------*/
        $('.featured__controls li').on('click', function () {
            $('.featured__controls li').removeClass('active');
            $(this).addClass('active');
        });
        if ($('.featured__filter').length > 0) {
            var containerEl = document.querySelector('.featured__filter');
            var mixer = mixitup(containerEl);
        }
    });

    /*------------------
        Background Set
    --------------------*/
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    //Humberger Menu
    $(".humberger__open").on('click', function () {
        $(".humberger__menu__wrapper").addClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").addClass("active");
        $("body").addClass("over_hid");
    });

    $(".humberger__menu__overlay").on('click', function () {
        $(".humberger__menu__wrapper").removeClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").removeClass("active");
        $("body").removeClass("over_hid");
    });

    /*------------------
		Navigation
	--------------------*/
    $(".mobile-menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    /*-----------------------
        Categories Slider
    ------------------------*/
    $(".categories__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 4,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            0: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 3,
            },

            992: {
                items: 4,
            }
        }
    });


    $('.hero__categories__all').on('click', function(){
        $('.hero__categories ul').slideToggle(400);
    });

    /*--------------------------
        Latest Product Slider
    ----------------------------*/
    $(".latest-product__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------------
        Product Discount Slider
    -------------------------------*/
    $(".product__discount__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 3,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            320: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 2,
            },

            992: {
                items: 3,
            }
        }
    });

    /*---------------------------------
        Product Details Pic Slider
    ----------------------------------*/
    $(".product__details__pic__slider").owlCarousel({
        loop: true,
        margin: 20,
        items: 4,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------
		Price Range Slider
	------------------------ */
    var rangeSlider = $(".price-range"),
        minamount = $("#minamount"),
        maxamount = $("#maxamount"),
        minPrice = rangeSlider.data('min'),
        maxPrice = rangeSlider.data('max');
    rangeSlider.slider({
        range: true,
        min: minPrice,
        max: maxPrice,
        values: [minPrice, maxPrice],
        slide: function (event, ui) {
            minamount.val('$' + ui.values[0]);
            maxamount.val('$' + ui.values[1]);
        }
    });
    minamount.val('$' + rangeSlider.slider("values", 0));
    maxamount.val('$' + rangeSlider.slider("values", 1));

    /*--------------------------
        Select
    ----------------------------*/
    $("select").niceSelect();

    /*------------------
		Single Product
	--------------------*/
    $('.product__details__pic__slider img').on('click', function () {

        var imgurl = $(this).data('imgbigurl');
        var bigImg = $('.product__details__pic__item--large').attr('src');
        if (imgurl != bigImg) {
            $('.product__details__pic__item--large').attr({
                src: imgurl
            });
        }
    });

    /*-------------------
		Quantity change
	--------------------- */
    var proQty = $('.pro-qty');
    proQty.prepend('<span class="dec qtybtn">-</span>');
    proQty.append('<span class="inc qtybtn">+</span>');
    proQty.on('click', '.qtybtn', function () {
        var $button = $(this);
        var oldValue = $button.parent().find('input').val();
        if ($button.hasClass('inc')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        $button.parent().find('input').val(newVal);
    });

    initUserInfo();
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

    function initUserInfo() {
        $.ajax({
            url: "/api/user/info",
            dataType: 'json',
            type: 'get',
            success: function (result) {
                let loginDivArray = [];
                if (!$.isEmptyObject(result)) {
                    initCartCount(result.id);
                    initOrderCount();
                    loginDivArray.push(`
                    <div class="header__top__right__language">
                        <div id="username" data-id="${result.id}">${result.name}</div>
                        <span class="arrow_carrot-down"></span>
                        <ul>
                            <li><button type="button" id="logoutButton"/><i class="fa fa-user"></i> Logout</li>
                        </ul>
                    </div>
                `);

                } else {
                    loginDivArray.push(`
                    <div class="header__top__right__auth">
                        <span><a href="/login"><i class="fa fa-user"></i> Login</a></span>
                    </div>                
                `);
                }

                $("#loginDiv").append(loginDivArray);
            }
        });
    }

    function initOrderCount() {
        $.ajax({
            url: "/api/order/count",
            dataType: 'json',
            type: 'get',
            success: function (result) {
                console.log("# " + result);
                $("#orderCount").empty();
                $("#orderCount").append(`<a href="/member/order"><i class="fa fa-heart"></i> <span>${result}</span></a>`);
            }
        });
    }

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

        $.ajax({
            url: '/api/cart/' + userId,
            dataType: 'text',
            contentType: 'application/json',
            data: JSON.stringify(data),
            type: 'post',
            success: function () {
                alert("장바구니에 담겼습니다.");
                initCartCount(userId);
            }
        });
    });

})(jQuery);