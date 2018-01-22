<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cambiar contraseña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MainStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/InputsWrapper.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
    <div class="formWrapper">
        <h1>Cambia tu contraseña</h1>
        <form action="${pageContext.request.contextPath}/exchange/ChangePasswordServlet" method="post">
        	<p class="errorMessage">${errorMessage}</p>
            <label>Contraseña actual:
                <input type="password" name="currentPassword" required>
            </label>
            <label>Nueva contraseña:
                <input type="password" name="newPassword" required>
            </label>
            <label>Repite la nueva contraseña:
                <input type="password" name="repeatedNewPassword" required>
            </label>
            <input type="submit" value="Cambiar contraseña">
        </form>
    </div>
    <c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>