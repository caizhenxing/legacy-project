<%@ page contentType="text/html; charset=gbk"%>
<table width="100%" cellpadding="0" cellspacing="1" >
	<tr>
		<td>
			<IFRAME name="iframeInquiry" id="iframeInquiry" src='text4main.jsp' frameborder="0" width="100%" height="200px"></IFRAME>
		</td>
	</tr>
	<tr>
    <td colspan="7" id="aaa" class="Content_title">���״̬
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
�ط�ʱ��<% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="Text">
<img alt="ѡ��ʱ��" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
���Ϊ
<input name="savedata" type="text" readonly="readonly" class="Text" size="15" value="ר������"/>
</td>
  </tr>
</table>

