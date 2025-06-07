create table products(
                         id number unique not null,
                         brand nvarchar2(10),
                         model nvarchar2(20),
                         os nvarchar2(10),
                         has_charger number(1),
                         has_headset number(1),
                         price number,
                         count number,
                         manufacture_date date
);

create sequence product_seq start with 1 increment by 1;

select product_seq.nextval from DUAL;

DROP SEQUENCE product_seq;

select * from Products where brand='SONY' and price>=2000;

select * from products;

delete from products;

DROP table PRODUCT;
