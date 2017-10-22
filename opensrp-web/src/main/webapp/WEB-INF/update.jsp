<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
    
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Data updating</title>
    
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">

  </head>
  <body>

    <div class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<img alt="OPENSRP" src="/resources/opensrp-logo.png">
		</div>
		<div class="col-md-8">
			<h3 class="text-center text-success">
				Data Updating -- OpenSRP
			</h3>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	<div class="row">
		
		<div class="col-md-9">
		<br/>
	<table style="width:100%" class="table" >
		<form:form  class="form-horizontal" 
		method="POST" action="/update">   
	 	<tr>
	 	<th>Mouza Para </th>
	    <td>
	    
	    <form:input type="text" path="name"  />
	   
	     </td>
	 	</tr>
	 	<tr>
	 	<th>Provider </th>
	    <td>
	    
	    <form:input type="text" path="privider"  />
	   
	     </td>
	 	</tr>
	    <tr>
	    <th>Action</th>
	    <td>
	     <input class="btn btn-success btn-md" role="button"  type="submit" value="Update"/>
	     </td>
	  	</tr>
  
  
   
  	</form:form>
	</table>
			
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-8">
		</div>
	</div>
</div>

    <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resources/js/scripts.js'/>"></script>
  </body>
</html>
