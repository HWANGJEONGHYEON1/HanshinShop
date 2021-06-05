(function ($) {
    let userId = $("#userId").val();

    let getPath = (attach) => {
        let path = "/img/" + attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName;
        return path;
    }

    function cartList() {

        $.ajax({
            url: '/member/cart/' + userId,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                let cartArray = [];
                let cartTotal = 0;
                for (let carts in result) {
                    let cart = result[carts];
                    let path = getPath(cart.attachList[0]);
                    let total = cart.price * cart.amount;
                    cartTotal += total;
                    cartArray.push(`
                        <tr>
                            <td class="shoping__cart__item">
                                <img src="${path}" alt="" style="width: 200px; height: 200px;">
                                <h5>${cart.goodsName}</h5>
                            </td>
                            <td class="shoping__cart__price">
                                ${cart.price}
                            </td>
                            <td class="shoping__cart__quantity">
                                <div class="quantity">
                                    <div class="pro-qty">
                                        <input type="text" value="${cart.amount}">
                                    </div>
                                </div>
                            </td>
                            <td class="shoping__cart__total">
                                ${total}
                            </td>
                            <td class="shoping__cart__item__close">
                                <span class="icon_close" id="deleteBtn" data-id="${cart.id}"></span>
                            </td>
                        </tr>
                    `);
                }
                $("#carts").empty();
                $("#carts").append(cartArray);
                $("#totalPrice").text(cartTotal);
            }
        });
    }

    cartList();

    $(document).on("click", "#deleteBtn", function(e) {

        let cartId = $(this).data("id");
        $.ajax({
            url: "/member/cart/" + cartId,
            method: "delete",
            dataType: "text",
            success: function () {
                cartList();
            }
        })
    })

    $("#deleteAllBtn").on("click", function(e) {
        console.log("deleteAll");
        $.ajax({
            url: "/member/carts/" + userId,
            method: "delete",
            dataType: "text",
            success: function (result) {
                alert("전체삭제 되었습니다.");
                location.href="/";
            }
        });
    })
})(jQuery);