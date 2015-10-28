<#if (actionMessages?exists && actionMessages?size > 0)>
	<div class="action_okmsg">
		<#list actionMessages as message>
			<span class="actionMessage">${message}</span><br/>
		</#list>
	</div>
</#if>
