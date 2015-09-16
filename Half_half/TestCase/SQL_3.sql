drop view v_avg,v_before,v_after;

create view v_avg as
select cust, month, avg(quant) as avg
from sales
where year=1997
group by cust,month;

create view v_before as
select v1.cust,v1.month, avg(v2.quant) as avg_before
from sales as v1, sales as v2
where v1.year=1997 and v2.year=1997 and v1.cust=v2.cust and v2.month<v1.month
group by v1.cust,v1.month;

create view v_after as
select v1.cust,v1.month, avg(v2.quant) as avg_after
from sales as v1, sales as v2
where v1.year=1997 and v2.year=1997 and v1.cust=v2.cust and v2.month>v1.month
group by v1.cust,v1.month;

select v_avg.cust,v_avg.month,v_before.avg_before,avg,v_after.avg_after
from v_avg natural full outer join v_before natural full outer join v_after
order by v_avg.cust,v_avg.month