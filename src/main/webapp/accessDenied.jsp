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
<title>Access Denied!</title>
</head>
<body>
<h2>Sorry, you do not have permission to view this page.</h2>
 
Click <a href="/login">here</a>
if you are not redirected automatically to the Login page.

<script type="text/javascript">
	$(function(){
		setTimeout(redirect, 2000);
	});
	
	function redirect(){
		location.replace("/login");
	}
</script>
</body>
</html>