<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<table width="100%" border="0">
  <tr align="center">
    <td class="labelStyle">�� ϵ ��</td>
    <td class="valueStyle"><input type="text" name="custName" class="writeTextStyle"></td>
    <td class="labelStyle">��������</td>
    <td class="valueStyle">
    <select name="dictSadType" class="selectStyle">
      <jsp:include flush="true" page="textout.jsp?selectName=sadType"/>
    </select>
    </td>
    <td class="labelStyle">��Ч����</td><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
    <td class="valueStyle">
    <input type="text" name="deployEnd" onclick="openCal('openwin','callback_time',false);" value="<%=date %>" class="writeTextStyle" size="17">
    <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
    </td>
  </tr>
  <tr align="center">
    <td class="labelStyle">��ϵ�绰</td>
    <td class="valueStyle"><input type="text" name="custTel" class="writeTextStyle"></td>
    <td class="labelStyle">��Ʒ����</td>
    <td class="valueStyle"><input type="text" name="productName" class="writeTextStyle"></td>
    <td class="labelStyle">��������</td>
    <td class="valueStyle">
    <input type="text" name="deployBegin" onclick="openCal('openwin','deployBegin',false);" value="<%=date %>" class="writeTextStyle" size="17">
    <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','deployBegin',false);">
    </td>
  </tr>
  <tr align="center">
    <td class="labelStyle">��ϵ��ַ</td>
    <td class="valueStyle"><input type="text" name="custAddr" class="writeTextStyle"></td>
    <td class="labelStyle">��Ʒ���</td>
    <td class="valueStyle"><input type="text" name="productScale" class="writeTextStyle"></td>
    <td rowspan="2" class="labelStyle">�� ע</td>
    <td rowspan="2" class="valueStyle"><textarea name="remark2" rows="3" class="writeTextStyle"></textarea></td>
  </tr>
  <tr align="center">
    <td class="labelStyle">�ʱ�</td>
    <td class="valueStyle"><input type="text" name="post" class="writeTextStyle"></td>
    <td class="labelStyle">��Ʒ����</td>
    <td class="valueStyle"><input type="text" name="productCount" class="writeTextStyle"></td>
  </tr>
  <tr align="right">
    <td colspan="6" class="labelStyle">���״̬
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
      �ط�ʱ��
      <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="writeTextStyle">
      <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
      ���Ϊ
      <select name="savedata" class="selectStyle">
        <option>��ͨ������</option>
        <option>���㰸����</option>
        <option>���ﰸ����</option>
        <option>Ч��������</option>
        <option>ũ��Ʒ�۸��</option>
        <option selected>ũ��Ʒ�����</option>
        <option>ר������</option>
        <option>ҽ����Ϣ��</option>
        <option>��ҵ��Ϣ��</option>
      </select></td>
  </tr>
</table>
