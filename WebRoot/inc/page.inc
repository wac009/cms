<%@page contentType="text/html; charset=UTF-8"%>
<s:if test="%{pagination==null||pagination.list==null||pagination.list.size<=0}">
	<tr><td colspan="100" class="pn-lnoresult">没有相关数据！</td></tr>
</s:if>			
<s:if test="%{pagination.list!=null&&pagination.list.size>0}">
	<tr class="pn-sp">
		<td colspan="100">
			<div class="pn-sp-left">
				<!--<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>-->
			</div>
			<div class="pn-sp-right">
				<s:if test="pageNo<=1">
					<a href="javascript:void(0)" class="txt-disabd">首页</a> &nbsp;
					<a href="javascript:void(0)" class="txt-disabd">上页</a> &nbsp;
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="_gotoPage(1)">首页</a> &nbsp;
					<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.prePage" />)">上页</a> &nbsp;
				</s:else>
				<s:if test="pageNo>=pagination.totalPage">
					<a href="javascript:void(0)" class="txt-disabd">下页</a> &nbsp;
					<a href="javascript:void(0)" class="txt-disabd">末页</a> &nbsp;
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.nextPage" />)">下页</a> &nbsp;
					<a href="javascript:void(0)" onclick="_gotoPage(<s:property value="pagination.totalPage" />)">末页</a> &nbsp;
				</s:else>
				 &nbsp; 
				共 <s:property value="pagination.totalCount"/> 条
				每页	<input type="text" value="<s:property value="pagination.pageSize"/>" size="2" onfocus="this.select();" 
					onblur="new Pn.Cookie().set(Pn.Cookie.countPerPage,this.value,10*365*24*60*60);setFormAction('list.jspa');this.form.submit();" 
					onkeypress="if(event.keyCode==13){$(this).blur();return false;}"/> 条
				
				当前  <s:property value="pagination.pageNo"/>/<s:property value="pagination.totalPage"/>  页
				<s:if test="1==pagination.totalPage">
					<span class="txt-disabd">转到第</span> 
					<input disabled="disabled" class="txt-disabd" type="text" id="_goPs" size="2"/><span class="txt-disabd">页</span>
					<input disabled="disabled" class="btn gopage txt-disabd" id="_goPage" type="button" value="转"/>
				</s:if>
				<s:else>
					转到第 
					<input type="text" id="_goPs" size="2" onfocus="this.select();"
						onkeypress="if(event.keyCode==13){$('#_goPage').click();return false;}"/> 页
					<input class="btn gopage" id="_goPage" type="button" value="转" onclick="setFormAction('list');_gotoPage($('#_goPs').val());"/>
				</s:else>
			</div>
		</td>
	</tr>
</s:if>