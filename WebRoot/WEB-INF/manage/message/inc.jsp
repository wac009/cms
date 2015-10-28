<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<a href='message_add.jspa' style="padding:0 5px;">发送站内信</a>
<a href='message_receivebox.jspa' style="padding:0 5px;">收件箱管理(<s:property value="%{notReadCount}"/>/<s:property value="%{receiveCount}"/>)</a>
<a href='message_sendbox.jspa' style="padding:0 5px;">发件箱管理(<s:property value="%{sendCount}"/>)</a>
<a href='message_draftbox.jspa' style="padding:0 5px;">草稿箱管理(<s:property value="%{draftCount}"/>)</a>
<a href='message_trashbox.jspa' style="padding:0 5px;">垃圾箱管理(<s:property value="%{trashCount}"/>)</a>
<a href='message_empty.jspa' style="padding:0 5px;" onclick="return confirm('确定清空所有站内信吗?');">清空站内信</a>