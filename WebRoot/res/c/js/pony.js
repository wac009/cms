Pn = {
	version : '1.0'
};
/**
 * Pn.ns('Company', 'Company.data');
 */
Pn.ns = function() {
	var a = arguments, o = null, i, j, d, rt;
	for (i = 0;i < a.length; ++i) {
		d = a[i].split(".");
		rt = d[0];
		eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = '
				+ rt + ';');
		for (j = 1;j < d.length; ++j) {
			o[d[j]] = o[d[j]] || {};
			o = o[d[j]];
		}
	}
};
/**
 * 创建html对象
 */
Pn.create = function(a) {
	return document.createElement(a);
};
/**
 * html模板
 * 使用#()作为占位符
 */
Pn.Template = function(s) {
	this.tpl = s;
}
Pn.Template.prototype.create = function(params) {
	var s = this.tpl;
	for(var p in params) {
		s = s.replace(new RegExp("#\\("+p+"\\)", "g") ,params[p]);
	}
	return s;
}
/**
 * 获得本页面上唯一的ID
 */
Pn.UID = 0;
Pn.getUID = function() {
	Pn.UID++;
	return Pn.UID;
}
// 设置全局 AJAX 默认选项。
$.ajaxSetup( {
	dataType : "json",
	type : "POST",
	beforeSend : function(xhr) {
		xhr.setRequestHeader('isAjax', 'true');
	}
});
jQuery.extend( {
	postJson : function(url, data, success, error) {
		$.ajax( {
			url : url,
			data : data,
			success : success,
			error : error
		});
	}
});
/**
 * 获得url参数。doc：文本对象document；key：参数。返回：value。
 */
Pn.getParam = function(key) {
	var aParams = location.search.substr(1).split('&');
	var aKv;
	for (var i = 0;i < aParams.length; i++) {
		aKv = aParams[i].split('=');
		if (aKv[0] == key) {
			return aKv[1];
		}
	}
};
/**
 * 复选框全选或取消
 * 
 * @param name
 *            复选框名称
 * @param checked
 *            of boolean 全选或取消
 */
Pn.checkBox = function(name, checked) {
	$("input[name='" + name + "']").attr("checked", checked);
	if(checked){
		Pn.LTable.lineSelect($("tr[name='tr_" + name + "']"));
	}else{
		$("tr[name='tr_" + name + "']").removeClass("pn-lselected");	
	}
}
/**
 * 复选框选中的个数
 * 
 * @param name
 *            string of checkbox name
 */
Pn.checkedCount = function(name) {
	alert(name);
	var batchChecks = document.getElementsByName(name);
	var count = 0;
	for (var i = 0;i < batchChecks.length; i++) {
		if (batchChecks[i].checked) {
			count++;
		}
	}
	return count;
}
//@ TODO 是否需要
Pn.checkLen = function(o, len) {
	len = len || 1;
	if (o.val() == undefined || $.trim(o.val()) == ''
			|| $.trim(o.val()).length < len) {
		return false;
	} else {
		return true;
	}
}
Pn.Cookie = function Cookie() {
	this.cookies = [];
	/**
	 * @param name
	 */
	this.get = function(name) {
		this._reset();
		return this.cookies[name];
	}

	/**
	 * name & value must be Required
	 * 
	 * @param {string}
	 *            name (Required)
	 * @param {string}
	 *            value (Required)
	 * @param {integer}
	 *            expires (optional, [Units:seconde] , exp (one day):
	 *            1*24*60*60)
	 * @param {string}
	 *            path (optional, exp: '/')
	 * @param {string}
	 *            domain (optional, exp: '.a.com')
	 * @param {boolean}
	 *            secure (optional, exp: false)
	 */
	this.set = function() {
		var args = this.set.arguments;
		var _num = args.length;
		if (_num < 2) {
			return;
		}
		var _cookie = args[0] + '=' + this._encode(args[1]);
		if (_num >= 3) {
			var now = new Date();
			var _expires = new Date(now.getTime() + args[2]);
			_cookie += ';expires=' + _expires.toUTCString();
		}
		if (_num >= 4) {
			_cookie += ';path=' + args[3];
		}
		if (_num >= 5) {
			_cookie += ';domain=' + args[4];
		}
		if (_num >= 6) {
			_cookie += ';secure';
		}
		document.cookie = _cookie;
	}

	this.print_r = function() {
		this._reset();
		for (var i in this.cookies) {
			alert(i + ' -> ' + this.cookies[i]);
		}
	}
	this._reset = function() {
		var cookie = document.cookie.split(';');
		var _num = cookie.length;
		for (var i = 0;i < _num; i++) {
			var _arr = cookie[i].split('=');
			var _name = this._trim(_arr[0]);
			var _value = '';
			if (typeof _arr[1] != 'undefined') {
				_value = this._decode(this._trim(_arr[1]));
			}
			this.cookies[_name] = _value;
		}
	}
	this._trim = function(_str) {
		return _str.replace(/(^\s+)|(\s*$)/g, '');
	}
	this._encode = function(_str) {
		return encodeURI(_str);
	}
	this._decode = function(_str) {
		return decodeURI(_str);
	}
}
Pn.Cookie.countPerPage = "_countPerPage";
Pn.ns('Pn.LTable');
Pn.LTable.lineOver=function(o){
	$(o).addClass("pn-lhover");
};
Pn.LTable.lineOut=function(o){
	$(o).removeClass("pn-lhover");
};
Pn.LTable.lineSelect=function(o){
	if(Pn.LTable.lineSelected){
		$(Pn.LTable.lineSelected).removeClass("pn-lselected");
	}
	Pn.LTable.lineSelected=o;
	$(o).addClass("pn-lselected");
}
Pn.ns('Pn.Tree');
Pn.Tree.switchDisplay=function(id,open) {	
	var isDisplay = $('#'+id+'-s').attr("isDisplay");
	if(open&&isDisplay=="true") {
		return;
	}
	if(isDisplay=="true") {		
		$('#'+id+'-co').hide();
		$('#'+id+'-fo').hide();
		$('#'+id+'-cc').show();
		$('#'+id+'-fc').show();
		$('#'+id+'-').hide();
		$('#'+id+'-s').attr("isDisplay","false");
	} else {
		$('#'+id+'-cc').hide();
		$('#'+id+'-fc').hide();
		$('#'+id+'-co').show();
		$('#'+id+'-fo').show();
		$('#'+id+'-').show();
		$('#'+id+'-s').attr("isDisplay","true");
	}
}
Pn.Tree.switchSelect=function(element,id,treeId) {
	if(element.checked) {
		$("input:checkbox",$('#'+id)).each(function(){this.checked=true;});
		var head = treeId;
		var tail = "-chk";
		//取去除头，取中间部分：t-0-6-1=-0-6-1
		var middle = id.substring(treeId.length);
		while(true) {
			middle = middle.substring(0,middle.lastIndexOf("-"));
			if(middle.length > 0) {
				$('#'+head+middle+tail).attr("checked",true);
			} else {
				break;
			}
		}
	} else {
		$("input:checkbox",$('#'+id)).each(function(){this.checked=false;});
	}
}
Pn.Tree.lineOver=function(element) {
	$(element).addClass("pn-tree-hover");
}
Pn.Tree.lineOut=function(element) {
	$(element).removeClass("pn-tree-hover");
}
Pn.Tree.lineSelected=function(element,treeId) {
	var selectedId = $('#'+treeId).attr("selectedId");
	if(selectedId){
		$('#'+selectedId).removeClass("pn-tree-selected");
	}
	$('#'+treeId).attr("selectedId",$(element).attr("id"));
	$(element).addClass("pn-tree-selected");	
}

/**
 * 颜色选择器
 */
Pn.ColorPicker = function(input) {
	var obj = this;
	this.isShow = false;
	this.target = $(input);
	this.colorTxt=$("<input type='text' disabled='true' style='border:1px solid #ccc;border-left:none; "
			+"background-color:#fff;cursor:pointer;padding:3px 0;width:40px; margin-right:2px;'/>");
	this.button = $("<input type='button' value='选取' "
			+ "style='border:1px solid #ccc;cursor:pointer;height:24px;line-height:24px;width:40px;'/>");
	this.target.after(this.colorTxt);
	this.colorTxt.after(this.button);
	this.over = function() {
		$(this).css("border", "1px solid #000");
	}
	this.out = function() {
		$(this).css("border", "1px solid #fff");
	}
	this.click = function() {
		var color = $(this).attr("title");
		obj.setColor(color);
		this.isShow = false;
		obj.picker.hide();
	}
	this.createPicker = function() {
		var c = ["#FF8080", "#FFFF80", "#80FF80", "#00FF80", "#80FFFF",
				"#0080FF", "#FF80C0", "#FF80FF", "#FF0000", "#FFFF00",
				"#80FF00", "#00FF40", "#00FFFF", "#0080C0", "#8080C0",
				"#FF00FF", "#804040", "#FF8040", "#00FF00", "#008080",
				"#004080", "#8080FF", "#800040", "#FF0080", "#800000",
				"#FF8000", "#008000", "#008040", "#0000FF", "#0000A0",
				"#800080", "#8000FF", "#400000", "#804000", "#004000",
				"#004040", "#000080", "#000040", "#400040", "#400080",
				"#000000", "#808000", "#808040", "#808080", "#408080",
				"#C0C0C0", "#400040", "#FFFFFF"];
		var s = "<table style='display:none;position:absolute;border:1px solid #ccc;background-color:#fff'>"
				+ "<tr height='17px'>";
		// 列数
		var col = 8;
		for (var i = 0, len = c.length;i < len; i++) {
			s += "<td width='17px' style='padding:3px 0 0 3px;'><div class='c' style='width:13px;height:13px;"
					+ "border:1px solid #fff;cursor:pointer;background-color:"
					+ c[i] + "' title='" + c[i] + "'>&nbsp;<div></td>";
			if ((i + 1) != c.length && (i + 1) % col == 0) {
				s += "</tr><tr height='17px'>";
			}
		}
		s += "</tr></table>";
		var picker = $(s);
		picker.find(".c").each(function() {
			$(this).bind("mouseover", obj.over);
			$(this).bind("mouseout", obj.out);
			$(this).bind("click", obj.click);
		});
		$(document.body).append(picker);
		return picker;
	}
	this.setColor = function(color) {
		obj.target.val(color);
		if (color == "") {
			color = "#fff";
		}
		try {
			obj.colorTxt.css( {
				backgroundColor : color
			});
		} catch (e) {
//			alert("color error: " + color);
			obj.target.val("");
			obj.target.focus().select();
		}
	}
	this.picker = this.createPicker();
	this.showPicker = function() {
		if (!obj.isShow) {
			obj.isShow = true;
			obj.picker.showBy(obj.target);
			$(document).bind("mousedown", function() {
				$(document).unbind("mousedown");
				setTimeout(function() {
					obj.isShow = false;
					obj.picker.hide();
					var val = obj.target.val();
					obj.setColor(obj.target.val());
				}, 200);
			});
		}
	}
	this.button.bind("click", obj.showPicker);
	this.target.bind("blur", function() {
		obj.setColor(obj.target.val());
	});
	var v = this.target.val();
	if (v != "") {
		this.setColor(v);
	}
};
$.fn.extend( {
	colorPicker : function() {
		new Pn.ColorPicker(this);
	}
});

