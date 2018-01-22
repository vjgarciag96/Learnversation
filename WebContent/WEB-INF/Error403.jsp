<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Error 403</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
</head>
<body>
	<h1 class="errorTitle">Error 403: Prohibido</h1>
	<a class="errorLink"
		href="${pageContext.request.contextPath}/LoginServlet">Volver a la
		página principal</a>
	<img class="errorImage" alt="Error 403"
		src="${pageContext.request.contextPath}/images/error403.gif">
</body>