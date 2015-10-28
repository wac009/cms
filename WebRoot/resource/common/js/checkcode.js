Pn.ns('cnca','cnca.CheckCode','cnca.CheckCode2');
cnca.showFlash = function(src, width, height) {
	var fs = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"
			+ width + "' height='" + height + "'>";
	fs += "<param name='movie' value='" + src + "'>";
	fs += "<param name='wmode' value='transparent'>";
	fs += "<embed src='" + src;
	fs += "' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"
			+ width + "' height='" + height + "'></embed></object>";
	document.write(fs);
}
/**
 * 激活输入框显示验证码
 * @param input
 *            验证码输入框jquery对象。
 * @param url
 *            验证码的url地址。
 * @param top
 *            上下偏移量。
 */
cnca.CheckCode.cssClass = 'j-chkcode';
cnca.CheckCode.url = '/CheckCode.svl';
cnca.CheckCode = function(input, url, top) {
	this.input = input;
	this.url = url || cnca.CheckCode.url;
	this.top = top || 45;
	this.imgLayer = null;
	this.img = null;
	this.event = null;
	this.isShow = false;
	var o = this;
	var showImg = function() {
		if (o.imgLayer == null) {
			o.createHtml();
		}
		if (!o.isShow) {
			var d = new Date().getTime();
			o.img.attr('src', o.url + '?d=' + d);
			var offset = o.input.offset();
			o.imgLayer.show();
			o.imgLayer.css('top', offset.top - o.top + 'px');
			o.imgLayer.css('left', offset.left + 'px');
			o.isShow = true;
		}
	};
	var hideImg = function() {
		if (o.isShow) {
			o.event = setTimeout(function() {
				o.imgLayer.hide();
				o.isShow = false;
			}, 200);
		}
	};
	this.input.bind('focus', showImg);
	this.input.bind('blur', hideImg);
};
cnca.CheckCode.prototype.createHtml = function() {
	this.imgLayer = $('<div/>');
	this.img = $('<img border="0" alt="验证码看不清楚?请点击刷新验证码"/>');
	var o = this;
	this.img.bind('click', function() {
		o.input.focus();
		if (o.event) {
			clearTimeout(o.event);
		}
		this.src = o.url + '?d=' + new Date().getTime();
	});
	this.img.appendTo(this.imgLayer);
	this.imgLayer.appendTo(document.body);
	this.imgLayer.addClass('j-chkcode');
};
/**
 * 输入框右边显示验证码，不隐藏
 * @param input
 *            验证码输入框jquery对象。
 * @param url
 *            验证码的url地址。
 * @param top
 *            上下偏移量。
 */
cnca.CheckCode2.url = '/CheckCode.svl';
cnca.CheckCode2 = function(span, url) {
	//alert(url);
	this.span = span;
	this.url = url || cnca.CheckCode2.url;
	this.img = null;
	this.event = null;
	var o = this;
	var showImg = function() {
		o.createHtml();
		var d = new Date().getTime();
		o.img.attr('src', o.url + '?d=' + d);
	};
	showImg();
};
cnca.CheckCode2.prototype.createHtml = function() {
	this.img = $('<img border="0" alt="点击刷新" style="cursor:pointer" onerror="javascript:this.src=\"/r_m/img/space.gif\"" />');
	var o = this;
	this.img.bind('click', function() {
		if (o.event) {
			clearTimeout(o.event);
		}
		this.src = o.url + '?d=' + new Date().getTime();
	});
	this.img.appendTo(this.span);
};
