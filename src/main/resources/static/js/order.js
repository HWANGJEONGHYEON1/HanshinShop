(function ($) {


    let getPath = (order) => {
        let path = "/img/" + order.uploadPath + "/" + order.uuid + "_" + order.fileName;
        return path;
    }

    function convertDate(date) {
        let serverDate = new Date(date);
        return serverDate.getFullYear() + "." +serverDate.getMonth() + "." + serverDate.getDay();
    }

    let data;
    function orderList() {
        $.ajax({
            url: '/api/order/details',
            dataType: 'json',
            type: 'get',
            success: function (result) {

                let orderArray = []
                let orderTotal = 0;
                let date;
                data = result;

                for (let orders in result) {

                    let order = result[orders];
                    let path = getPath(order);
                    let total = 0;
                    date = convertDate(order.orderDate);
                    orderArray.push(`
                        <tr>
                            <td class="shoping__cart__price">
                                ${order.id}
                            </td>
                            <td class="shoping__cart__item">
                                <img src="${path}" alt="" style="width: 200px; height: 200px;">
                                <h5>${order.name}</h5>
                            </td>
                            <td class="shoping__cart__price">
                                ${order.orderPrice}
                            </td>
                            <td class="shoping__cart__price">
                                ${order.amount}
                            </td>
                            <td class="shoping__cart__total">
                                ${order.address}
                            </td>
                            <td class="shoping__cart__total">
                                ${date}
                            </td>
                            <td class="shoping__cart__total">
                                ${order.state}
                            </td>
                            <td class="shoping__cart__total">
                               <button class="ui-icon-cancel cancel" 
                                    data-orderid="${order.id}" 
                                    data-state="${order.state}"
                                    >
                                    주문취소
                               </button>
                            </td>
                        </tr>
                    `);
                    console.log(order.state);
                    if (order.state == "ORDER") {
                        total = order.orderPrice * order.amount;
                    }
                    orderTotal += total;
                }
                $("#orders").empty();
                $("#orders").append(orderArray);

                $("#totalPrice").text(orderTotal);
            }
        });
    }

    $(document).on("click", ".cancel", function (e) {
        console.log($(this).data("orderid"));
        let orderId = $(this).data("orderid");
        let state = $(this).data("state");

        if (state == 'CANCEL') {
            alert("이미 주문 취소 되었습니다.");
            return;
        }

        $.ajax({
            url: '/api/order/cancel/' + orderId,
            method: "PATCH",
            dataType: 'text',
            success: function (result) {
                alert('주문 번호 '+ orderId+ ' 취소되었습니다.');
                orderList();
            }
        })
    })


    $("#orderAllCancelBtn").on("click", function(e) {
        console.log("## orederCancel btn click");
        $.ajax({
            url: '/api/order/cancel',
            method: "PATCH",
            dataType: 'text',
            success: function (result) {
                alert("주문취소 되었습니다.");
                orderList();
            }
        });
    })

    orderList();

})(jQuery);