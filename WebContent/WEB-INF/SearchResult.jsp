<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Resultados de la búsqueda</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/SearchResultStyle.css">
</head>
<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
<div class="pageBodyWrapper">
	<div class="searchOptionsWrapper">
		<form
			action="${pageContext.request.contextPath}/exchange/SimpleSearchServlet"
			method="post">
			<label>Domina: <select name="languageIdl">
					<option>---</option>
					<c:forEach items="${languageList}" var="languageItem">
						<option value="${languageItem.idl}"
							<c:if test="${languageItem.idl == language}">selected="selected"</c:if>>${languageItem.langname}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel hablado: <select name="speakingLevel">
					<option>---</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}"
							<c:if test="${level.idlv == speakingLevel}">selected="selected"</c:if>>${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel escrito: <select name="writingLevel">
					<option>---</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}"
							<c:if test="${level.idlv == writingLevel}">selected="selected"</c:if>>${level.levelName}</option>
					</c:forEach>
			</select>

			</label> <label>Nivel comprensión auditiva: <select
				name="listeningLevel">
					<option>---</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}"
							<c:if test="${level.idlv == listeningLevel}">selected="selected"</c:if>>${level.levelName}</option>
					</c:forEach>
			</select></label> <label>Nivel comprensión lectora: <select
				name="readingLevel">
					<option>---</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}"
							<c:if test="${level.idlv == readingLevel}">selected="selected"</c:if>>${level.levelName}</option>
					</c:forEach>
			</select> <a
				href="${pageContext.request.contextPath}/exchange/LanguageReferencesServlet">¿Que
					nivel necesito?</a>
			</label> <label>País <input type="text" name="country"
				value="${country}" placeholder="País">
			</label> <label class="singleLabel">Tipo de intercambio</label> <label>Conversación
				cara a cara<input type="checkbox" name="exchangeTypes"
				value="Cara a cara"
				<c:if test="${fn:contains(exchangeTypes, 'Cara a cara')}">checked</c:if>>
			</label> <label>Conversación a través de internet<input
				type="checkbox" name="exchangeTypes" value="A través de internet"
				<c:if test="${fn:contains(exchangeTypes, 'A través de internet')}">checked</c:if>></label>
			<label>Nombre de usuario: <input type="text"
				name="username" placeholder="Nombre de usuario" value="${username}"></label>
				<input type="submit" value="Buscar">
		</form>
	</div>
	<div class="searchResultsWrapper">
		<p>${zeroUsersFound}</p>
		<c:forEach var="userSearched" items="${userList}">
			<div class="userBox">
				<div class="boxTitle">
					<h1>${userSearched.username}</h1>
				</div>
				<div class="profileContent">
					<div class="firstBlock">
						<div class="profileImage">
							<c:forEach items="${profileImages}" var="image">
							<c:if test="${userSearched.idi == image.idi}">
							<img
								src="${pageContext.request.contextPath}/profileImages/${image.imageName}"
								alt="Foto de perfil de ${userSearched.username}">
							</c:if>
							</c:forEach>
						</div>
						<div class="textInfo">
							<h2>GÉNERO:</h2>
							<p>${userSearched.gender}</p>
							<h2>PAÍS:</h2>
							<p>${userSearched.country}</p>
							<h2>IDIOMA/S QUE DOMINA:</h2>
							<c:forEach items="${usersLanguages}" var="userLanguage">
								<c:choose>
									<c:when test="${userSearched.idu == userLanguage.idu}">
										<p>
											<c:forEach items="${languageList}" var="language">
												<c:choose>
													<c:when test="${userLanguage.idl == language.idl}">${language.langname}.</c:when>
												</c:choose>
											</c:forEach>
											Nivel hablado:
											<c:forEach items="${languageLevels}" var="level">
												<c:choose>
													<c:when test="${userLanguage.speakingLevel==level.idlv}">${level.levelName}.</c:when>
												</c:choose>
											</c:forEach>
											Nivel escrito:
											<c:forEach items="${languageLevels}" var="level">
												<c:choose>
													<c:when test="${userLanguage.writingLevel==level.idlv}">${level.levelName}.</c:when>
												</c:choose>
											</c:forEach>
											Nivel comprensión auditiva:
											<c:forEach items="${languageLevels}" var="level">
												<c:choose>
													<c:when test="${userLanguage.listeningLevel==level.idlv}">${level.levelName}.</c:when>
												</c:choose>
											</c:forEach>
											Nivel comprensión lectora:
											<c:forEach items="${languageLevels}" var="level">
												<c:choose>
													<c:when test="${userLanguage.readingLevel==level.idlv}">${level.levelName}.</c:when>
												</c:choose>
											</c:forEach>
									</c:when>
								</c:choose>
							</c:forEach>
							<h2>TIPO DE INTERCAMBIO:</h2>
							<p>${userSearched.exchangeTypes}</p>
						</div>
					</div>
					<div class="boxFooter">
						<a class="seeProfile"
							href="${pageContext.request.contextPath}/exchange/UserProfileServlet?userVisitedIdu=${userSearched.idu}">
							<img class="icon" alt="Ver perfil de usuario"
							src="${pageContext.request.contextPath}/icons/seeProfile.png">Ver
							perfil
						</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>