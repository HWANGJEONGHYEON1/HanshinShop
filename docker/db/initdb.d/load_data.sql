insert into `kurly`.user (password, name, tel, address, email, birth)
values('$2a$10$reDtXMcaM6XlOmmMlpuuWuuVNpgWa7e4IVFui1eQ5qaZZ47YqF8tW', 'test', '010-1111-1111', '', 'test@gmail.com', ''); -- test
insert into `kurly`.user_role(email, role) values('test@gmail.com', 'ROLE_MEMBER');

insert into user (password, name, tel, address, email, birth)
values('$2a$10$LGVmPHe1h4VKdGUhyExBN.334JTi7PxXLoJ80gijQr2fb8W5qBhE6', 'test1', '010-1111-1111', '', 'test1@naver.com', ''); -- test1
insert into `kurly`.user_role(email, role) values('test1@naver.com', 'ROLE_ADMIN');

insert into `kurly`.category(id, name) values(1, 'organic');
insert into `kurly`.category(id, name) values(2, 'fruit');
insert into `kurly`.category(id, name) values(3, 'beef');
insert into `kurly`.category(id, name) values(4, 'cereal');

insert into `kurly`.goods(category_id, price, discount_rate, name, description, review_count)
values (1, 2000, 0.1, '파프리카', '맛있는 사과', 3);
insert into `kurly`.attach(uuid, upload_path, file_name, goods_id)
values ('8b127c66-8eef-477f-accf-9604b2ee3dd5', 'http://kormedi.com/wp-content/uploads/2021/01/gettyimages-1256718654-580x482.jpg/', '사과', 1);

insert into `kurly`.goods(category_id, price, discount_rate, name, description, review_count)
values (2, 33000, 0.2, '샤인머스켓', '대한민국 당도 1등', 100);
insert into `kurly`.attach(uuid, upload_path, file_name, goods_id)
values ('af7affeb-8342-4c64-af44-8f81196a3074', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVFjOGNPXNc3JVvoxPf6VIpate-aDkyt6kxQ&usqp=CAU/', '귤', 2);

insert into `kurly`.cart(goods_id, amount, user_id)
values( 1, 10,  1);

insert into `kurly`.cart(goods_id, amount, user_id)
values( 2, 10,  1);

insert into `kurly`.cart(goods_id, amount, user_id)
values( 2, 10,  2);

insert into `kurly`.orders(user_id, state) values(1, 'ORDER');
insert into `kurly`.order_goods(order_id, goods_id, order_price, amount) values(1, 1, 2000, 1);

insert into `kurly`.orders(user_id, state) values(2, 'ORDER');
insert into `kurly`.order_goods(order_id, goods_id, order_price, amount) values(2, 2, 33000, 1);