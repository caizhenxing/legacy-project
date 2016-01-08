set ECLIPSE_HOME=D:\eclipse

set cp="%ECLIPSE_HOME%\workspace\jsps-kaken\web\WEB-INF\classes"
set url=http://localhost/kaken/gyomu/shinsaSaisoku.do

java -cp %cp% jp.go.jsps.kaken.model.client.Wget -v %url%


