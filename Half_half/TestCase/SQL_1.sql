drop view temp_ny,temp_ct,temp_nj;

create view temp_ny as
select cust,avg(quant) as ny_avg
from sales
where year=1997 and state='NY'
group by cust;

create view temp_ct as
select cust,avg(quant) as ct_avg
from sales
where year=1997 and state='CT'
group by cust;

create view temp_nj as
select cust,avg(quant) as nj_avg
from sales
where year=1997 and state='NJ'
group by cust;

select temp_ny.cust, temp_ny.ny_avg, temp_ct.ct_avg, temp_nj.nj_avg
from temp_ct natural full outer join temp_ny natural full outer join temp_nj
where ny_avg>nj_avg and ny_avg>ct_avg

