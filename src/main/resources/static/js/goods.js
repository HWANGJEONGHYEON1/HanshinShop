'use strict';

(function ($) {

        let getPath = (goodsAttach) => {
            let path = "/img/" + goodsAttach.uploadPath + "/" + goodsAttach.uuid + "_" + goodsAttach.fileName;
            return path;
        }

        if ($("#goodsCategoryId").val()) {
            $.ajax({
                url: '/goods-detail/category/' + $("#goodsCategoryId").val(),
                dataType: 'json',
                type: 'get',
                success: function (result) {
                let shopRecommendArray = [];
                shopRecommendArray.push(`<div class="categories__slider owl-carousel">`);

                let relatedProduct = result[0];
                let path = getPath(relatedProduct.attachList[0]);
                let detailUrl = "/goods/" + relatedProduct.id;
                shopRecommendArray.push(`
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="${path}">
                                <img src="${path}" />
                                <ul class="product__item__pic__hover">
                                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="${detailUrl}">${relatedProduct.name}</a></h6>
                                <h5>${relatedProduct.price}</h5>
                            </div>
                        </div>
                    </div>
                `);
                shopRecommendArray.push(`</div>`);
                $("#relatedProduct").append(shopRecommendArray);
                }
             });
        }
})(jQuery);
