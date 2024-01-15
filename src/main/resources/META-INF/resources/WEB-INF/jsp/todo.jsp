<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container">
	<h1 class="">Enter Todo Details</h1>
	<form:form method="post" cssClass="container form d-flex flex-column gap-4 my-2" modelAttribute="todo">

		<fieldset>
			<form:label path="description">Description </form:label>
			<form:input type="text" cssClass="w-25" path="description"></form:input>
			<form:errors path="description" cssClass="text-danger"/>
		</fieldset>
		
		<fieldset>
			<form:label path="targetDate">Target Date </form:label>
			<form:input type="text" cssClass="w-25" path="targetDate"></form:input>
			<form:errors path="targetDate" cssClass="text-danger"/>
		</fieldset>
		
		<form:input path="id" type="hidden"/>

		<form:input path="done" type="hidden"/>

		<input type="submit" class="btn w-50 btn-success">
	</form:form>
	</div>
	
	
<%@ include file="common/footer.jspf" %>
<script type="text/javascript">
	$('#targetDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>