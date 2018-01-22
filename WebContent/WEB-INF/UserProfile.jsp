<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:choose>
		<c:when test="${profileType == 'editing'}">${user.username}</c:when>
		<c:otherwise>
    	${userVisited.username}
    	</c:otherwise>
	</c:choose></title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/DetailViewStyle.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<div class="userBox">
		<div class="boxTitle">
			<h1>
				<c:choose>
					<c:when test="${profileType == 'editing'}">
			${user.username}
			<a
							href="${pageContext.request.contextPath}/exchange/EditProfileServlet">
							<img class="icon" alt="Eliminar perfil"
							src="${pageContext.request.contextPath}/icons/edit.png">Editar
							perfil
						</a>
						<a class="deleteProfile"
							href="${pageContext.request.contextPath}/exchange/DeleteProfileServlet">
							Dar de baja perfil<img class="icon" alt="Eliminar perfil"
							src="${pageContext.request.contextPath}/icons/deleteProfile.png">
						</a>
					</c:when>
					<c:otherwise>${userVisited.username}</c:otherwise>
				</c:choose>
			</h1>
		</div>
		<div class="profileContent">
			<div class="firstBlock">
				<div class="profileInfo" id="profileImage">
					<c:forEach items="${profileImages}" var="image">
						<c:choose>
							<c:when test="${profileType == 'editing'}">
								<c:if test="${user.idi == image.idi }">
									<a
										href="${pageContext.request.contextPath}/exchange/ChangeProfileImageServlet?confirmation=0"><img
										src="${pageContext.request.contextPath}/profileImages/${image.imageName}"
										alt="Foto de perfil de ${user.username}"></a>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${userVisited.idi == image.idi }">
									<img
										src="${pageContext.request.contextPath}/profileImages/${image.imageName}"
										alt="Foto de perfil de ${userVisited.username}">
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${profileType == 'editing'}">
						<div class="changeProfileImageInfo">
							<p class="changeImgProfileInfo">Pincha en la imagen para
								cambiar la foto de perfil</p>
						</div>
					</c:if>
				</div>

				<div class="profileInfo" id="textInfo">
					<h2>CORREO ELECTRÓNICO</h2>
					<p>
						<c:choose>
							<c:when test="${profileType == 'editing'}">${user.email}</c:when>
							<c:otherwise>${userVisited.email}</c:otherwise>
						</c:choose>
					</p>
					<h2>GÉNERO:</h2>
					<p>
						<c:choose>
							<c:when test="${profileType == 'editing'}">${user.gender}</c:when>
							<c:otherwise>${userVisited.gender}</c:otherwise>
						</c:choose>
					</p>
					<h2>FECHA DE NACIMIENTO:</h2>
					<p>
						<c:choose>
							<c:when test="${profileType == 'editing'}">${user.birthDate}</c:when>
							<c:otherwise>${userVisited.birthDate}</c:otherwise>
						</c:choose>
					</p>
					<h2>PAÍS:</h2>
					<p>
						<c:choose>
							<c:when test="${profileType == 'editing'}">${user.country}</c:when>
							<c:otherwise>${userVisited.country}</c:otherwise>
						</c:choose>
					</p>
					<h2>TIPO DE INTERCAMBIO:</h2>
					<p>
						<c:choose>
							<c:when test="${profileType == 'editing'}">${user.exchangeTypes}</c:when>
							<c:otherwise>${userVisited.exchangeTypes}</c:otherwise>
						</c:choose>
					</p>
					<h2>
						IDIOMA/S
						<c:if test="${profileType == 'editing'}">
							<a
								href="${pageContext.request.contextPath}/exchange/AddLanguageServlet"><img
								class="icon"
								src="${pageContext.request.contextPath}/icons/ic_add.png"
								alt="añadir idioma" /></a>
						</c:if>
					</h2>
					<c:forEach items="${userLanguageList}" var="userLanguage">
						<p class="userLanguageDescription">
							<c:forEach items="${languageList}" var="language">
								<c:choose>
									<c:when test="${userLanguage.idl==language.idl}">${language.langname}.</c:when>
								</c:choose>
							</c:forEach>
							Nivel hablado:
							<c:forEach items="${levelsList}" var="level">
								<c:choose>
									<c:when test="${userLanguage.speakingLevel==level.idlv}">${level.levelName}.</c:when>
								</c:choose>
							</c:forEach>
							Nivel escrito:
							<c:forEach items="${levelsList}" var="level">
								<c:choose>
									<c:when test="${userLanguage.writingLevel==level.idlv}">${level.levelName}.</c:when>
								</c:choose>
							</c:forEach>
							Nivel comprensión auditiva:
							<c:forEach items="${levelsList}" var="level">
								<c:choose>
									<c:when test="${userLanguage.listeningLevel==level.idlv}">${level.levelName}.</c:when>
								</c:choose>
							</c:forEach>
							Nivel comprensión lectora:
							<c:forEach items="${levelsList}" var="level">
								<c:choose>
									<c:when test="${userLanguage.readingLevel==level.idlv}">${level.levelName}.</c:when>
								</c:choose>
							</c:forEach>
						</p>
						<c:if test="${profileType == 'editing'}">
							<p class="userLanguageDescription">
								<a
									href="<c:url value='/exchange/EditLanguageServlet?languageId=${userLanguage.idl}&speakingLevel=${userLanguage.speakingLevel}&writingLevel=${userLanguage.writingLevel}&listeningLevel=${userLanguage.listeningLevel}&readingLevel=${userLanguage.readingLevel}'/>"><img
									class="icon"
									src="${pageContext.request.contextPath}/icons/edit.png"
									alt="editar ${userLanguage.idl}" /></a> <a
									href="<c:url value='/exchange/DeleteLanguageServlet?languageId=${userLanguage.idl}&speakingLevel=${userLanguage.speakingLevel}&writingLevel=${userLanguage.writingLevel}&listeningLevel=${userLanguage.listeningLevel}&readingLevel=${userLanguage.readingLevel}'/>"><img
									class="icon"
									src="${pageContext.request.contextPath}/icons/delete.png"
									alt="edit ${order.id }" /></a>
							</p>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div class="commentsWrapper">
		<c:choose>
			<c:when test="${not empty messageState}">
				<p>${messageState}</p>
			</c:when>
			<c:when test="${not empty errorMessage}">
				<p class="errorMessage">${errorMessage}</p>
			</c:when>
		</c:choose>
		<c:if test="${profileType != 'editing'}">
			<div class="userComments">
				<h1 class="userComments">Enviar comentario a
					${userVisited.username}</h1>
				<form
					action="${pageContext.request.contextPath}/exchange/SendCommentServlet"
					method="post">
					<input type="hidden" name="userVisitedIdu"
						value="${userVisited.idu}">
					<textarea placeholder="Escribe tu mensaje aqui..." name="comment"></textarea>
					<input type="submit" value="Enviar">
				</form>
			</div>
		</c:if>
		<h2>COMENTARIOS:</h2>
		<c:if test="${empty commentsAndSenders}">
			<p>Este usuario todavia no tiene ningún comentario.</p>
		</c:if>
		<c:forEach items="${commentsAndSenders}" var="commentAndSender">
			<div class="userComments">
				<h1 class="userComments">
					Comentario de ${commentAndSender.first}
					<c:if
						test="${commentAndSender.second.sender == user.idu or commentAndSender.second.sender == userVisited.idu}">
						<a
							href="<c:url value='/exchange/EditCommentServlet?commentIdc=${commentAndSender.second.idc}'/>"><img
							class="icon"
							src="${pageContext.request.contextPath}/icons/edit.png"
							alt="editar comentario" /></a>
						<a
							href="<c:url value='/exchange/DeleteCommentServlet?commentIdc=${commentAndSender.second.idc}'/>"><img
							class="icon"
							src="${pageContext.request.contextPath}/icons/delete.png"
							alt="edit ${order.id }" /></a>
					</c:if>
				</h1>
				<p class="userComments">${commentAndSender.second.text}</p>
				<p class="timeStamp">${commentAndSender.second.timeStamp}</p>
			</div>
		</c:forEach>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>