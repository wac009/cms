<#if (actionErrors?exists && actionErrors?size > 0)>
	<div class="action_errormsg">
	<#list actionErrors as error>
		<span class="errorMessage">${error}</span><br/>
	</#list>
	</div>
</#if>
