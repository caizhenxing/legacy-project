<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<table width="100%" border="0">
  <tr align="center">
    <td class="labelStyle">联 系 人</td>
    <td class="valueStyle"><input type="text" name="custName" class="writeTextStyle"></td>
    <td class="labelStyle">供求类型</td>
    <td class="valueStyle">
    <select name="dictSadType" class="selectStyle">
      <jsp:include flush="true" page="textout.jsp?selectName=sadType"/>
    </select>
    </td>
    <td class="labelStyle">有效期至</td><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
    <td class="valueStyle">
    <input type="text" name="deployEnd" onclick="openCal('openwin','callback_time',false);" value="<%=date %>" class="writeTextStyle" size="17">
    <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
    </td>
  </tr>
  <tr align="center">
    <td class="labelStyle">联系电话</td>
    <td class="valueStyle"><input type="text" name="custTel" class="writeTextStyle"></td>
    <td class="labelStyle">产品名称</td>
    <td class="valueStyle"><input type="text" name="productName" class="writeTextStyle"></td>
    <td class="labelStyle">发布日期</td>
    <td class="valueStyle">
    <input type="text" name="deployBegin" onclick="openCal('openwin','deployBegin',false);" value="<%=date %>" class="writeTextStyle" size="17">
    <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','deployBegin',false);">
    </td>
  </tr>
  <tr align="center">
    <td class="labelStyle">联系地址</td>
    <td class="valueStyle"><input type="text" name="custAddr" class="writeTextStyle"></td>
    <td class="labelStyle">产品规格</td>
    <td class="valueStyle"><input type="text" name="productScale" class="writeTextStyle"></td>
    <td rowspan="2" class="labelStyle">备 注</td>
    <td rowspan="2" class="valueStyle"><textarea name="remark2" rows="3" class="writeTextStyle"></textarea></td>
  </tr>
  <tr align="center">
    <td class="labelStyle">邮编</td>
    <td class="valueStyle"><input type="text" name="post" class="writeTextStyle"></td>
    <td class="labelStyle">产品数量</td>
    <td class="valueStyle"><input type="text" name="productCount" class="writeTextStyle"></td>
  </tr>
  <tr align="right">
    <td colspan="6" class="labelStyle">解决状态
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
      回访时间
      <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value="<%= date %>" size="10" class="writeTextStyle">
      <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
      另存为
      <select name="savedata" class="selectStyle">
        <option>普通案例库</option>
        <option>焦点案例库</option>
        <option>会诊案例库</option>
        <option>效果案例库</option>
        <option>农产品价格库</option>
        <option selected>农产品供求库</option>
        <option>专题调查库</option>
        <option>医疗信息库</option>
        <option>企业信息库</option>
      </select></td>
  </tr>
</table>
