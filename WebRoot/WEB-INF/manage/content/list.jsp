<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  
  <body>
  	<div id="container">
  	  	<div class="navi"><span class="navi_b">当前位置:</span><span class="navi_h">内容管理 - 内容列表</span>
  	  		<span class="navi_f">
  	  			<a href='content_list.jspa' style="padding:0 5px;">刷新</a>
  	  			<a href='content_add.jspa' style="padding:0 5px;">添加</a>
  	  			<input class="del btn" type="button" value="删除" onclick="javascript:_batchDel()"/>
  	  		</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo" id="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form id="dataForm">
	  		<s:hidden name="id"/>
	  		<s:hidden name="pageNo"/>
	  		<div class="search">
				标题<s:textfield name="queryTitle" cssClass="input" />
				编辑<s:select name="queryUser" id="queryUser" list="userList" listKey="id" listValue="username" cssClass="select" onchange="_search()" />
				<s:select name="queryChannel" id="queryChannel" list="channelList" listKey="id" listValue="selectTree" cssClass="select" onchange="_search()" />
				<s:select name="queryType" id="queryType" list="queryTypeList" listKey="id" listValue="name" cssClass="select" onchange="_search()" />
				<s:select name="queryOrderBy" cssClass="select" onchange="_search()" value="%{queryOrderBy}"
				list="#{'0':'默认排序','1':'ID降序','2':'ID升序','3':'发布时间降','4':'发布时间升','5':'固顶降,发布降','6':'固顶降,发布升',
				'7':'日点击降','8':'周点击降','9':'月点击降','10':'总点击降','11':'日评论降','12':'周评论降','13':'月评论降','14':'总评论降','15':'日下载降',
				'16':'周下载降','17':'月下载降','18':'总下载降','19':'日顶降','20':'周顶降','21':'月顶降','22':'总顶降'}" />
				<s:radio list='#{"all":"全部","0":"草稿","1":"待审","2":"已审","3":"回收站","4":"采集"}' name="queryStatus" listKey="key" listValue="value" onclick="_search()"></s:radio>
				<s:checkbox name="selfChannel" id="selfChannel"  onclick="_search()" /><label for="selfChannel">只显自身栏目</label>
				<input type="button" value="搜索" class="btn" onclick="_search()"/>
	  		</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<th width="20px"><input type="checkbox" id="allCheck" onclick="Pn.checkBox('ids',this.checked);"  title="全选/不选　本页所有数据"/></th>
					<th width="500px">[置顶][类型]标题</th><th>栏目</th><th>发布时间</th><!-- <th>浏览量</th> --><th>编辑</th>
					<th>状态</th><th>排序</th><th class="last">操作</th>
				</tr>
				</thead>
				<s:if test="%{pagination.list!=null||pagination.list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{pagination.list}" status="status">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><input type="checkbox" name="ids" value="<s:property value="#row.id"/>" /></td>
					<td>	
						<s:if test="#row.topLevel>0"><span class="red" style="font-weight:bold;">
							[置顶<s:if test="#row.topLevel==1">6</s:if><s:if test="#row.topLevel==2">5</s:if><s:if test="#row.topLevel==3">4</s:if><s:if test="#row.topLevel==4">3</s:if><s:if test="#row.topLevel==5">2</s:if><s:if test="#row.topLevel==6">1</s:if>]</span>
						</s:if>
						[<s:property value="#row.contentType.name"/>]
						<a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" ><s:property value="#row.tit"/></a>
					</td>
					<td><s:property value="#row.channel.name"/>[<s:property value="#row.channel.id"/>]
						<s:iterator id="chnl" value="#row.channelsWithoutMain">
							<div style="padding:0">
								<s:iterator id="node" value="#chnl.nodeList" status="stat">
									<span><s:property value="#node.name"/></span>
									<s:if test="!#stat.last"> > </s:if>
								</s:iterator>
							</div>
						</s:iterator>
					</td>
					<td><cc:datetimes format="yyyy-MM-dd HH:mm:ss" datetype="date" value="#row.releaseDate"/></td>
					<!-- <td><s:property value="#row.views"/></td> -->
					<td><s:property value="#row.user.username"/>[<s:property value="#row.editorName"/>]</td>
					<td>
						<s:if test="%{#row.draft}">草稿</s:if>
						<s:if test="%{#row.checking}">审核中</s:if>
						<s:if test="%{#row.checked}"><span class="green">审核通过</span></s:if>
						<s:if test="%{#row.recycle}"><span class="red">回收站</span></s:if>
						<s:if test="%{#row.acquisition}"><span class="red">采集</span></s:if>
					</td>
					<td>
						<s:if test="queryOrderBy==null||queryOrderBy==0">
							<span class="move"><s:if test="!#status.first">
								<a href="javascript:_operate(_up,'<s:property value="#row.id"/>')" class="pn-loperator" >
									<img src="<%=r_m%>/img/move_up.gif" alt="上移" />
								</a>
							</s:if></span>
							<s:if test="!#status.last">
					  		<a href="javascript:_operate(_down,'<s:property value="#row.id"/>')" class="pn-loperator" >
								<img src="<%=r_m%>/img/move_down.gif" alt="下移" />
							</a>
							</s:if>
						</s:if>
					</td>
					<td class="pn-lopt">
					    <a href="javascript:_operate(_edit,'<s:property value="#row.id"/>')" class="pn-loperator" >
					  		<!-- <img src="<%=r_m%>/img/edit.gif" alt="修改" /> -->修改
					  	</a> 
					  	<a href="javascript:_operate(_delete,'<s:property value="#row.id"/>')" class="pn-loperator"  >
						  	<!-- <img src="<%=r_m%>/img/delete.gif" alt="删除" /> -->删除
						</a>
						<a href="/<s:property value="#row.channel.path"/>/<s:property value="#row.id"/>.jhtml" class="pn-loperator" target="_top_black" title="预览">
							<!-- <img src="<%=r_m%>/img/prev.gif" alt="预览" /> -->预览
						</a>
					</td>
				</tr>
			    </s:iterator>
				</tbody>
				</s:if>
				<tfoot>		
					<%@ include file="/inc/page.inc"%>
				</tfoot>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>
