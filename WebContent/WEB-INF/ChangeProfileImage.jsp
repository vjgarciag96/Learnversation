<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Editar Comentario</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ChooseImage.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<h1>Elige tu foto de perfil</h1>
	<div class="imageWrapper">
		<h2 class="title">Pincha sobre la imagen que quieres utilizar en tu perfil</h2>
		<c:forEach var="image" items="${profileImages}">
			<a href="<c:url value='/exchange/ChangeProfileImageServlet?confirmation=1&idi=${image.idi}'/>"><img class=profileImage
				src="${pageContext.request.contextPath}/profileImages/${image.imageName}"
				alt="Imagen de perfil ${image.idi}"> </a>
		</c:forEach>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>