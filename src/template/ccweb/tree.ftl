<#--调用macro 生成树-->

<@tree root=parameters.root treeId=parameters.treeId target=parameters.target showDeep=parameters.showDeep vld=parameters.vld
	   valueContent=parameters.valueContent suffix=parameters.suffix
	   isCheckBox=parameters.isCheckBox onlyLeafCheckBox=parameters.onlyLeafCheckBox checkBoxName=parameters.checkBoxName 
	   isUrl=parameters.isUrl isJavascriptUrl=parameters.isJavascriptUrl onlyLeafUrl=parameters.onlyLeafUrl 
	   isFile=parameters.isFile
	   isRadio=parameters.isRadio onlyLeafRadio=parameters.onlyLeafRadio radioName=parameters.radioName 
	   durl=parameters.durl url=parameters.url dparams=parameters.dparams params=parameters.params/>

<#--
	root：树对象。
	treeName：节点名称；
	id：节点id，使用复选框时需要使用；
	treeUrl：节点链接（${base+node.treeUrl!}${suffix}）；
	isLeaf：是否叶子节点，主要用于动态加载树，为NULL则检查是否有子节点；
	child：子节点。
	treeId：树的ID。在一个页面有多个树时，需要使用。
	target：目标。链接打开的目标frame。
	showDeep：展开深度。0不展开。
	isCheckBox：是否是复选框。
	onlyLeafCheckBox: 只是叶子显示复选框
	checkBoxName：复选框的名称。
	valueContent：值集合，指示复选框是否选中。
	isUrl：是否有链接。
	suffix：链接后缀。
-->
<#macro tree root treeId="t" target="right" showDeep=1 
	isCheckBox=false onlyLeafCheckBox=true checkBoxName="ids"
	isRadio=false onlyLeafRadio=true radioName="id" 
	isFile = false
	vld="" valueContent=[]
	childName="child" treeName="node.treeName"
	url="" onlyLeafUrl=false durl="" params={'id':'id'} dparams={'id':'id'} prefix=base+"/manage/" suffix=".jspa" isUrl=true isJavascriptUrl=false>
<#assign _tree_params=params/>
<#assign _tree_dparams=dparams/>
<#assign _tree_child_name=childName/>
<#assign _tree_tree_name=treeName/>
<#assign _checkbox_vld=vld/>
<div id="${treeId}" class="pn-tree">
<@drawNode node=root treeId=treeId pid=treeId target=target showDeep=showDeep 
	isCheckBox=isCheckBox onlyLeafCheckBox=onlyLeafCheckBox checkBoxName=checkBoxName 
	isRadio=isRadio onlyLeafRadio=onlyLeafRadio radioName=radioName 
	isFile=isFile
	valueContent=valueContent 
	url=url durl=durl prefix=prefix suffix=suffix isUrl=isUrl isJavascriptUrl=isJavascriptUrl onlyLeafUrl=onlyLeafUrl/>
</div>
</#macro>
<#--
	DIV的ID命名规则及结构：
	<pid-index>（整体）
		<pid-index-s>（self，自身）
			<空白列><线条列><节点列><名称列>
		</pid-index-s>
		<pid-index->（子集）
			...
		</pid-index->
	</pid-index>
	node：节点
	deep：深度
	isEndList：祖先节点是否为末节点
	pid：父级ID
	index：当前层的序号
-->
<#macro drawNode node pid treeId target showDeep valueContent 
	isCheckBox onlyLeafCheckBox checkBoxName 
	isRadio onlyLeafRadio radioName isFile
	url durl prefix suffix isUrl isJavascriptUrl onlyLeafUrl
	deep=0 isEndList=[true] index=0>

<#local id=pid+'-'+index />
<#if showDeep gt deep>
	<#local isDisplay=true />
<#else>
	<#local isDisplay=false />
</#if>
<div id="${id}">
<div id="${id}-s" onmouseover="Pn.Tree.lineOver(this)" onmouseout="Pn.Tree.lineOut(this)" onclick="Pn.Tree.lineSelected(this,'${treeId}');" isDisplay="${isDisplay?string}">
<#--空格列-->
<#if deep gt 0> 
<img src="${base+'/res/c/img/tree/s.gif'}" width="15px" /><#t/>
</#if>
<#--直线列-->
<#if deep gt 1>
	<#list 2..deep as i>
		<#if isEndList[i-1]>
			<img src="${base+'/res/c/img/tree/s.gif'}" width="15px" /><#t/>
		<#else>
			<img src="${base+'/res/c/img/tree/elbow-line.gif'}" /><#t/>
		</#if>
	</#list>
</#if>
<#--节点列-->
<#if node.treeLeaf??>
	<#assign isLeaf=node.treeLeaf/>
<#elseif (node[_tree_child_name])??>
	<#assign isLeaf=!(node[_tree_child_name]?size>0)/>
<#else>
	<#assign isLeaf=true/>
</#if>
<#if isDisplay>
	<#local openDisplay="" />
	<#local closeDisplay="display:none;" />
<#else>
	<#local openDisplay="display:none;" />
	<#local closeDisplay="" />
</#if>
<#if isLeaf && isEndList[deep]>
	<img src="${base+'/res/c/img/tree/elbow-end.gif'}" /><img src="${base+'/res/c/img/tree/leaf.gif'}" /><#t/>
<#elseif isLeaf && !isEndList[deep]>
	<img src="${base+'/res/c/img/tree/elbow.gif'}" /><img src="${base+'/res/c/img/tree/leaf.gif'}" /><#t/>
<#elseif !isLeaf && isEndList[deep]>
	<img id="${id}-co" src="${base+'/res/c/img/tree/elbow-end-minus.gif'}" onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;${openDisplay}"/><#t/>
	<img id="${id}-cc" src="${base+'/res/c/img/tree/elbow-end-plus.gif'}" onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;${closeDisplay}"/><#t/>
	<img id="${id}-fo" src="${base+'/res/c/img/tree/folder-open.gif'}" style="${openDisplay}"/><#t/>
	<img id="${id}-fc" src="${base+'/res/c/img/tree/folder.gif'}" style="${closeDisplay}"/><#t/>
<#elseif !isLeaf && !isEndList[deep]>
	<img id="${id}-co" src="${base+'/res/c/img/tree/elbow-minus.gif'}" onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;${openDisplay}"/><#t/>
	<img id="${id}-cc" src="${base+'/res/c/img/tree/elbow-plus.gif'}" onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;${closeDisplay}"/><#t/>
	<img id="${id}-fo" src="${base+'/res/c/img/tree/folder-open.gif'}" style="${openDisplay}"/><#t/>
	<img id="${id}-fc" src="${base+'/res/c/img/tree/folder.gif'}" style="${closeDisplay}"/><#t/>
</#if>
<#--表单选择列-->
<#if isCheckBox>
	<#if onlyLeafCheckBox>
		<#if isLeaf>
			<input type="checkbox" name="${checkBoxName}" id="${id}-chk" <#rt/>
				<#if valueContent?seq_contains(node.id)>checked="true"</#if><#lt/>value="${node.id}" nodeList='[<#list node.nodeList as n>"${n.name}"<#if n_has_next>,</#if></#list>]' class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
		</#if>
	<#else>
		<#if node.id??>
		<input type="checkbox" name="${checkBoxName}"  id="${id}-chk" <#rt/>
			<#if valueContent?seq_contains(node.id)>checked="true"</#if><#lt/>value="${node.id}" nodeList='[<#list node.nodeList as n>"${n.name}"<#if n_has_next>,</#if></#list>]' class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
		</#if>
	</#if>
</#if>
<#if isRadio>
	<#if onlyLeafRadio>
		<#if isLeaf>
			<#if isFile>
				<input type="radio" name="${radioName}" id="${id}-chk" <#rt/>
				<#if valueContent?seq_contains(node.name)>checked="true"</#if><#lt/>value="${node.name}" relPath="${node.relPath}" class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
			<#else>
				<input type="radio" name="${radioName}" id="${id}-chk" <#rt/>
				<#if valueContent?seq_contains(node.id)>checked="true"</#if><#lt/>value="${node.id}" nodeList='[<#list node.nodeList as n>"${n.name}"<#if n_has_next>,</#if></#list>]' class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
			</#if>
		</#if>
	<#else>
		<#if isFile>
			<input type="radio" name="${radioName}" id="${id}-chk" <#rt/>
			<#if valueContent?seq_contains(node.name)>checked="true"</#if><#lt/>value="${node.name}" relPath="${node.relPath}" class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
		<#else>
			<input type="radio" name="${radioName}" id="${id}-chk" <#rt/>
			<#if valueContent?seq_contains(node.id)>checked="true"</#if><#lt/>value="${node.id}" nodeList='[<#list node.nodeList as n>"${n.name}"<#if n_has_next>,</#if></#list>]' class="pntree-checkbox" size="0" onclick="Pn.Tree.switchSelect(this,'${id}','${treeId}');" /><#t/>
		</#if>
	</#if>
</#if>  
<#--名称列-->
<#if !isLeaf>
<#if !isCheckBox>
&nbsp;<#t/>
</#if>

<#if !isUrl||onlyLeafUrl>
	<span onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;">${_tree_tree_name?eval}</span>
<#else>
	<#if (node.treeUrl?? && node.treeUrl!='') || durl!=''>
		<#if isJavascriptUrl>
			<a href="${(durl!node.treeUrl)}<#rt/>
			" target="${target}" onclick="Pn.Tree.switchDisplay('${id}',true)">${_tree_tree_name?eval}</a>
		<#else>
			<a href="${prefix+(durl!node.treeUrl)+suffix}<#rt/>
			<#list _tree_dparams?keys as pa>
			<#if pa_index==0>?<#else>&</#if>${pa}=${node[_tree_dparams[pa]]!}<#rt/>
			</#list>
			" target="${target}" onclick="Pn.Tree.switchDisplay('${id}',true)">${_tree_tree_name?eval}</a>
		</#if>
	<#else>
		<span onclick="Pn.Tree.switchDisplay('${id}')" style="cursor:pointer;">${_tree_tree_name?eval}</span>
	</#if>
</#if>
</div>
<#--子节点开始-->
<div id="${id+'-'}" style="${openDisplay}">
<#if node[_tree_child_name]??>
<#list node[_tree_child_name] as cnode>
<@drawNode node=cnode pid=id target=target treeId=treeId showDeep=showDeep 
	isCheckBox=isCheckBox onlyLeafCheckBox=onlyLeafCheckBox checkBoxName=checkBoxName 
	isRadio=isRadio onlyLeafRadio=onlyLeafRadio radioName=radioName 
	isFile=isFile
	valueContent=valueContent 
	url=url durl=durl prefix=prefix suffix=suffix isUrl=isUrl isJavascriptUrl=isJavascriptUrl onlyLeafUrl=onlyLeafUrl
	deep=deep+1 isEndList=isEndList+[!cnode_has_next] index=cnode_index />

</#list>
</#if>
</div>
<#else>
<#if isCheckBox || !isUrl>
<label for="${id}-chk"><span>${_tree_tree_name?eval}</span></label>
<#else>
	<#if isJavascriptUrl>
		<a href="${(url!node.treeUrl)}<#rt/>
		" target="${target}" value="path" onclick="Pn.Tree.switchDisplay('${id}',true)">${_tree_tree_name?eval}</a>
	<#else>
		&nbsp;<a href="${prefix+(url!node.treeUrl)+suffix}<#rt/>
		<#list _tree_params?keys as pa>
		<#if pa_index==0>?<#else>&</#if>${pa}=${node[_tree_params[pa]]!}<#rt/>
		</#list>
		" target="${target}">${_tree_tree_name?eval}</a>
	</#if>
</#if>
</div>
</#if>
</div>
</#macro>





