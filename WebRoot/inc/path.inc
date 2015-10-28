<%@page contentType="text/html; charset=UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  String baseMngPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/manage/";
  String filePath = request.getRequestURI();
  
  String r_c ="/resource/common";
  String r_m="/resource/manage";
  String r_f ="/resource/w";
  String r_t ="/resource/template";
%>