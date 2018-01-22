<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Registro de usuarios</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/NotLoggedTopnav.jsp"></c:import>
	<div class="formWrapper">
		<h1>Registrate en la web</h1>
		<form action="${pageContext.request.contextPath}/RegisterServlet"
			method="post">
			<p class="errorMessage">${errorMessage}</p>
			<label>Nombre de usuario: <input type="text" name="username"
				placeholder="Nombre" value="${username}" required></label> <label>Fecha
				de nacimiento: <input type="date" name="birthDate"
				value="${birthDate}" required>
			</label> <label>País de residencia: <input type="text" name="country"
				placeholder="País" value="${country}" required></label> <label>Género:</label>
			<label><input type="radio" name="gender" value="Hombre"
				<c:if test="${gender == 'Hombre'}">checked</c:if>>Hombre</label> <label><input
				type="radio" name="gender" value="Mujer"
				<c:if test="${gender == 'Mujer'}">checked</c:if>>Mujer</label> <label>Tipo
				de intercambio</label> <label><input type="checkbox"
				name="exchangeTypes" value="Cara a cara"
				<c:if test="${fn:contains(exchangeTypes, 'Cara a cara')}">checked</c:if>>Conversación
				cara a cara</label> <label><input type="checkbox"
				name="exchangeTypes" value="A través de internet"
				<c:if test="${fn:contains(exchangeTypes, 'A través de internet')}">checked</c:if>>A
				través de internet</label> <label>Correo electrónico: <input
				type="email" name="email" placeholder="E-mail" value="${email}"
				required></label> <label>Contraseña: <input type="password"
				name="password" placeholder="Contraseña" required></label> <label>Repetir
				contraseña: <input type="password" name="repeatedPassword"
				placeholder="Repetir contraseña" required>
			</label> <input type="submit" value="Registrarse">
		</form>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>>
</body>
</html>