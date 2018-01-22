<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Error 404</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
</head>
<body>
	<h1 class="errorTitle">Error 404: No se ha podido encontrar la
		página en el servidor</h1>
	<a class="errorLink"
		href="${pageContext.request.contextPath}/LoginServlet">Volver a la
		página principal</a>
	<img class="errorImage" alt="Error 404"
		src="${pageContext.request.contextPath}/images/error404.png">
</body>