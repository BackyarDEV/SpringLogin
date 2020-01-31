<%@page import="com.backyardev.springlogin.User"%>
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
	<style type="text/css">
		.profile-container {
			width: 640px;
	    	margin: 30px auto;
		}
		/*.login-form {
			width: 340px;
	    	margin: 30px auto;
		}
	    .login-form form {
	    	margin-bottom: 15px;
	        background: #f7f7f7;
	        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	        padding: 30px;
	    }
	    .login-form h2 {
	        margin: 0 0 15px;
	    }
	    .login-form .hint-text {
			color: #777;
			padding-bottom: 15px;
			text-align: center;
	    }
	    .form-control, .btn {
	        min-height: 38px;
	        border-radius: 2px;
	    }
	    .login-btn {        
	        font-size: 15px;
	        font-weight: bold;
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
	    .social-btn .btn {
	        margin: 10px 0;
	        font-size: 15px;
	        text-align: left; 
	        line-height: 24px;       
	    }
		.social-btn .btn i {
			float: left;
			margin: 4px 15px  0 5px;
	        min-width: 15px;
		}
		.input-group-addon .fa{
			font-size: 18px;
		}
		.btn-github {
			background: #24292e;
			color: #fff;
		}
		.btn-github:hover, .btn-github:focus{
			background: #3a3c3e;
			color: #fff;
		}*/
	</style>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</head>
<body>

	<div class="container">
		<div class="container authenticated">
			<div class="container profile-container">
			    <div class="row">
			        <div class="col-sm-4 col-md-4">
			            <img id="propic" alt="profile picture" class="img-rounded img-responsive" />
			        </div>
			        <div class="col-sm-6 col-md-6">
			            <blockquote>
			                <p><span id="user"></span></p>
			                <small><i class="glyphicon glyphicon-envelope"></i> <span id="email"></span></small>
			            	<br><hr>
							<div>
								<button onClick="logout()" class="btn btn-primary">Logout</button>
							</div>
			            </blockquote>
			        </div>
			    </div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	    
          $.get("/user", function(data) {
        	  if(data.name != undefined){
       		  	$('#propic').attr('src', data.propic);
               	$('#user').html(data.name);
               	$('#email').html(data.email);  
        	  }
          });
          var logout = function() {
         	location.replace('/logout');
          }

	</script>
</body>
</html>