----cteate
insert into PRODUCTs(id, brand, model, os, HAS_CHARGER, HAS_HEADSET, price, count, MANUFACTURE_DATE)
VALUES (2,'samsung','135','android',1,1,115,15,null);


--select
select * from PRODUCTs where os='ios';


--update
update PRODUCTs set PRICE=10 where BRAND='sony';


--delete
delete from PRODUCTs;