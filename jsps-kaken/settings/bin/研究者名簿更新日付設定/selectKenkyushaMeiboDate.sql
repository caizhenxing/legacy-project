
col "研究者名簿更新日付"  format a20

------------------------------------------------------------

select
 to_char(MASTER_DATE, 'YYYY/MM/DD') as "研究者名簿更新日付"
from
 MASTER_INFO
where
 MASTER_SHUBETU = '7'
;

quit;








