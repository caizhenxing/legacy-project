<%@ page contentType="text/html; charset=gbk"%>

<table width="100%" border="0" cellpadding="0" cellspacing="1">
  <tr style="text-align: center;">
    <td class="Content_title" width="61" style="text-indent: 6px;">�� ϵ ��</td>
    <td class="Content2" style="text-align: left;"><input type="text" name="custName" class="Text" style="margin-left: 5px;"></td>
	<td class="Content_title" style="text-indent: 5px;" width="70">��ϵ�绰</td>
    <td class="Content2" width="180"><input type="text" name="custTel" class="Text" style="margin-left: 5px;"></td>
	<td class="Content_title" style="text-indent: 5px;" width="70">��ϵ��ַ</td>
    <td class="Content2" width="241">
	    <input type="text" name="custAddr" class="Text" style="margin-left: 5px;" size="31">
	    <input name="add" type="button" id="add" value="ѡ��"
										onClick="window.open('../priceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" class="buttonStyle"
										style="BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #002D96 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #002D96 1px solid;height: 17;width: 32;" />
	</td>
  </tr>
  <tr>
    <td class="Content_title" style="text-indent: 6px;">��Ʒ����</td>
    <td class="Content2"><input type="text" name="productName" class="Text" style="margin-left: 5px;"></td>
	<td class="Content_title" style="text-indent: 5px;">��Ʒ����</td>
    <td class="Content2"><input type="text" name="productCount" class="Text" style="margin-left: 5px;"></td>
    <td class="Content_title" style="text-indent: 5px;">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="Content2"><input type="text" name="post" class="Text" style="margin-left: 5px;" size="31"></td>
  </tr>
  <tr>
    <td class="Content_title" width="60" style="text-indent: 6px;">��������</td>
    <td class="Content2">
    <select name="dictSadType" class="Next_pulls" style="width: 131px;margin-left: 5px;">
      <jsp:include flush="true" page="textout.jsp?selectName=sadType"/>
    </select>
    </td>
    <td class="Content_title" style="text-indent: 5px;">��Ʒ���</td>
    <td class="Content2"><input type="text" name="productScale" class="Text" style="margin-left: 5px;"></td>
    <td rowspan="2" class="Content_title">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
    <td rowspan="2" class="Content2"><textarea name="remark2" rows="3" class="Text" style="margin-left: 5px;" cols="30" style="width: 197px;"></textarea></td>
  </tr>
  <tr>
    <td class="Content_title" width="60" style="text-indent: 6px;">��ʼʱ��</td><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
    <td class="Content2">
    <input type="text" name="deployBegin" onclick="openCal('openwin','deployBegin',false);" value="" class="Text" style="margin-left: 5px;">
    <img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('openwin','deployBegin',false);">
    </td>
    <td class="Content_title" style="text-indent: 5px;">��ֹʱ��</td>
    <td class="Content2">
    <input type="text" name="deployEnd" onclick="openCal('openwin','deployEnd',false);" value="" class="Text" style="margin-left: 5px;">
    <img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('openwin','deployEnd',false);">
    </td>
  </tr>
  <tr>
    <td colspan="6" class="Content_title">���״̬
      <select name="dict_is_answer_succeed" class="Next_pulls">
          <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
        </select>
      �����ʽ
      <select name="answer_man" class="Next_pulls">
        <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
      </select>
      �Ƿ�ط�
      <select name="dict_is_callback" class="Next_pulls">
        <option>��</option>
        <option>��</option>
      </select>
      �ط�ʱ��
      <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="Text" style="margin-left: 5px;">
      <img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
      ���Ϊ
      <input name="savedata" type="text" readonly="readonly" class="Text" size="15" value="ũ��Ʒ�����"/>
      </td>
  </tr>
</table>
