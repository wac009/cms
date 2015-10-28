﻿/**
 * ymPrompt.js 消息提示组件
 * @author netman8410@163.com
 */
//<meta http-equiv="X-UA-Compatible" content="IE=7" />  IE8透明度解决方案
(function() {
	if (window.ymPrompt) return;
	window.ymPrompt = {
		version: '4.0',
		pubDate: '2009-02-26',
		apply: function(o, c, d) {
			if (d) ymPrompt.apply(o, d);
			if (o && c && typeof c == 'object') for (var p in c) o[p] = c[p];
			return o;
		},
		eventList: []
	};

	/*初始化可能在页面加载完成调用的接口，防止外部调用失败。_initFn:缓存初始调用传入的参数*/
	var initFn = ['setDefaultCfg','show'], _initFn = {},t;
	while(t=initFn.shift()) ymPrompt[t] = eval('0,function(){_initFn.'+t+'?_initFn.'+t+'.push(arguments):(_initFn.'+t+'=[arguments])}');

	/*以下为公用函数及变量*/
	var isIE=!+'\v1';	//IE浏览器
	var isCompat=document.compatMode == 'CSS1Compat';
	var IE6 = isIE && /MSIE (\d)\./.test(navigator.userAgent) && parseInt(RegExp.$1) < 7; //IE6需要用iframe来遮罩
	var useFixed=!isIE||(!IE6&&isCompat);	//滚动时IE7+及其它浏览器使用Fixed定位
	var $ = function(id) {
		return document.getElementById(id)
	}; //获取元素
	var $height = function(obj) {
		return parseInt(obj.style.height) || obj.offsetHeight
	}; //获取元素高度
	var addEvent = (function() {
		return new Function('env','fn','obj',['obj=obj||document;', window.attachEvent ? "obj.attachEvent('on'+env,fn)": 'obj.addEventListener(env,fn,false)', ';ymPrompt.eventList.push([env,fn,obj])'].join(''))
	})(); //事件绑定
	var detachEvent = (function() {
		return new Function('env','fn','obj',['obj=obj||document;', window.attachEvent ? "obj.detachEvent('on'+env,fn)": 'obj.removeEventListener(env,fn,false)'].join(''))
	})(); //取消事件绑定

	//为元素的特定样式属性设定值
	var setStyle = function(el, n, v) {
		if (!el) return;
		if (typeof n == 'object') {
			for (var i in n) setStyle(el, i, n[i]);
			return;
		}
		/*dom数组或dom集合*/
		if (el instanceof Array || /htmlcollection|nodelist/i.test(''+el)) {
			for (var i = el.length - 1; i >= 0; i--) setStyle(el[i], n, v);
			return;
		}
		try{el.style[n] = v}catch(e){}
	};
	/*----------------和业务有关的公用函数-----------------*/
	var btnIndex = 0, btnCache, seed = 0; //当前焦点的按钮的索引、当前存在的按钮、id种子
	/*创建按钮*/
	var mkBtn = function(txt, sign, autoClose, id) {
		if (!txt) return;
		if (txt instanceof Array) {
			/*无效按钮删除*/
			var item,t=[],dftBtn={OK:[curCfg.okTxt, 'ok'], CANCEL:[curCfg.cancelTxt, 'cancel']};
			while(txt.length) (item=txt.shift())&&t[t.push(mkBtn.apply(null, dftBtn[item]||item))-1]||t.pop();
			return t;
		}
		id = id || 'ymPrompt_btn_' + seed++;
		autoClose = autoClose == undefined ? 'undefined': !!autoClose;
		return {
			id: id,
			html: "<input type='button' id='" + id + "' onclick='ymPrompt.doHandler(\"" + sign + "\"," + autoClose + ")' style='cursor:pointer' onmouseover='this.className=\"btnStyle btnOnStyle\"' onmouseout='this.className=\"btnStyle\"' class='btnStyle handler' value='" + txt + "' />"
		};
	};
	/*生成按钮组合的html*/
	var joinBtn = function(btn) {
		if (!btn) return btnCache = '';
		if (! (btn instanceof Array)) btn = [btn];
		if(!btn.length) return btnCache='';
		btnCache = btn.concat();
		var html=[];
		while(btn.length) html.push(btn.shift().html);
		return html.join('');
	}
	/*默认显示配置及用户当前配置*/
	var dftCfg = {
		message: '内容',	//消息框按钮
		width: 400,
		height: 160,
		title: '系统提示',		//消息框标题
		handler: function() {}, //回调事件
		maskAlphaColor: '#000',	//遮罩透明色
		maskAlpha: 0.1,		//遮罩透明度
		iframe: false,		//iframe模式
		icoCls: '',
		btn: null,
		autoClose: true,		//点击关闭、确定等按钮后自动关闭
		fixPosition: true,		//随滚动条滚动
		dragOut: false,			//不允许拖出窗体范围
		titleBar: true,			//显示标题栏
		showMask: true,			//显示遮罩
		winPos: 'c',		//在页面中间显示
		winAlpha:0.8,		//拖动窗体时窗体的透明度
		closeBtn:true,
		showShadow:false,	//不显示阴影，只对IE有效
		useSlide:false,		//不使用淡入淡出
		slideCfg:{increment:0.3,interval:50},	//淡入淡出配置
		closeTxt: '关闭',
		okTxt:' 确 定 ',
		cancelTxt:' 取 消 ',
		msgCls:'ym-content',
		minBtn:false,
		minTxt:'最小化',
		maxBtn:false,
		maxTxt:'最大化'
	},curCfg = {};
	/*开始解析*/
	(function() {
		var rootEl=document.body,callee=arguments.callee;
		if (!rootEl || typeof rootEl != 'object') return addEvent('load', callee, window); //等待页面加载完成
		/*防止在IE下因document未就绪而报“IE无法打开INTERNET站点的错”的错*/
		if(isIE && document.readyState!='complete') return addEvent('readystatechange',function(){if(document.readyState=="complete")callee()});
		rootEl = isCompat ? document.documentElement: rootEl; //根据html Doctype获取html根节点，以兼容非xhtml的页面

		var frameset=document.getElementsByTagName('frameset').length;	//frameset页面
		if(!isIE&&frameset) return;	//frameset页面且不是IE则直接返回，否则会出现错误。
		/*获取scrollLeft和scrollTop，在fixed定位时返回0，0*/
		var getScrollPos=function(){
			return curCfg.fixPosition&&useFixed?[0,0]:[rootEl.scrollLeft,rootEl.scrollTop];
		}
		/*保存窗口定位信息，弹出窗口相对页面左上角的坐标信息*/
		var saveWinInfo = function() {
			var pos=getScrollPos();
			ymPrompt.apply(dragVar, {
				_offX:parseInt(ym_win.style.left)-pos[0],
				_offY:parseInt(ym_win.style.top)-pos[1]
			});
		};
		/*-------------------------创建弹窗html-------------------*/
		var maskStyle = 'position:absolute;top:0;left:0;display:none;text-align:center';
		var div = document.createElement('div');
		div.innerHTML = [
		/*遮罩*/
		"<div id='maskLevel' style=\'" + maskStyle + ';z-index:10000;\'></div>', IE6 ? ("<iframe id='maskIframe' style='" + maskStyle + ";z-index:9999;filter:alpha(opacity=0);opacity:0'></iframe>") : '',
		/*窗体*/
		"<div id='ym-window' style='position:absolute;z-index:10001;display:none'>", IE6 ? "<iframe style='width:100%;height:100%;position:absolute;top:0;left:0;z-index:-1'></iframe>": '', "<div class='ym-tl' id='ym-tl'><div class='ym-tr'><div class='ym-tc' style='cursor:move;'><div class='ym-header-text'></div><div class='ym-header-tools'>",
		"<div class='ymPrompt_min' title='最小化'><strong>0</strong></div>",
		"<div class='ymPrompt_max' title='最大化'><strong>1</strong></div>",
		"<div class='ymPrompt_close' title='关闭'><strong>r</strong><a class='a_close' href='javascript:void();'></a></div>",
		"</div></div></div></div>", "<div class='ym-ml' id='ym-ml'><div class='ym-mr'><div class='ym-mc'><div class='ym-body'></div></div></div></div>", "<div class='ym-ml' id='ym-btnl'><div class='ym-mr'><div class='ym-btn'></div></div></div>", "<div class='ym-bl' id='ym-bl'><div class='ym-br'><div class='ym-bc'></div></div></div>", "</div>",
		/*阴影*/
		isIE ? "<div id='ym-shadow' style='position:absolute;z-index:10000;background:#808080;filter:alpha(opacity=80) progid:DXImageTransform.Microsoft.Blur(pixelradius=2);display:none'></div>":''
		].join('');
		document.body.appendChild(div);

		/*窗口上的对象*/
		/*mask、window*/
		var maskLevel = $('maskLevel');
		var ym_win = $('ym-window');
		var ym_shadow = $('ym-shadow');
		var ym_wins;
		/*header*/
		var ym_headbox = $('ym-tl');
		var ym_head = ym_headbox.firstChild.firstChild;
		var ym_hText = ym_head.firstChild;
		var ym_hTool = ym_hText.nextSibling;
		/*content*/
		var ym_body = $('ym-ml').firstChild.firstChild.firstChild;
		/*button*/
		var ym_btn = $('ym-btnl');
		var ym_btnContent = ym_btn.firstChild.firstChild;
		/*bottom*/
		var ym_bottom = $('ym-bl');

		var maskEl=[maskLevel];	//遮罩元素
		IE6&&maskEl.push($('maskIframe'));
		var ym_ico=ym_hTool.childNodes;	//右上角的图标

		var dragVar = {};
		/*窗口的最大化最小化核心功能实现*/
		var cur_state='normal',cur_cord=[0,0];	//cur_cord记录最大化前窗口的坐标
		var cal_cord=function(){
			var pos=getScrollPos();
			cur_cord=[parseInt(ym_win.style.left)-pos[0],parseInt(ym_win.style.top)-pos[1]]
		};	//保存坐标
		var get_cord=function(){
			var pos=getScrollPos();
			return [cur_cord[0]+pos[0],cur_cord[1]+pos[1]]
		};	//获取当前坐标
		var doMax=function(){
			if(cur_state!='max'){
				cal_cord();
				cur_state='max';
				ym_ico[1].firstChild.innerHTML='2';
				ym_ico[1].className='ymPrompt_normal';
			}
			setWinSize(rootEl.clientWidth,rootEl.clientHeight,getScrollPos());
		};
		var doMin=function(){
			if(cur_state!='min'){
				cal_cord();
				cur_state='min';
				ym_ico[0].firstChild.innerHTML='2';
				ym_ico[0].className='ymPrompt_normal';
			}
			/*不计算窗口位置*/
			setWinSize(0,$height(ym_headbox),get_cord());
		};
		var doNormal=function(){
			cur_state='normal';
			ym_ico[0].firstChild.innerHTML='0';
			ym_ico[1].firstChild.innerHTML='1';
			ym_ico[0].className='ymPrompt_min';
			ym_ico[1].className='ymPrompt_max';
			setWinSize(0,0,get_cord());
		};
		var max,min;
		addEvent('click',min=function(){
			cur_state!='normal'?doNormal():doMin();
		},ym_ico[0]);	//最小化
		addEvent('click',max=function(){
			cur_state!='normal'?doNormal():doMax();
		},ym_ico[1]);	//最大化
		addEvent('click',function(){
			ymPrompt.doHandler('close');
		},ym_ico[2]);	//关闭
		/*窗口最大化最小化核心部分结束*/

		/*getWinSize取得页面实际大小*/
		var getWinSize=function(){return [Math.max(rootEl.scrollWidth,rootEl.clientWidth),Math.max(rootEl.scrollHeight,rootEl.clientHeight)]};
		var winSize=getWinSize();	//保存当前页面的实际大小

		/*事件绑定部分*/
		var bindEl=ym_head.setCapture&&ym_head;	//绑定拖放事件的对象，只有Ie下bindEl有效
		/*窗体透明度控制*/
		var filterWin=function(v){
			!frameset&&curCfg.winAlpha!=1&&setStyle(ym_win,isIE?{filter: 'Alpha(opacity='+v*100+')'}:{opacity:v});	//鼠标按下时取消窗体的透明度
		};
		/*mousemove事件*/
		var mEvent=function(e) {
			var sLeft = dragVar.offX + e.clientX;
			var sTop = dragVar.offY + e.clientY;
			if (!curCfg.dragOut) {	//页面可见区域内拖动
				var pos=getScrollPos(),sl = pos[0],st = pos[1];
				sLeft = Math.min(Math.max(sLeft, sl), rootEl.clientWidth - ym_win.offsetWidth + sl);
				sTop = Math.min(Math.max(sTop, st), rootEl.clientHeight - ym_win.offsetHeight + st);
			}else if(curCfg.showMask && ''+winSize!=''+getWinSize())	//及时调整遮罩大小
				resizeMask(true);
			setStyle(ym_wins,{left:sLeft+'px',top:sTop+'px'});
		};
		/*mouseup事件*/
		var uEvent=function() {
			filterWin(1);
			detachEvent("mousemove",mEvent,bindEl);
			detachEvent("mouseup",uEvent,bindEl);
			saveWinInfo();//保存当前窗口的位置
			/*IE下窗口外部拖动*/
			bindEl&&(detachEvent("losecapture",uEvent,bindEl),bindEl.releaseCapture());
		};
		addEvent('mousedown',function(e) {
			if((e.srcElement||e.target).parentNode==ym_hTool) return false;	//点击操作按钮不进行启用拖动处理
			filterWin(curCfg.winAlpha);	//鼠标按下时窗体的透明度
			/*鼠标与弹出框的左上角的位移差*/
			ymPrompt.apply(dragVar, {
				offX: parseInt(ym_win.style.left)-e.clientX,
				offY: parseInt(ym_win.style.top)-e.clientY
			});
			addEvent("mousemove",mEvent,bindEl);
			addEvent("mouseup",uEvent,bindEl);
			/*IE下窗口外部拖动*/
			bindEl&&(addEvent("losecapture",uEvent,bindEl),bindEl.setCapture());
		},ym_head);

		/*页面滚动弹出窗口滚动*/
		var scrollEvent=function(){
			setStyle(ym_win, {
				left:dragVar._offX + rootEl.scrollLeft + 'px',
				top:dragVar._offY + rootEl.scrollTop + 'px'
			});
		};
		/*键盘监听*/
		var keydownEvent=function(e) {
			var keyCode=e.keyCode;
			if(keyCode==27) destroy();//esc键
			if(btnCache){
				var l = btnCache.length,nofocus;
				/*tab键/左右方向键切换焦点*/
				document.activeElement&&document.activeElement.id!=btnCache[btnIndex].id && (nofocus=true);
				if (keyCode == 9 || keyCode == 39) nofocus&&(btnIndex=-1),$(btnCache[++btnIndex == l ? (--btnIndex) : btnIndex].id).focus();
				if (keyCode == 37) nofocus&&(btnIndex=l),$(btnCache[--btnIndex < 0 ? (++btnIndex) : btnIndex].id).focus();
				if (keyCode == 13) return true;
			}
			/*禁止F1-F12/ tab 回车*/
			return keyEvent(e,(keyCode > 110 && keyCode < 123) || keyCode == 9 || keyCode == 13);
		};
		/*监听键盘事件*/
		var keyEvent=function(e,d){
			e=e||event;
			/*允许对表单项进行操作*/
			if(!d&&/input|select|textarea/i.test((e.srcElement||e.target).tagName)) return true;
			try{
				e.returnValue=false;
				e.keyCode = 0;
			} catch(ex) {
				e.preventDefault&&e.preventDefault();
			}
			return false;
		};
		maskLevel.oncontextmenu = ym_win.onselectstart = ym_win.oncontextmenu = keyEvent; //禁止右键和选择

		/*重新计算遮罩的大小*/
		var resizeMask=function(noDelay){
			setStyle(maskEl, 'display','none');	//先隐藏
			var size=getWinSize();
			var resize=function(){
				setStyle(maskEl, {
					width:size[0]+'px',
					height:size[1]+'px',
					display:''
				});
			};
			isIE?noDelay===true?resize():setTimeout(resize,0):resize();
			cur_state=='min'?doMin():cur_state=='max'?doMax():setWinSize();	//最大化最小化状态还原
		};
		/*蒙版的显示隐藏,state:true显示,false隐藏，默认为true*/
		var maskVisible = function(visible) {
			if (!curCfg.showMask) return;	//无遮罩
			(visible === false?detachEvent:addEvent)("resize",resizeMask,window);	//页面大小改变及时调整遮罩大小
			if (visible === false) return setStyle(maskEl, 'display','none');	//隐藏遮罩
			setStyle(maskLevel, {
				background:curCfg.maskAlphaColor,
				filter: 'Alpha(opacity='+curCfg.maskAlpha * 100+')',
				opacity:curCfg.maskAlpha
			});
			resizeMask(true);
		};
		/*计算指定位置的坐标*/
		var getPos=function(f){
			if(typeof f!='string')
				f=f.length==2?f[0]+'+{2},{3}+'+f[1]:posMap[c];	//自定义坐标处理
			var pos=[rootEl.clientWidth - ym_win.offsetWidth, rootEl.clientHeight - ym_win.offsetHeight].concat(getScrollPos());
			var arr=f.replace(/\{(\d)\}/g,function(s,s1){return pos[s1]}).split(',');
			return [eval(arr[0]),eval(arr[1])];
		};
		//9个常用位置常数
		var posMap = {
			c: '{0}/2+{2},{1}/2+{3}',
			l: '{2},{1}/2+{3}',
			r: '{0}+{2},{1}/2+{3}',
			t: '{0}/2+{2},{3}',
			b: '{0}/2,{1}+{3}',
			lt: '{2},{3}',
			lb: '{2},{1}+{3}',
			rb: '{0}+{2},{1}+{3}',
			rt: '{0}+{2},{3}'
		};
		/*设定窗口大小及定位*/
		var setWinSize = function(w, h ,pos) {
			if (ym_win.style.display == 'none') return;	//当前不可见则不处理
			/*默认使用配置的宽高*/
			h = parseInt(h) || curCfg.height;
			w = parseInt(w) || curCfg.width;

			setStyle(ym_wins, {
				width:w + 'px',
				height:h + 'px',
				left:0,
				top:0
			});
			if(!pos){	//根据当前配置计算坐标
				pos = getPos(posMap[curCfg.winPos]||curCfg.winPos); //支持自定义坐标
				if(!(pos instanceof Array))pos=getPos(posMap['c']);
			}
			setStyle(ym_wins, {
				top: pos[1] + 'px',
				left:pos[0] + 'px'
			});

			saveWinInfo();	//保存当前窗口位置信息
			setStyle(ym_body, 'height', h - $height(ym_headbox) - $height(ym_btn) - $height(ym_bottom) + 'px'); //设定内容区的高度
		};
		var _obj=[];	//IE中可见的obj元素
		var cacheWin=[];	//队列中的窗口
		var winVisible = function(visible) {
			var fn=visible===false?detachEvent:addEvent;
			fn('scroll', curCfg.fixPosition&&!useFixed?scrollEvent:saveWinInfo, window);
			setStyle(ym_wins,'position',curCfg.fixPosition&&useFixed?'fixed':'absolute');
			fn('keydown', keydownEvent);
			if (visible === false) {	//关闭
				setStyle(ym_shadow, 'display','none');
				/*关闭窗口执行的操作*/
				var closeFn=function(){
					setStyle(ym_win, 'display','none');
					setStyle(_obj, 'visibility','visible');
					_obj=[];
					//把当前弹出移除
					cacheWin.shift();
					//读取队列中未执行的弹出
					if(cacheWin.length)ymPrompt.show.apply(null,cacheWin[0].concat(true))
				};
				/*渐变方式关闭*/
				var alphaClose=function(){
					var alpha=1;
					var hideFn=function(){
						alpha=Math.max(alpha-curCfg.slideCfg.increment,0);
						setStyle(ym_win, {
							filter: 'Alpha(opacity='+alpha * 100+')',
							opacity:alpha
						});
						if(alpha==0){
							setStyle(ym_win,{filter:'',opacity:''});	//清除透明度属性设置
							maskVisible(false);
							closeFn();
							clearInterval(it);
						}
					};
					hideFn();
					var it=setInterval(hideFn,curCfg.slideCfg.interval);
				};
				curCfg.useSlide?alphaClose():closeFn();
				return ;
			}
			isIE&&setStyle(ym_win,'filter','');	//清除IE下的阴影滤镜，避免在fixed定位时弹出框中iframe内容不可见的bug
			for(var o=document.getElementsByTagName('object'),i=o.length-1;i>-1;i--) o[i].style.visibility!='hidden'&&_obj.push(o[i])&&(o[i].style.visibility='hidden');
			setStyle([ym_hText, ym_hTool], 'display',(curCfg.titleBar ? '': 'none'));
			ym_head.className = 'ym-tc' + (curCfg.titleBar ? '': ' ym-ttc');	//无标题栏
			ym_hText.innerHTML = curCfg.title; //标题
			for(var i=0,c=['min','max','close'];i<3;i++){
				ym_ico[i].style.display=curCfg[c[i]+'Btn']?'':'none';
				ym_ico[i].title=curCfg[c[i]+'Txt'];
			}
			ym_body.innerHTML = !curCfg.iframe ? ('<div class="'+curCfg.msgCls+'">' + curCfg.message + '</div>') : "<iframe width='100%' height='100%' border='0' frameborder='0' src='" + curCfg.message + "'></iframe>"; //内容
			(function(el,obj){for(var i in obj)try{el[i]=obj[i]}catch(e){}})(ym_body.firstChild,curCfg.iframe);//为iframe添加自定义属性
			ym_body.className = "ym-body " + curCfg.icoCls; //图标类型
			setStyle(ym_btn, 'display',((ym_btnContent.innerHTML = joinBtn(mkBtn(curCfg.btn))) ? '': 'none')); //没有按钮则隐藏
			!curCfg.useSlide&&curCfg.showShadow&&setStyle(ym_shadow, 'display','');
			setStyle(ym_win, 'display','')
			setWinSize();	//定位窗口
			isIE&&!isCompat&&filterWin(curCfg.useSlide?0:1);//使用filter解决IE非标准模式下有时下边会出现1px空白，使内容与下部不衔接
			/*渐变方式显示*/
			curCfg.useSlide&&(function(){
				var alpha=0;
				var showFn=function(){
					alpha=Math.min(alpha+curCfg.slideCfg.increment,1);
					setStyle(ym_win, {
						filter: 'Alpha(opacity='+alpha * 100+')',
						opacity:alpha
					});
					if(alpha==1){
						setStyle(ym_win,{filter:'',opacity:''});	//清除透明度属性
						clearInterval(it);
						curCfg.showShadow&&setStyle(ym_shadow, 'display','')
					}
				}
				showFn();
				var it=setInterval(showFn,curCfg.slideCfg.interval);
			})();
			btnCache && $(btnCache[btnIndex = 0].id).focus(); //第一个按钮获取焦点
		}; //初始化
		var init = function() {
			ym_wins=[ym_win].concat(curCfg.showShadow?ym_shadow:'');	//是否使用阴影
			maskVisible();
			winVisible();
		}; //销毁
		var destroy = function() {
			!curCfg.useSlide&&maskVisible(false);
			winVisible(false);
		};
		ymPrompt.apply(ymPrompt, {
			close: destroy,
			max: max,
			min: min,
			normal: doNormal,
			getPage: function() {
				return curCfg.iframe ? ym_body.firstChild: null
			},
			/*显示消息框,fargs:优先配置，会覆盖args中的配置*/
			/*show 强制显示*/
			show: function(args, fargs, show) {
				//如果有窗口未关闭则将本次传入的信息放到队列里
				if(!show&&cacheWin.push([args,fargs]) && cacheWin.length>1) return;
				/*支持两种参数传入方式:(1)JSON方式 (2)多个参数传入*/
				var a = [].slice.call(args, 0), o = {}, j=-1;
				if (typeof a[0] != 'object') {
					for(var i in dftCfg) if(a[++j]) o[i] = a[j];
				} else {
					o = a[0];
				}
				ymPrompt.apply(curCfg, ymPrompt.apply({},o, fargs), ymPrompt.setDefaultCfg()); //先还原默认配置
				/*修正curCfg中的无效值(null/undefined)改为默认值*/
				for(var i in curCfg) curCfg[i]=curCfg[i]!=null?curCfg[i]:ymPrompt.cfg[i];
				init();
			},
			doHandler: function(sign, autoClose, closeFirst) {
				if(autoClose == undefined ? curCfg.autoClose: autoClose) destroy();
				try{(curCfg.handler)(sign)}catch(e){alert(e.message)};
			},
			resizeWin: setWinSize,
			/*设定默认配置*/
			setDefaultCfg: function(cfg) {
				return ymPrompt.cfg = ymPrompt.apply({}, cfg, ymPrompt.apply({}, ymPrompt.cfg, dftCfg));
			},
			getButtons:function(){
				var btns=btnCache||[],btn,rBtn=[];
				while(btn=btns.shift())rBtn.push($(btn.id));
				return rBtn;
			}
		});
		ymPrompt.setDefaultCfg(); //初始化默认配置
		/*执行用户初始化时的调用*/
		var t;
		for (var i in _initFn) while(t=_initFn[i].shift()) ymPrompt[i].apply(null, t);

		/*取消事件绑定*/
		addEvent('unload',function() {
			while(ymPrompt.eventList.length) detachEvent.apply(null, ymPrompt.eventList.shift());
		},window);
	})();
})(); //各消息框的相同操作
ymPrompt.apply(ymPrompt, {
	alert: function() {
		ymPrompt.show(arguments, {
			icoCls: 'ymPrompt_alert',
			btn: ['OK']
		});
	},
	succeedInfo: function() {
		ymPrompt.show(arguments, {
			icoCls: 'ymPrompt_succeed',
			btn: ['OK']
		});
	},
	errorInfo: function() {
		ymPrompt.show(arguments, {
			icoCls: 'ymPrompt_error',
			btn: ['OK']
		});
	},
	confirmInfo: function() {
		ymPrompt.show(arguments, {
			icoCls: 'ymPrompt_confirm',
			btn: ['CANCEL','OK']
		});
	},
	win: function() {
		ymPrompt.show(arguments);
	}
});