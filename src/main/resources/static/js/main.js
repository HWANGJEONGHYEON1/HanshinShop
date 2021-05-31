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

    initCategory();
    initMainShop();
    initMainRecommend();

    $("#categories").on("click", "li", function(){
        let id = $(this).attr("id");
        $.ajax({
            url: "/goods/category/" + id,
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
            url: '/goods/categories',
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
            url: '/goods/main',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                makeMainShopDiv(result);
            }
        });
    }

    function initMainRecommend() {
        $.ajax({
            url: '/goods/recommend',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                let shopRecommendArray = [];
                shopRecommendArray.push(`<div class="categories__slider owl-carousel">`)
                for (let recommend in result) {
                    let recommends = result[recommend];
                    let path = getPath(recommends.attachList[0]);
                    let detailUrl = "/goods-detail/" + recommends.id;
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
        let shopArray = [];
        for (let shop in result) {
            let goods = result[shop];
            let path = getPath(goods.attachList[0]);
            let detailUrl = "/goods-detail/" + goods.id;
            shopArray.push(`
                    <div class="col-lg-3 col-md-4 col-sm-6 mix ${goods.categoryId}">
                    <div class="featured__item">
                        <div class="featured__item__pic set-bg" data-setbg="${path}">
                            <img src="${path}" />
                            <ul class="featured__item__pic__hover">
                                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
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
        $("#shopMain").empty();
        $("#shopMain").append(shopArray);
    }
})(jQuery);