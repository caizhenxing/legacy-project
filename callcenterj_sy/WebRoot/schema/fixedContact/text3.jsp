<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

	<table width="100%" height="100%" border="0">
          <tr>
            <td width="50%"><table width="100%" border="0">
              <tr>
                <td align="right" class="labelStyle">��ѯ����</td>
                <td width="40%" colspan="3" class="valueStyle"><textarea name="question_content" cols="47" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">��ѯ��</td>
                <td colspan="3" class="valueStyle"><textarea name="answer_content" cols="47" rows="3" class="writeTextStyle"></textarea></td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">�������</td>
                <td colspan="3" width="25%" class="valueStyle">
                <select name="dict_question_type2" class="selectStyle" onChange="selecttype(this)">
                  <option>��ѡ�����</option>
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
                  <option>���߷��漰����</option>
                  <option>����</option>
                </select>
                <br><span id="selectspan">
                <select name="dict_question_type3" class="selectStyle">
                  <option>��ѡ��С��</option>
                  <option>����ѡ����࣬Ȼ��ſ���ѡ��С��</option>
                </select></span>
               <br>
                <select name="dict_question_type4"  class="selectStyle">
                  	<option>Ʒ�ֽ���</option>
					<option>�������</option>
					<option>��ֳ����</option>
					<option>�߲�����</option>
					<option>��ݷ���</option>
					<option>�ջ�����</option>
					<option>��Ʒ�ӹ�</option>
					<option>�г�����</option>
					<option>��������</option>
					<option>ũ��ʹ��</option>
					<option>��ʩ�޽�</option>
					<option>���߹���</option>
					<option>�����ۺ�</option>
                </select>
                </td>
              </tr>
              <tr>
                <td width="25%" align="right" class="labelStyle">���״̬</td>
                <td width="25%" class="valueStyle"><select name="dict_is_answer_succeed"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed"/>
                </select></td>
                <td width="25%" align="right" class="labelStyle">�����ʽ</td>
                <td width="25%" class="valueStyle"><select name="answer_man"  class="selectStyle">
                  <jsp:include flush="true" page="textout.jsp?selectName=answer_man"/>
                </select></td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">�Ƿ�ط�</td>
                <td class="valueStyle"><select name="dict_is_callback"  class="selectStyle">
                  <option>��</option>
                  <option>��</option>
                </select></td>
                <td align="right" class="labelStyle">���Ϊ</td>
                <td rowspan="2" class="valueStyle">
                <select name="savedata" size="3" multiple  class="selectStyle">
                  <option>��ͨ������</option>
                  <option>���㰸����</option>
                  <option>���ﰸ����</option>
                  <option>Ч��������</option>
                  <option>ũ��Ʒ�۸��</option>
                  <option>ũ��Ʒ�����</option>
                  <option>ר������</option>
                  <option>ҽ����Ϣ��</option>
                  <option>��ҵ��Ϣ��</option>
                </select>
                </td>
              </tr>
              <tr>
                <td align="right" class="labelStyle">�ط�ʱ��</td>
                <td colspan="2" class="valueStyle"><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
                <input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value='<%= date %>' size="10" class="writeTextStyle">
                <img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
                </td>
                </tr>
            </table></td>
            <td width="50%" class="valueStyle"><table width="100%" height="100%" border="0">
              <tr>
                <td><input name="textfield242" type="text" value="�����������" size="60" onClick="if(this.value=='�����������')this.value=''" onpropertychange="search(this.value)" class="writeTextStyle"></td>
              </tr>
              <tr>
                <td height="100%">
				
				<DIV style="width:100%;height:100%;overflow-y:auto;" id="div1">
				
				<table width="100%">
				  <tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" class="valueStyle">
				    <td width="100%">��������б�
				    </td>
				  </tr>
				</table>
				
				</Div>
				
				
				</td>
              </tr>
            </table></td>
          </tr>

        </table>