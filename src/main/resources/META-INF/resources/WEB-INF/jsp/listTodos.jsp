<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container my-2">
	<h1>Welcome <pre>${username}</pre></h1>
	<hr>
	<table class="table">
		<thead>
			<tr>
				<th>Username</th>
				<th>Description</th>
				<th>Target Date</th>
				<th>Completed</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${todos}" var="todo">
			<tr>
				<td>${todo.username}</td>
				<td>${todo.description}</td>
				<td>${todo.targetDate}</td>
				<td>${todo.done}</td>
				<td><a href="delete-todo?id=${todo.id}" class="btn btn-dark">Delete</a></td>
				<td><a href="update-todo?id=${todo.id}" class="btn btn-warning">Update</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<a href="add-todo" class="btn btn-success mx-auto w-25">Add Task</a>
	</div>
	
<%@ include file="common/footer.jspf" %>
	