<%@ page contentType="text/html; charset=gbk"%>

<table width="100%" border="0" id="table" cellpadding="0" cellspacing="1">
  <tr class="Blue_title2">
				<td>
					产品大类
				</td>
				<td>
					产品小类
				</td>
				<td>
					产品名称
				</td>
				<td>
					价格类型
				</td>
				<td>
					价格
				</td>
				<td>
					当地情况及备注
				</td>
				<td>
					<input name="add" type="button" id="add" value="添加"   onClick="addtr()"
					style="BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #002D96 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #002D96 1px solid;" 
					>
				</td>
			</tr>
<% 
for(int i = 0; i<4; i++){
String style = i % 2 == 0 ? "Content" : "Content_title";
%>
  <tr class="<%= style %>" style="text-align: center;" height="26">
				<td>
					<select name="dict_product_type1" class="Next_pulls" onChange="select1(this)">
						<OPTION value="">请选择大类</OPTION>
      				  	<jsp:include flush="true" page="select_product.jsp"/>
				     </select>
				</td>
				<td id="dict_product_type2_td_<%= i %>">
					<select name="dict_product_type2" class="Next_pulls">
						<OPTION value="">请选择小类</OPTION>
					</select>
				</td>
				<td id="product_name_td_<%= i %>">
					<select name="product_name" class="Next_pulls">
						<OPTION value="">请选择名称</OPTION>
					</select>
				</td>
				<td>
					<select name="dict_price_type" class="Next_pulls">
						<jsp:include flush="true" page="textout.jsp?selectName=priceType"/>
					</select>
					
				</td>
				<td>
					<input name="product_price" type="text" class="Text" id="product_price">
				</td>
				<td>
					<input name="remarkj" type="text" class="Text" id="remarkj" size="35">
				</td>
				<td>
					
				</td>
			</tr>
<%
}
%>
  <tr class="Content_title">
    <td colspan="7" id="aaa">解决状态
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
<input name="savedata" type="text" readonly="readonly" class="Text" size="15" value="农产品价格库"/>
</td>
  </tr>
</table>
