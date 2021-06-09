(function ($) {


    let getPath = (order) => {
        let path = "/img/" + order.uploadPath + "/" + order.uuid + "_" + order.fileName;
        return path;
    }

    let data;
    function orderList() {
        $.ajax({
            url: '/member/order-details',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                console.log(result)
                let orderArray = []
                let orderTotal = 0;
                data = result;
                for (let orders in result) {
                    let order = result[orders];
                    let path = getPath(order);
                    let total = order.orderPrice * order.amount;
                    orderTotal += total;
                    orderArray.push(`
                        <tr>
                            <td class="shoping__cart__item">
                                <img src="${path}" alt="" style="width: 200px; height: 200px;">
                                <h5>${order.name}</h5>
                            </td>
                            <td class="shoping__cart__price">
                                ${total}
                            </td>
                            <td class="shoping__cart__price">
                                ${order.amount}
                            </td>
                            <td class="shoping__cart__total">
                                ${order.address}
                            </td>
                            <td class="shoping__cart__total">
                                ${order.orderDate}
                            </td>
                            <td class="shoping__cart__total">
                                ${order.state}
                            </td>
                        </tr>
                    `);
                }
                $("#orders").empty();
                $("#orders").append(orderArray);
                $("#totalPrice").text(orderTotal);
            }
        });
    }

    $("#orderCancelBtn").on("click", function(e) {
        console.log("## orederCancel btn click");
        $.ajax({
            url: '/member/order/cancel',
            method: "patch",
            dataType: 'text',
            success: function (result) {
                console.log(result);
                alert("주문취소 되었습니다.");
                orderList();
            }
        });
    })

    orderList();

})(jQuery);