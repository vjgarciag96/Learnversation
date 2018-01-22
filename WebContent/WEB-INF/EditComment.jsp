<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Editar Comentario</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<div class="formWrapper">
			<h1 class="userComments">Editar comentario enviado a ${commentAndReceiver.first}</h1>
			<form action="${pageContext.request.contextPath}/exchange/EditCommentServlet" method="post">
				<c:if test="${not empty errorMessage}"><p class="errorMessage">${errorMessage}</p></c:if>
				<input type="hidden" name="userVisitedIdu" value="${commentAndReceiver.second.receiver}">
				<input type="hidden" name="commentIdc" value="${commentAndReceiver.second.idc}">			
				<textarea placeholder="Escribe tu mensaje aqui..." name="comment">${commentAndReceiver.second.text}</textarea>
				<input type="submit" value="Guardar Edición">
			</form>
		</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>