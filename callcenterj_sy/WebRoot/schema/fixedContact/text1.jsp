<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<table width="100%" border="0" id="table">
  <tr align="center">
    <td colspan="2" class="listTitleStyle">Ʒ�����</td>
    <td class="listTitleStyle">�۸�</td>
    <td class="listTitleStyle">�����������ע</td>
    <td class="listTitleStyle"><input name="add" type="button" id="add" value="���" onClick="addtr()"></td>
  </tr>
<% 
for(int i = 0; i<4; i++){
%>
  <tr>
    <td class="valueStyle">
	<select name="dict_product_type2" class="selectStyle" onChange="selecttype(this)">
      <option value="">��ѡ�����</option>
      <option>��������</option>
      <option>��������</option>
      <option>�߲�</option>
      <option>ҩ��</option>
      <option>����</option>
      <option>��ƺ���ر�</option>
      <option>����</option>
      <option>����</option>
      <option>����</option>
      <option>����</option>
      <option>Ϻ/з/��/��/��/��/�ݱ���������</option>
      <option>������ֳ</option>
      <option>������ʩ����������</option>
      <option>����</option>
     </select>
	</td>
    <td class="valueStyle" id="dict_product_type1_td_<%= i %>">
	<select name="dict_product_type1" class="selectStyle">
	<OPTION>��ѡ��С��</OPTION>
	<OPTION>����ѡ����࣬Ȼ��ſ���ѡ��С��</OPTION>
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
    <td colspan="5" class="valueStyle"  id="aaa">���״̬
      <select name="dict_is_answer_succeed" class="selectStyle">
        <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
      </select>
      �����ʽ
      <select name="answer_man" class="selectStyle">
        <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
      </select>
      �Ƿ�ط�
      <select name="dict_is_callback" class="selectStyle">
        <option>��</option>
        <option>��</option>
      </select>
�ط�ʱ��<% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="writeTextStyle">
<img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
���Ϊ
<select name="savedata" class="selectStyle">
  <option>��ͨ������</option>
  <option>���㰸����</option>
  <option>���ﰸ����</option>
  <option>Ч��������</option>
  <option selected>ũ��Ʒ�۸��</option>
  <option>ũ��Ʒ�����</option>
  <option>ר������</option>
  <option>ҽ����Ϣ��</option>
  <option>��ҵ��Ϣ��</option>
</select></td>
  </tr>
</table>
