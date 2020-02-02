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
		.login-form {
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
		}
	</style>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</head>
<body>

	<div class="container">
		<div class="container unauthenticated">
		    <div class="login-form">
			    <form class="form" action="" method="post">
			        <h2 class="text-center">Sign in</h2>		
			        <div class="text-center social-btn">
			            <a href="/login/google" class="btn btn-danger btn-block"><i class="fa fa-google"></i> Sign in with <b>Google</b></a>
			            <a href="/login/github" class="btn btn-github btn-block"><i class="fa fa-github"></i> Sign in with <b>GitHub</b></a>
		            </div>
					<div class="or-seperator"><i>or</i></div>
			        <div class="form-group">
			        	<div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-user"></i></span>
			                <input type="text" class="form-control" name="username" placeholder="Username" required="required">
			            </div>
			        </div>
					<div class="form-group">
			            <div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
			                <input type="password" class="form-control" name="password" placeholder="Password" required="required">
			            </div>
			        </div>        
			        <div class="form-group">
			            <button type="submit" class="btn btn-success btn-block login-btn">Sign in</button>
			        </div>
			        <div class="clearfix">
			            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
			            <a href="#" class="pull-right text-success">Forgot Password?</a>
			        </div>  
			        
			    </form>
			    <div class="hint-text small">Don't have an account? <a href="/register" class="text-success">Register Now!</a></div>
			</div>
		</div>		
	</div>
	<script type="text/javascript">
		/*$('.form').submit(function(s){
			s.preventDefault();
			var form = $('.form');
			var url = form.attr("action");
			var method = form.attr("method");
			
			$.ajax({
				type: method,
				url: url,
				data: form.serialize(),
				success: function(data){
					if (data){
						location.replace('/');
					} else {
						alert('There was a problem signing in!');
					}
				}	
			});
		});*/
		
	</script>
</body>
</html>