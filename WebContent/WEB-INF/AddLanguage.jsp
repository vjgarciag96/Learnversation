<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add Language</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<div class="formWrapper">
		<h1>Añade un idioma nuevo.</h1>
		<form
			action="${pageContext.request.contextPath}/exchange/AddLanguageServlet"
			method="post">
			<label>Idioma que puedes aportar: <select name="languageIdl">
					<c:forEach items="${languageList}" var="language">
						<option value="${language.idl}">${language.langname}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel hablado: <select name="speakingLevel">
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel escrito: <select name="writingLevel">
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel comprensión auditiva: <select name="listeningLevel">
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label><label>Nivel comprensión lectora: <select name="readingLevel">
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select></label> <a
				href="${pageContext.request.contextPath}/exchange/LanguageReferencesServlet">
				¿Qué nivel tengo? </a> <input type="submit" value="Añadir idioma">
		</form>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>