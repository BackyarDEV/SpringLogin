<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Spring Login</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width"/>
    <base href="/"/>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script> 
	<title>Error Occured!</title>
	<style type="text/css">
		.error-container {
			width: 640px;
	    	margin: 30px auto;
	    	background: #f7f7f7;
	        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	        padding: 40px;
		}
		.error-container h4 {
	        margin: 0 0 15px;
	    }
	    .or-seperator {
	        margin: 20px 0 10px;
	        text-align: center;
	        border-top: 1px solid #ccc;
	    }
	    .or-seperator i {
	        padding: 0 10px;
	        background: #f7f7f7;
	        position: relative;
	        top: -11px;
	        z-index: 1;
	    }
	</style>
</head>
<body>
	<div class='container error-container'>
		<h4>Sorry, Something Went Wrong!.</h4>
		Click <a href="/login">here</a>
		if you are not redirected automatically to the Login page.
	</div>
	
	<script type="text/javascript">
		$(function(){
			setTimeout(redirect, 8000);
		});
		
		function redirect(){
			location.replace("/login");
		}
	</script>
</body>
</html>