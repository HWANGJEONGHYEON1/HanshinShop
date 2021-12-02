insert into user (password, name, tel, address, email, birth)
values( 'test', 'test', '010-1111-1111', '', 'test', '', );

insert into user_role(email, role) values('test', 'ROLE_MEMBER');

insert into user (password, name, tel, address, email, birth)
values( 'test1', 'test1', '010-1111-1111', '', 'test1', '');

insert into user_role(email, role) values('test1', 'ROLE_MEMBER');

insert into category(id, name) values(1, 'organic');
insert into category(id, name) values(2, 'fruit');

insert into goods(category_id, price, discount_rate, name, description, review_count)
values (1, 2000, 0.1, '파프리카', '맛있는 파프리카', 3);


insert into goods(category_id, price, discount_rate, name, description, review_count)
values (2, 33000, 0.2, '샤인머스켓', '대한민국 당도 1등', 100);
