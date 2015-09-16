drop view v1,v2;
create view v1 as
    select x.prod, x.quant, count(y.prod) as count1
    from sales x, sales y
    where x.prod = y.prod
    group by x.prod, x.quant;

create view v2 as
    select x.prod, x.quant, count(y.prod) as count2
    from sales x, sales y
    where 
        x.prod = y.prod and y.quant < x.quant
    group by x.prod, x.quant;

select v1.prod, v1.quant
from v1 natural full outer join v2
where count2 = count1/2
