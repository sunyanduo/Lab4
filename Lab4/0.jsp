<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'searchAuthorResult.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    This is my SearchResult page. <br><br>
    This author is<br> <br>
    <s:property value="SearchAuthorInfoResult"/>
    <s:iterator value="searchAuthorInfo" id="a">
    	<hr/>
    	ISBN : <s:property value="#a.isbn"/><br>
    	TITLE : <s:property value="#a.title"/><br>
    	PUBLISHER : <s:property value="#a.publisher"/><br>
    	PUBLISHEDATE : <s:property value="#a.publishdate"/><br>
    	PRICE : <s:property value="#a.price"/><br>
    	<hr/>
    </s:iterator>
    	
    Click <a href="index.jsp">here</a> to go back Homepage;
  </body>
</html>
