create or alter view destroyed_content_per_day as
select 
    type_id_id, 
    sum(quentity) as quentity, 
    cast(date as Date) as date
from destroyed_content dc 
group by 
    cast(date as Date), 
    type_id_id
GO