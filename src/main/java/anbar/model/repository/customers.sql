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

--insert
insert into customer
(id, nationalid, name, family, gender, birth_date, phone_number, username, password)
VALUES
(20,'0011223344','narges','hajizadeh','WOMEN','1986.04.21','09123987298','narges','Nvd@123';

--select
select * from customer;


--delete
delete from customer where nationalid='0080386822';

--update
update customer set password=? where nationalid=?;

--delete table
DROP table customer;



--sequence
--create
create sequence customer_seq start with 1 increment by 1;

--select
select customer_seq.nextval from DUAL;

--delete Table
DROP SEQUENCE customer_seq;





