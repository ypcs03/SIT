drop view v1,v2,v3;

create view v1 as
select x.prod,x.month, avg(y.quant) as previous_avg
from sales x, sales y
where x.year=1997 and y.year=1997
	and x.prod=y.prod
	and x.month=y.month+1
group by x.prod,x.month;

create view v2 as
select x.prod,x.month, avg(y.quant) as after_avg
from sales x, sales y
where x.year=1997 and y.year=1997
	and x.prod=y.prod
	and x.month=y.month-1
group by x.prod,x.month;

create view v3 as
select prod, month, quant
from sales
where year=1997;

select v3.prod, v3.month, count(v3.*)
from v3 natural full outer join v1 natural full outer join v2
where v3.quant>coalesce(previous_avg,0) and v3.quant<coalesce(after_avg,0)
group by v3.prod, v3.month

