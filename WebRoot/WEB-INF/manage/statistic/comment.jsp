<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/inc/path.inc"%>
<%@include file="/inc/taglib.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/inc/manage.inc"%>
  </head>
  <script>
function getQueryForm(){
	return document.getElementById("queryForm");
}

Statistic = {};
Statistic.date = new Date();
Statistic.MonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];  
Statistic.CurrYear = function(){
	return this.date.getFullYear().toString();  
};  
Statistic.CurrMonth = function(){
	return this.date.getMonth();  
};  
Statistic.CurrDay = function(){
	return this.date.getDate();  
};  
Statistic.InitYearSelect = function(){
	var years = this.CurrYear();
	for (var i = years - 10; i <= years; i++) {  
		$("#year").get(0).options.add(new Option(i, i));  
	}   
}
Statistic.InitMonthSelect = function(){
	for (var i = 1; i < 13; i++) {  
        $("#month").get(0).options.add(new Option(i, i));  
    }  
};

Statistic.InitData = function(){
	$("#year").val(<s:if test="%{year!=null}"><s:property value="year"/></s:if><s:else>this.CurrYear()</s:else>);
	$("#month").val(<s:if test="%{month!=null}"><s:property value="month"/></s:if><s:else>this.CurrMonth()+1</s:else>);
	FlushDay();
	$("#day").val(<s:if test="%{day!=null}"><s:property value="day"/></s:if><s:else>this.CurrDay()</s:else>);
}

function FlushDay(){
	var year = $("#year");  
    var month = $("#month");  
    var day = $("#day");  
	var mn = month.val();  
    var ye = year.val();  
    var n = Statistic.MonthDays[mn - 1];  
    day.empty();  
    if (ye % 4 == 0 && mn==2) {  
            n++;  
    }  
    for (var i = 1; i < n + 1; i++) {  
        day.get(0).options.add(new Option(i, i));  
    }  
}

function init(){
	$("#year").change(FlushDay);
    $("#month").change(FlushDay); 
	Statistic.InitYearSelect();
	Statistic.InitMonthSelect();
    chgModel($("input[name='queryModel']:checked").val()); 
    Statistic.InitData();
}

function chgModel(queryModel){
	if(queryModel=="year"){
		FlushDay();
		$("#month").attr("disabled",true);
		$("#day").attr("disabled",true);
	}
	if(queryModel=="month"){
		FlushDay();
		$("#month").attr("disabled",false);
		$("#day").attr("disabled",true);
	}
	if(queryModel=="week"){
		$("#month").attr("disabled",false);
		$("#day").attr("disabled",false);
	}
	if(queryModel=="day"){
		$("#month").attr("disabled",false);
		$("#day").attr("disabled",false);
	}
}

function querySubmit(){
	$("input[name='year']").val($("#year").val());
	$("input[name='month']").val($("#month").val());
	$("input[name='day']").val($("#day").val());
	getQueryForm().submit();
}


$(function(){
	init();
});
</script>
  <body>
  	<div id="container">
  	  	<div class="navi"><span class="navi_b">当前位置:</span>
  	  		<span class="navi_h">统计 - 评论统计</span>
  	  	</div>
  		<div class="mainData">
  		<div class="actInfo"><s:actionerror theme="ccweb"/><s:actionmessage theme="ccweb"/></div> 
  		<s:form  id="queryForm" action="statistic_comment">
	  		<input type="hidden" name="year"/>
			<input type="hidden" name="month"/>
			<input type="hidden" name="day"/>
	  		<div class="search">
				<select id="year"></select>年
				<select id="month"></select>月
				<select id="day"></select>日
				<input type="radio" name="queryModel" id="qmyear" value="year" onclick="chgModel(this.value);"  <s:if test='statisticModel.toString()=="year"'>checked="checked" </s:if> /><label for="qmyear">年报表</label>
				<input type="radio" name="queryModel" id="qmmonth" value="month" onclick="chgModel(this.value);" <s:if test='statisticModel.toString()=="month"'>checked="checked"</s:if> /><label for="qmmonth">月报表</label>
				<input type="radio" name="queryModel" id="qmweek" value="week" onclick="chgModel(this.value);" <s:if test='statisticModel.toString()=="week"'>checked="checked"</s:if> /><label for="qmweek">周报表</label>
				<input type="radio" name="queryModel" id="qmday" value="day" onclick="chgModel(this.value);" <s:if test='statisticModel.toString()=="day"'>checked="checked"</s:if> /><label for="qmday">日报表</label>
	  			<input name="replyed" type="radio" id="q1" value="" <s:if test='replyed==null||replyed==""'>checked="checked" </s:if> /><label for="q1">全部</label>
				<input name="replyed" type="radio" id="q2"  value="true" <s:if test='replyed!=null&&replyed=="true"'>checked="checked" </s:if> /><label for="q2">已回复</label>
				<input name="replyed" type="radio" id="q3"  value="false" <s:if test='replyed!=null&&replyed=="false"'>checked="checked" </s:if> /><label for="q3">未回复</label>
				<input type="submit" value="查询" class="btn" />
	  		</div>
	  		<div class="data">
				<table class="pn-ltable">
				<thead class="pn-lthead">
				<tr>
					<s:if test='statisticModel.toString()=="year"'>
						<th>月份</th>
					</s:if>
					<s:if test="statisticModel.toString()=='month'">
						<th>日期</th>
					</s:if>
					<s:if test="statisticModel.toString()=='week'">
						<th>星期</th>
					</s:if>
					<s:if test="statisticModel.toString()=='day'">
						<th>时间</th>
					</s:if>
					<th>评论数</th><th>百分比</th>
				</tr>
				</thead>
				
				<s:if test="%{list!=null||list.size>0}">
				<tbody class="pn-ltbody">
				<s:iterator id="row" value="%{list}">
				  <tr name="tr_ids" onmouseover="Pn.LTable.lineOver(this);" onmouseout="Pn.LTable.lineOut(this);" onclick="Pn.LTable.lineSelect(this);">
					<td><s:property value="#row.description"/></td>
					<td><s:property value="#row.count"/></td>
					<td>
						<s:property value="#row.percent"/>
						<img src="<%=basePath%>/res/m/img/vote_bar.gif" width="<s:property value="#row.barWidth"/>" height="10px" border="0"/>
					</td>
				</tr>
			    </s:iterator>
				</tbody>
				</s:if>
				</table>				
  			</div>
  		</s:form>
  		</div>
  	</div>	
  </body>
</html>
