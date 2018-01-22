<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Marco Común Europeo de Referencia para las Lenguas</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MainStyle.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/LanguageReferencesStyle.css">
</head>
<body>
	<c:import url="/WEB-INF/LoggedTopnav.jsp"></c:import>
	<table>
		<tbody>
			<tr>
				<th>Nivel</th>
				<th>Subnivel</th>
				<th>Descripción</th>
			</tr>
			<tr>
				<td rowspan="2"><b>A</b><br> (Usuario básico)</td>
				<td><b>A1</b><br> (Acceso)</td>
				<td>Es capaz de comprender y utilizar expresiones cotidianas de
					uso muy frecuente así como frases sencillas destinadas a satisfacer
					necesidades de tipo inmediato. Puede presentarse a sí mismo y a
					otros, pedir y dar información personal básica sobre su domicilio,
					sus pertenencias y las personas que conoce. Puede relacionarse de
					forma elemental siempre que su interlocutor hable despacio y con
					claridad y esté dispuesto a cooperar.</td>
			</tr>
			<tr>
				<td><b>A2</b><br> (Plataforma)</td>
				<td>Es capaz de comprender frases y expresiones de uso
					frecuente relacionadas con áreas de experiencia que le son
					especialmente relevantes (información básica sobre sí mismo y su
					familia, compras, lugares de interés, ocupaciones, etc). Sabe
					comunicarse a la hora de llevar a cabo tareas simples y cotidianas
					que no requieran más que intercambios sencillos y directos de
					información sobre cuestiones que le son conocidas o habituales.
					Sabe describir en términos sencillos aspectos de su pasado y su
					entorno así como cuestiones relacionadas con sus necesidades
					inmediatas.</td>
			</tr>
			<tr>
				<td rowspan="2"><b>B</b><br> (Usuario independiente)</td>
				<td><b>B1</b><br> (Intermedio)</td>
				<td>Es capaz de comprender los puntos principales de textos
					claros y en lengua estándar si tratan sobre cuestiones que le son
					conocidas, ya sea en situaciones de trabajo, de estudio o de ocio.
					Sabe desenvolverse en la mayor parte de las situaciones que pueden
					surgir durante un viaje por zonas donde se utiliza la lengua. Es
					capaz de producir textos sencillos y coherentes sobre temas que le
					son familiares o en los que tiene un interés personal. Puede
					describir experiencias, acontecimientos, deseos y aspiraciones, así
					como justificar brevemente sus opiniones o explicar sus planes.</td>
			</tr>
			<tr>
				<td><b>B2</b><br> (Intermedio alto)</td>
				<td>Es capaz de entender las ideas principales de textos
					complejos que traten de temas tanto concretos como abstractos,
					incluso si son de carácter técnico siempre que estén dentro de su
					campo de especialización. Puede relacionarse con hablantes nativos
					con un grado suficiente de fluidez y naturalidad de modo que la
					comunicación se realice sin esfuerzo por parte de ninguno de los
					interlocutores. Puede producir textos claros y detallados sobre
					temas diversos así como defender un punto de vista sobre temas
					generales indicando los pros y los contras de las distintas
					opciones.</td>
			</tr>
			<tr>
				<td rowspan="2"><b>C</b><br> (Usuario competente)</td>
				<td><b>C1</b><br> (Dominio operativo eficaz)</td>
				<td>Es capaz de comprender una amplia variedad de textos
					extensos y con cierto nivel de exigencia, así como reconocer en
					ellos sentidos implícitos. Sabe expresarse de forma fluida y
					espontánea sin muestras muy evidentes de esfuerzo para encontrar la
					expresión adecuada. Puede hacer un uso flexible y efectivo del
					idioma para fines sociales, académicos y profesionales. Puede
					producir textos claros, bien estructurados y detallados sobre temas
					de cierta complejidad, mostrando un uso correcto de los mecanismos
					de organización, articulación y cohesión del texto.</td>
			</tr>
			<tr>
				<td><b>C2</b><br> (Maestría)</td>
				<td>Es capaz de comprender con facilidad prácticamente todo lo
					que oye o lee. Sabe reconstruir la información y los argumentos
					procedentes de diversas fuentes, ya sean en lengua hablada o
					escrita, y presentarlos de manera coherente y resumida. Puede
					expresarse espontáneamente, con gran fluidez y con un grado de
					precisión que le permite diferenciar pequeños matices de
					significado incluso en situaciones de mayor complejidad.</td>
			</tr>
		</tbody>
	</table>
	<c:import url="/WEB-INF/Footer.jsp"></c:import>
</body>
</html>