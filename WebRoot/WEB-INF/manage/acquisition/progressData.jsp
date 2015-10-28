<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<div style="border-right:#ffffff 1px solid;border-top:#ffffff 1px solid;background:#dcdcdc;width:800px;border-bottom:#cccccc 1px solid;height:30px;">
		<div style="overflow:hidden;width:100%;height:30px;position:absolute">
			<img id="bar" src="<%=r_m%>/img/vote_bar.gif" style="height:30px;"/>
		</div>
		<div id="percent" style="font-size:20px;width:800px;color:white;font-family:arial;font-weight:bold;height:14px;text-align:center;position:absolute;padding-top:4px;"></div>
</div>
<p></p>
<div id="messageBox" style="font-size:11px;width:800px;color:#999999;font-family:arial;height:14px;text-align:center;position:relative; padding-top:3px;"></div>
<p></p>
<s:if test="%{acquisition!=null}">
	<s:if test="%{acquisitionTemps.size>0}">
			【<s:property value="acquisition.name"/>】共有<s:property value="acquisition.totalNum"/>个地址 正在分析第<s:property value="acquisition.currNum"/>个地址<p/>
			第<s:property value="acquisition.currNum"/>个地址共有<s:property value="acquisition.totalItem"/>个内容<p/>
			<s:iterator id="row" value="%{acquisitionTemps}">
				第<s:property value="#row.seq"/>条
				<s:property value="#row.contentUrl"/>
				【<s:property value="#row.title"/>】
				<s:if test="#row.description=='SUCCESS'">
					采集成功！
				</s:if>
				<s:elseif test="#row.description=='TITLESTARTNOTFOUND'">
					采集失败!原因: 标题起始点不匹配
				</s:elseif>
				<s:elseif test="#row.description=='TITLEENDNOTFOUND'">
					采集失败!原因: 标题结束点不匹配
				</s:elseif>
				<s:elseif test="#row.description=='TITLEENDNOTFOUND'">
					采集失败!原因: 内容起始点不匹配
				</s:elseif>
				<s:elseif test="#row.description=='CONTENTENDNOTFOUND'">
					采集失败!原因: 内容结束点不匹配
				</s:elseif>
				<s:elseif test="#row.description=='TITLEEXIST'">
					采集失败!原因: 标题已存在
				</s:elseif>
				<s:else>
					未知错误
				</s:else>
				<br/>
		    </s:iterator>
		    <script>
					percent=<s:property value="percent"/>;
			</script>
	</s:if>
	<s:else>
		<div>正在分析【<s:property value="acquisition.name"/>】...</div>
	</s:else>
</s:if>
<s:else>
	没有采集任务在执行!
	<script>
			percent=0;
	</script>
</s:else>
