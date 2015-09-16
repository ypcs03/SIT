drop view v1,v2;

create view v1 as
select cust,prod, avg(quant) as avg
from sales
group by cust,prod;

create view v2 as
select x.cust, x.prod, avg(y.quant) as other_avg
from sales x, sales y
where y.prod=x.prod and y.cust != x.cust
group by x.cust,x.prod;

select v1.cust, v1.prod, avg, other_avg
from v1 natural full outer join v2