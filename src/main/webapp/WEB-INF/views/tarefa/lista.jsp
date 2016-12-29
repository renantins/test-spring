<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="novaTarefa">Criar nova tarefa</a>
	<br>
	<table>
		<tr>
			<th>Id</th>
			<th>Descricao</th>
			<th>Finalizado?</th>
			<th>Data Finalizacao</th>
		</tr>
		<c:forEach var="tarefa" items="${tarefas}">
		
		</c:forEach>
	</table>
</body>
</html>