
col "�����Җ���X�V���t"  format a20

------------------------------------------------------------

select
 to_char(MASTER_DATE, 'YYYY/MM/DD') as "�����Җ���X�V���t"
from
 MASTER_INFO
where
 MASTER_SHUBETU = '7'
;

quit;








