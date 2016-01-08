<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<table width="100%" border="0" id="table">
  <tr align="center">
    <td colspan="2" class="listTitleStyle">品种类别</td>
    <td class="listTitleStyle">价格</td>
    <td class="listTitleStyle">当地情况及备注</td>
    <td class="listTitleStyle"><input name="add" type="button" id="add" value="添加" onClick="addtr()"></td>
  </tr>
<% 
for(int i = 0; i<4; i++){
%>
  <tr>
    <td class="valueStyle">
	<select name="dict_product_type2" class="selectStyle" onChange="selecttype(this)">
      <option value="">请选择大类</option>
      <option>粮油作物</option>
      <option>经济作物</option>
      <option>蔬菜</option>
      <option>药材</option>
      <option>花卉</option>
      <option>草坪及地被</option>
      <option>家畜</option>
      <option>家禽</option>
      <option>牧草</option>
      <option>鱼类</option>
      <option>虾/蟹/鳖/龟/蛙/藻/螺贝及软体类</option>
      <option>特种养殖</option>
      <option>基础设施及生产资料</option>
      <option>其他</option>
     </select>
	</td>
    <td class="valueStyle" id="dict_product_type1_td_<%= i %>">
	<select name="dict_product_type1" class="selectStyle">
	<OPTION>请选择小类</OPTION>
	<OPTION>请先选择大类，然后才可以选择小类</OPTION>
	</select>
	</td>
    <td class="valueStyle"><input name="product_price" type="text" class="writeTextStyle" id="product_price"></td>
    <td class="valueStyle"><input name="remarkj" type="text" class="writeTextStyle" id="remarkj"></td>
    <td class="listTitleStyle"></td>
  </tr>
<%
}
%>
  <tr align="right">
    <td colspan="5" class="valueStyle"  id="aaa">解决状态
      <select name="dict_is_answer_succeed" class="selectStyle">
        <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
      </select>
      解决方式
      <select name="answer_man" class="selectStyle">
        <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
      </select>
      是否回访
      <select name="dict_is_callback" class="selectStyle">
        <option>否</option>
        <option>是</option>
      </select>
回访时间<% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="writeTextStyle">
<img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
另存为
<select name="savedata" class="selectStyle">
  <option>普通案例库</option>
  <option>焦点案例库</option>
  <option>会诊案例库</option>
  <option>效果案例库</option>
  <option selected>农产品价格库</option>
  <option>农产品供求库</option>
  <option>专题调查库</option>
  <option>医疗信息库</option>
  <option>企业信息库</option>
</select></td>
  </tr>
</table>
