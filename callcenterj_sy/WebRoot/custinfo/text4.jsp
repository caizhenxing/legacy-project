<%@ page contentType="text/html; charset=gbk"%>
<table width="100%" cellpadding="0" cellspacing="1" >
	<tr>
		<td>
			<IFRAME name="iframeInquiry" id="iframeInquiry" src='text4main.jsp' frameborder="0" width="100%" height="200px"></IFRAME>
		</td>
	</tr>
	<tr>
    <td colspan="7" id="aaa" class="Content_title">解决状态
      <select name="dict_is_answer_succeed" class="Next_pulls">
        <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
      </select>
      解决方式
      <select name="answer_man" class="Next_pulls">
        <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
      </select>
      是否回访
      <select name="dict_is_callback" class="Next_pulls">
        <option>否</option>
        <option>是</option>
      </select>
回访时间<% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="Text">
<img alt="选择时间" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
另存为
<input name="savedata" type="text" readonly="readonly" class="Text" size="15" value="专题调查库"/>
</td>
  </tr>
</table>

