set ECLIPSE_HOME=D:\All-In-One-Eclipse

set cp="D:\workspace\jsps-kaken\web\WEB-INF\classes"
set url=http://localhost:8080/kaken/system/gaiyoPdfConvert.do

java -cp %cp% jp.go.jsps.kaken.model.client.Wget -v %url%


