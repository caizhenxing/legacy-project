<%@ page contentType="text/html; charset=gbk"%>

<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="59" class="Content_title" style="text-indent: 7px;">
												��ѯ����
											</td>
											<td width="337" class="Content">
												<textarea name="question_content" cols="51" rows="4" class="Text"></textarea>
											</td>
											<td width="60" class="Content_title" style="text-indent: 10px;">
												�������
											</td>
											<td class="Content" width="235" colspan="3">
												
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td>
															<select name="dict_question_type2" class="Next_pulls" onChange="selecttype(this)" style="width: 230px;" style="text-indent: 5px;">
													<option>
														��ѡ�����
													</option>
													<option>
														��������
													</option>
													<option>
														��������
													</option>
													<option>
														�߲�
													</option>
													<option>
														ҩ��
													</option>
													<option>
														����
													</option>
													<option>
														��ƺ���ر�
													</option>
													<option>
														����
													</option>
													<option>
														����
													</option>
													<option>
														����
													</option>
													<option>
														����
													</option>
													<option>
														Ϻ/з/��/��/��/��/�ݱ���������
													</option>
													<option>
														������ֳ
													</option>
													<option>
														������ʩ����������
													</option>
													<option>
														���߷��漰����
													</option>
													<option>
														����
													</option>
												</select>
														</td>
													</tr>
													<tr>
														<td>
															<span id="selectspan">
													<select name="dict_question_type3" class="Next_pulls" style="width: 230px;" style="text-indent: 5px;">
														<option>
															��ѡ��С��
														</option>
														<option>
															����ѡ����࣬Ȼ��ſ���ѡ��С��
														</option>
													</select>
												</span>
														</td>
													</tr>
													<tr>
														<td>
															<select name="dict_question_type4" class="Next_pulls" style="width: 230px;" style="text-indent: 5px;">
													<option style="">
														Ʒ�ֽ���
													</option>
													<option>
														�������
													</option>
													<option>
														��ֳ����
													</option>
													<option>
														�߲�����
													</option>
													<option>
														��ݷ���
													</option>
													<option>
														�ջ�����
													</option>
													<option>
														��Ʒ�ӹ�
													</option>
													<option>
														�г�����
													</option>
													<option>
														��������
													</option>
													<option>
														ũ��ʹ��
													</option>
													<option>
														��ʩ�޽�
													</option>
													<option>
														���߹���
													</option>
													<option>
														�����ۺ�
													</option>
												</select>
														</td>
													</tr>
												</table>
												
												
												
											</td>
											<td rowspan="3" class="Content_title" width="20" align="center">
												��
												<br><br>
												��
												<br><br>
												Ϊ
											</td>
											<td rowspan="3" class="Content" width="80">
												<select name="savedata" size="9" multiple class="Next_pulls" style="width: 80px">
													<option>
														��ͨ������
													</option>
													<option>
														���㰸����
													</option>
													<option>
														���ﰸ����
													</option>
													<option>
														Ч��������
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="Content_title" rowspan="2" style="text-indent: 7px;">
												���ߴ�
											</td>
											<td class="Content" rowspan="2">
												<textarea name="answer_content" cols="51" rows="4" class="Text"></textarea>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												���״̬
											</td>
											<td class="Content" width="85">
												<select name="dict_is_answer_succeed" class="Next_pulls">
													<jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed" />
												</select>
											</td>
											<td class="Content_title" width="60" style="text-indent: 8px;">
												�������
											</td>
											<td class="Content" width="105" style="text-align: left;">
												<select name="answer_man" class="Next_pulls" style="width: 88px;">
													<jsp:include flush="true" page="textout.jsp?selectName=answer_man" />
												</select>
											</td>
										</tr>
										<tr>
											<td class="Content_title" style="text-indent: 10px;">
												�Ƿ�ط�
											</td>
											<td class="Content">
												<select name="dict_is_callback" class="Next_pulls" style="width: 75px;">
													<option>
														��
													</option>
													<option>
														��
													</option>
												</select>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												�ط�ʱ��
											</td>
											<td class="Content" style="text-align: left;"><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
												<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value='<%=date%>' size="9"
													class="Text">
												<img alt="ѡ������" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
											</td>
										</tr>
									</table>
									<table width="100%" height="100" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="61" class="Content_title" style="text-indent: 7px;">
												ͬ������
											</td>
											<td class="Content">
												<input name="textfield242" type="text" value="�����������" size="122" onClick="if(this.value=='�����������')this.value=''" onpropertychange="search(this.value)"
													class="Text">
											</td>
										</tr>
										<tr>
											<td width="60" class="Content_title" style="text-indent: 7px;">
												�������
											</td>
											<td height="100%">
				
												<DIV style="width:100%;height:100%;overflow-y:auto;" id="div1">
				
													<table width="100%">
														<tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" class="Content">
															<td width="100%">
																��������б�
															</td>
														</tr>
													</table>
				
												</Div>
				
											</td>
										</tr>
									</table>