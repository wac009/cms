<%@page contentType="text/html; charset=UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  String baseMngPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/manage/";
  String filePath = request.getRequestURI();
  
  String r_c ="/res/c";
  String r_m="/res/m";
  String r_f ="/res/w";
  String r_t ="/res/t";
%>