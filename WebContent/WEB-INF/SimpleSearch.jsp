<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Búsqueda de usuarios</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<div class="formWrapper">
		<h1>Busca compañer@s de conversación</h1>
		<form method="post"
			action="${pageContext.request.contextPath}/exchange/SimpleSearchServlet">
			<label>Domina <select name="languageIdl">
					<option value="">Selecciona idioma</option>
					<c:forEach items="${languageList}" var="language">
					</c:forEach>
			</select>
			</label> <label>Nivel hablado: <select name="speakingLevel">
					<option value="">Selecciona nivel hablado</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel escrito: <select name="writingLevel">
					<option value="">Selecciona nivel escrito</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <label>Nivel de comprensión auditiva: <select name="listeningLevel">
					<option value="">Selecciona nivel comprensión auditiva</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label><label>Nivel de comprensión lectora: <select name="readingLevel">
					<option value="">Selecciona nivel comprensión lectora</option>
					<c:forEach items="${languageLevels}" var="level">
						<option value="${level.idlv}">${level.levelName}</option>
					</c:forEach>
			</select>
			</label> <a
				href="${pageContext.request.contextPath}/exchange/LanguageReferencesServlet">¿Que
				nivel necesito?</a> <label>País <input type="text"
				name="country" placeholder="País">
			</label> <label id="type">Tipo de intercambio</label> <label>Conversación
				cara a cara<input type="checkbox" name="exchangeTypes"
				value="Cara a cara">
			</label> <label>Conversación a través de internet<input
				type="checkbox" name="exchangeTypes" value="A través de internet"></label>
			<label>Nombre de usuario: <input type="text"
				name="username" placeholder="Nombre de usuario">
			</label>
			<input type="submit" value="Buscar">
		</form>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>