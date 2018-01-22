<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Java Exception</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
</head>
<body>
	<h1 class="errorTitle">Algo ha ido mal con Java, disculpa las molestias</h1>
	<a class="errorLink"
		href="${pageContext.request.contextPath}/LoginServlet">Volver a la
		página principal</a>
	<img class="errorImage" alt="Java Exception"
		src="${pageContext.request.contextPath}/images/javaException.jpg">
</body>