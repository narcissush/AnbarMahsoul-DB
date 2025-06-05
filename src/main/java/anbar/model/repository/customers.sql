create table customer (
  id number primary key,
    nationalid nvarchar2(10) not null ,
    name nvarchar2(30),
    family nvarchar2(30),
    gender nvarchar2(8),
    birth_date date,
    phone_number nvarchar2(11),
    username nvarchar2(20) not null ,
    password nvarchar2(30) not null

);

--select
select * from customer;


--delete
delete from customer where nationalid='0080386822';

--update
    update customer set;


--sequence
create sequence customer_seq start with 1 increment by 1;

select customer_seq.nextval from DUAL;

DROP SEQUENCE customer_seq;



