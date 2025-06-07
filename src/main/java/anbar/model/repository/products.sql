--create Table
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

--insert
insert into PRODUCTS
(id, brand, model, os, HAS_CHARGER, HAS_HEADSET, price, count, MANUFACTURE_DATE)
VALUES
(2,'samsung','135','android',1,1,115,15,null);

--select
select * from Products where brand='SONY' and price>=2000;

--select All
select * from products;

--delete
delete from products;

--delete Table
DROP table PRODUCTs;



-- sequence
create sequence product_seq start with 1 increment by 1;

select product_seq.nextval from DUAL;

DROP SEQUENCE product_seq;