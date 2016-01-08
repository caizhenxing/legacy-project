<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

	<table width="100%" height="100%" border="0">
          <tr>
            <td width="50%"><table width="100%" border="0">
              <tr>
                <td align="right" class="labelStyle">咨询内容</td>
                <td width="40%" colspan="3" class="valueStyle"><textarea name="question_content" cols="47" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">咨询答案</td>
                <td colspan="3" class="valueStyle"><textarea name="answer_content" cols="47" rows="3" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">问题分类</td>
                <td colspan="3" width="25%" class="valueStyle">
                <select name="dict_question_type2" class="selectStyle" onChange="selecttype(this)">
                  <option>请选择大类</option>
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
                  <option>政策法规及管理</option>
                  <option>其他</option>
                </select>
                <br><span id="selectspan">
                <select name="dict_question_type3" class="selectStyle">
                  <option>请选择小类</option>
                  <option>请先选择大类，然后才可以选择小类</option>
                </select></span>
               <br>
                <select name="dict_question_type4"  class="selectStyle">
                  	<option>品种介绍</option>
					<option>栽培管理</option>
					<option>养殖管理</option>
					<option>疫病防治</option>
					<option>虫草防除</option>
					<option>收获贮运</option>
					<option>产品加工</option>
					<option>市场行情</option>
					<option>饲料配制</option>
					<option>农资使用</option>
					<option>设施修建</option>
					<option>政策管理</option>
					<option>其他综合</option>
                </select>
                </td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">解决状态</td>
                <td width="25%" class="valueStyle"><select name="dict_is_answer_succeed"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
                </select></td>
                <td width="25%" align="right" class="labelStyle">解决方式</td>
                <td width="25%" class="valueStyle"><select name="answer_man"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
                </select></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">是否回访</td>
                <td class="valueStyle"><select name="dict_is_callback"  class="selectStyle">
                  <option>否</option>
                  <option>是</option>
                </select></td>
                <td align="right" class="labelStyle">另存为</td>
                <td rowspan="2" class="valueStyle">
                <select name="savedata" size="3" multiple  class="selectStyle">
                  <option>普通案例库</option>
                  <option>焦点案例库</option>
                  <option>会诊案例库</option>
                  <option>效果案例库</option>
                  <option>农产品价格库</option>
                  <option>农产品供求库</option>
                  <option>专题调查库</option>
                  <option>医疗信息库</option>
                  <option>企业信息库</option>
                </select>
                </td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">回访时间</td>
                <td colspan="2" class="valueStyle"><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
                <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value='<%= date %>' size="10" class="writeTextStyle">
                <img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
                </td>
                </tr>
            </table></td>
            <td width="50%" class="valueStyle"><table width="100%" height="100%" border="0">
              <tr>
                <td><input name="textfield242" type="text" value="搜索相关问题" size="60" onClick="if(this.value=='搜索相关问题')this.value=''" onpropertychange="search(this.value)" class="writeTextStyle"></td>
              </tr>
              <tr>
                <td height="100%">
				
				<DIV style="width:100%;height:100%;overflow-y:auto;" id="div1">
				
				<table width="100%">
				  <tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" class="valueStyle">
				    <td width="100%">搜索结果列表
				    </td>
				  </tr>
				</table>
				
				</Div>
				
				
				</td>
              </tr>
            </table></td>
          </tr>

        </table>