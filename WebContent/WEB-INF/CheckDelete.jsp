<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Eliminar Idioma</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<div class="formWrapper">
		<!-- DeleteProfile -->
		<c:choose>
			<c:when test="${checkType == 'profile'}">
				<h1>${user.username},¿estás segurodeque quieres eliminar tu
					perfil?</h1>
			</c:when>
			<c:when test="${checkType == 'language'}">
				<h1>Eliminar idioma.</h1>
			</c:when>
			<c:when test="${checkType == 'comment'}">
				<h1>Eliminar comentario</h1>
			</c:when>
			<c:when test="${checkType == 'profileImage'}">
				<h1>Cambiar foto de perfil</h1>
			</c:when>
		</c:choose>
		<form action="?" method="post">
			<!-- DeleteLanguage -->
			<c:choose>
				<c:when test="${checkType == 'profile'}">
					<p class="errorMessage">${errorMessage}</p>
					<p>Para dar de baja tu perfil, introduce tu contraseña y
						confirma la eliminación.</p>
					<label>*Contraseña: <input type="password" name="password"
						placeholder="Contraseña"></label>
				</c:when>
				<c:when test="${checkType == 'language'}">
					<p>¿Estás seguro de que deseas eliminar el idioma siguiente?</p>
					<ul>
						<li>Idioma: ${language.langname}</li>
						<li>Nivel hablado: ${speakingLevel.levelName}</li>
						<li>Nivel escrito: ${writingLevel.levelName}</li>
						<li>Nivel comprensión auditiva: ${listeningLevel.levelName}</li>
						<li>Nivel comprensión lectora: ${readingLevel.levelName}</li>
					</ul>
				</c:when>
				<c:when test="${checkType == 'comment'}">
					<input type="hidden" name="commentIdc"
						value="${commentAndReceiver.second.idc}">
					<input type="hidden" name="receiverIdu"
						value="${commentAndReceiver.second.receiver}">
					<p>¿Estás seguro que quieres eliminar este comentario?</p>
					<p>Destinatario: ${commentAndReceiver.first}</p>
					<p>Mensaje:${commentAndReceiver.second.text}</p>
				</c:when>
				<c:when test="${checkType == 'profileImage'}">
					<input type="hidden" name="idi" value="${profileImage.idi}">
					<p>Pulsa confirmar si quieres utilizar esta imagen como foto de
						perfil.</p>
					<img class=profileImage
						src="${pageContext.request.contextPath}/profileImages/${profileImage.imageName}"
						alt="Foto de perfil">
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${checkType == 'profileImage'}">
					<input type="submit" value="Confirmar">
				</c:when>
				<c:otherwise>
					<input type="submit" value="Confirmar Eliminacion">
				</c:otherwise>
			</c:choose>
		</form>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>