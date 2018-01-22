<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/LoginStyle.css">
<title>Learnversation</title>
</head>
<body>
	<c:import url="/WEB-INF/NotLoggedTopnav.jsp"></c:import>
	<div class="pageBodyWrapper">
		<article>
			<h1>¿A quién está dirigida esta página?</h1>
			<p>Todas las personas que quieran aprender un idioma podrán
				hacerlo utilizando esta página, independientemente del nivel que
				tengan, y de forma gratuita, gracias a la colaboración de otras
				personas. Lo único que tienes que aportar son tus conocimientos en
				los idiomas que domines para que otras personas también puedan
				aprenderlos.</p>
		</article>
		<div class="login">
			<form method="post"
				action="${pageContext.request.contextPath}/LoginServlet">
				<fieldset>
					<label id="loginTitle">Accede a tu cuenta</label> <label>Nombre
						de usuario o email:</label> <input type="text" placeholder="Introduce usuario o email"
						name="username" required> <label>Contraseña:</label> <input
						type="password" placeholder="Contraseña" name="password" required>
					<input type="submit" value="Entrar">
					<p class="errorMessage">${errorMessage}</p>
				</fieldset>
			</form>
			<a href="${pageContext.request.contextPath}/RegisterServlet">Si
				no estás registrado aún, puedes hacerlo aquí</a>
		</div>
	</div>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>