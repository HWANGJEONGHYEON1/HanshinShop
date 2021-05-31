
/**********************************/
/* Table Name: Order */
/**********************************/
CREATE TABLE `Order`(
                        id INT AUTO_INCREMENT primary key,
                        user_id INT,
                        name VARCHAR(100),
                        tel VARCHAR(100),
                        addr VARCHAR(100),
                        comment VARCHAR(100),
                        state VARCHAR(100),
                        date TIMESTAMP DEFAULT now(),
                        price INT
);

/**********************************/
/* Table Name: User */
/**********************************/
CREATE TABLE User(
                     id INT AUTO_INCREMENT primary key,
                     account VARCHAR(100) NOT NULL,
                     name VARCHAR(100) NOT NULL,
                     pwd VARCHAR(100) NOT NULL,
                     tel VARCHAR(100),
                     addr VARCHAR(100),
                     email VARCHAR(100),
                     birth VARCHAR(100),
                     join_date TIMESTAMP DEFAULT now()
);

/**********************************/
/* Table Name: OrdersGoods */
/**********************************/
CREATE TABLE OrdersGoods(
                            orders_id VARCHAR(100),
                            goods_cont INT,
                            goods_id INT
);

/**********************************/
/* Table Name: Goods */
/**********************************/
CREATE TABLE Goods(
                      id INT AUTO_INCREMENT primary key,
                      category_id INT,
                      price INT,
                      discount_rate INT,
                      name VARCHAR(100),
                      detail VARCHAR(100),
                      img_uuid VARCHAR(100),
                      reg VARCHAR(100),
                      info VARCHAR(100),
                      sale_begin TIMESTAMP,
                      sale_end TIMESTAMP,
                      reg_date TIMESTAMP DEFAULT now()
);

/**********************************/
/* Table Name: Cart */
/**********************************/
CREATE TABLE Cart(
                     id INT AUTO_INCREMENT primary key,
                     goods_id INT,
                     goods_count INT,
                     user_id INT
);

/**********************************/
/* Table Name: Category */
/**********************************/
CREATE TABLE Category(
                         id INT AUTO_INCREMENT primary key,
                         name VARCHAR(10),
                         goods_id INT
);

/**********************************/
/* Table Name: Review */
/**********************************/
CREATE TABLE Review(
                       id INT AUTO_INCREMENT primary key,
                       goods_id INT,
                       view_count INT,
                       likes_count INT,
                       star INT,
                       user_id VARCHAR(100),
                       title VARCHAR(100),
                       content VARCHAR(100),
                       goods_img_uuid VARCHAR(100),
                       date TIMESTAMP DEFAULT now()
);

/**********************************/
/* Table Name: GoodsImg */
/**********************************/
CREATE TABLE GoodsImg(
                         id INT AUTO_INCREMENT primary key,
                         goods_id INT,
                         url VARCHAR(100),
                         file_name VARCHAR(100)
);


create table attach(
	uuid varchar(100) not null,
    upload_path varchar(100) not null,
    fileName varchar(100) not null,
    goods_id int
);


ALTER TABLE User ADD CONSTRAINT IDX_User_account UNIQUE (account);
ALTER TABLE User ADD CONSTRAINT IDX_User_email UNIQUE (email);

ALTER TABLE Goods ADD CONSTRAINT IDX_Goods_PK PRIMARY KEY (id);

ALTER TABLE Cart ADD CONSTRAINT IDX_Cart_PK PRIMARY KEY (id);

ALTER TABLE Category ADD CONSTRAINT IDX_Category_PK PRIMARY KEY (id);

ALTER TABLE Review ADD CONSTRAINT IDX_Review_PK PRIMARY KEY (id);

ALTER TABLE GoodsImg ADD CONSTRAINT IDX_GoodsImg_PK PRIMARY KEY (id);

ALTER TABLE attach ADD CONSTRAINT IDX_Attach_PK PRIMARY KEY (uuid);

alter table attach change fileName file_name varchar(100);

alter table category drop column goods_id;