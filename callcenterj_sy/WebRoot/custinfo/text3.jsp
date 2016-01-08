<%@ page contentType="text/html; charset=gbk"%>

<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="59" class="Content_title" style="text-indent: 7px;">
												咨询内容
											</td>
											<td width="337" class="Content">
												<textarea name="question_content" cols="51" rows="4" class="Text"></textarea>
											</td>
											<td width="60" class="Content_title" style="text-indent: 10px;">
												问题分类
											</td>
											<td class="Content" width="235" colspan="3">
												
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td>
															<select name="dict_question_type2" class="Next_pulls" onChange="selecttype(this)" style="width: 230px;" style="text-indent: 5px;">
													<option>
														请选择大类
													</option>
													<option>
														粮油作物
													</option>
													<option>
														经济作物
													</option>
													<option>
														蔬菜
													</option>
													<option>
														药材
													</option>
													<option>
														花卉
													</option>
													<option>
														草坪及地被
													</option>
													<option>
														家畜
													</option>
													<option>
														家禽
													</option>
													<option>
														牧草
													</option>
													<option>
														鱼类
													</option>
													<option>
														虾/蟹/鳖/龟/蛙/藻/螺贝及软体类
													</option>
													<option>
														特种养殖
													</option>
													<option>
														基础设施及生产资料
													</option>
													<option>
														政策法规及管理
													</option>
													<option>
														其他
													</option>
												</select>
														</td>
													</tr>
													<tr>
														<td>
															<span id="selectspan">
													<select name="dict_question_type3" class="Next_pulls" style="width: 230px;" style="text-indent: 5px;">
														<option>
															请选择小类
														</option>
														<option>
															请先选择大类，然后才可以选择小类
														</option>
													</select>
												</span>
														</td>
													</tr>
													<tr>
														<td>
															<select name="dict_question_type4" class="Next_pulls" style="width: 230px;" style="text-indent: 5px;">
													<option style="">
														品种介绍
													</option>
													<option>
														栽培管理
													</option>
													<option>
														养殖管理
													</option>
													<option>
														疫病防治
													</option>
													<option>
														虫草防除
													</option>
													<option>
														收获贮运
													</option>
													<option>
														产品加工
													</option>
													<option>
														市场行情
													</option>
													<option>
														饲料配制
													</option>
													<option>
														农资使用
													</option>
													<option>
														设施修建
													</option>
													<option>
														政策管理
													</option>
													<option>
														其他综合
													</option>
												</select>
														</td>
													</tr>
												</table>
												
												
												
											</td>
											<td rowspan="3" class="Content_title" width="20" align="center">
												另
												<br><br>
												存
												<br><br>
												为
											</td>
											<td rowspan="3" class="Content" width="80">
												<select name="savedata" size="9" multiple class="Next_pulls" style="width: 80px">
													<option>
														普通案例库
													</option>
													<option>
														焦点案例库
													</option>
													<option>
														会诊案例库
													</option>
													<option>
														效果案例库
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="Content_title" rowspan="2" style="text-indent: 7px;">
												热线答复
											</td>
											<td class="Content" rowspan="2">
												<textarea name="answer_content" cols="51" rows="4" class="Text"></textarea>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												解决状态
											</td>
											<td class="Content" width="85">
												<select name="dict_is_answer_succeed" class="Next_pulls">
													<jsp:include flush="true" page="textout.jsp?selectName=dict_is_answer_succeed" />
												</select>
											</td>
											<td class="Content_title" width="60" style="text-indent: 8px;">
												解决方法
											</td>
											<td class="Content" width="105" style="text-align: left;">
												<select name="answer_man" class="Next_pulls" style="width: 88px;">
													<jsp:include flush="true" page="textout.jsp?selectName=answer_man" />
												</select>
											</td>
										</tr>
										<tr>
											<td class="Content_title" style="text-indent: 10px;">
												是否回访
											</td>
											<td class="Content">
												<select name="dict_is_callback" class="Next_pulls" style="width: 75px;">
													<option>
														否
													</option>
													<option>
														是
													</option>
												</select>
											</td>
											<td class="Content_title" style="text-indent: 10px;">
												回访时间
											</td>
											<td class="Content" style="text-align: left;"><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
												<input name="callback_time" type="text" onclick="openCal('openwin','callback_time',false);" value='<%=date%>' size="9"
													class="Text">
												<img alt="选择日期" src="../html/img/cal.gif" onclick="openCal('openwin','callback_time',false);">
											</td>
										</tr>
									</table>
									<table width="100%" height="100" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="61" class="Content_title" style="text-indent: 7px;">
												同步搜索
											</td>
											<td class="Content">
												<input name="textfield242" type="text" value="搜索相关问题" size="122" onClick="if(this.value=='搜索相关问题')this.value=''" onpropertychange="search(this.value)"
													class="Text">
											</td>
										</tr>
										<tr>
											<td width="60" class="Content_title" style="text-indent: 7px;">
												搜索结果
											</td>
											<td height="100%">
				
												<DIV style="width:100%;height:100%;overflow-y:auto;" id="div1">
				
													<table width="100%">
														<tr bgcolor='#ffffff' onMouseOut="this.bgColor='#ffffff'" onMouseOver="this.bgColor='#78C978';this.style.cursor='pointer';" class="Content">
															<td width="100%">
																搜索结果列表
															</td>
														</tr>
													</table>
				
												</Div>
				
											</td>
										</tr>
									</table>