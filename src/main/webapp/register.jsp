<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Spring Register</title>
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
	</style>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</head>
<body>

	<div class="container">
		<div class="container unauthenticated">
		    <div class="login-form">
			    <form class="form" action="/api/useraccounts" method="post">
			        <h2 class="text-center">Sign Up</h2>		
			        <div class="form-group">
			        	<div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-user"></i></span>
			                <input type="text" class="form-control" name="user_name" placeholder="Username" required="required">
			            </div>
			        </div>
			        <div class="form-group">
			        	<div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-user"></i></span>
			                <input type="text" class="form-control" name="first_name" placeholder="First Name" required="required">
			            </div>
			        </div>
			        <div class="form-group">
			        	<div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-user"></i></span>
			                <input type="text" class="form-control" name="last_name" placeholder="Last Name" required="required">
			            </div>
			        </div>
			        <div class="form-group">
			        	<div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-at"></i></span>
			                <input type="text" class="form-control" name="email" placeholder="Email" required="required">
			            </div>
			        </div>
					<div class="form-group">
			            <div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
			                <input type="password" id="pass" class="form-control" name="password" placeholder="Password" required="required">
			            </div>
			        </div>        
					<div class="form-group">
			            <div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
			                <input type="password" id="conf" class="form-control" name="conf_password" placeholder="Confirm Password" required="required">
			            </div>
			        </div>
	                <input type="hidden" class="form-control" value="true" name="enabled">
			        <div class="form-group">
			            <button type="submit" class="btn btn-success btn-block login-btn">Sign Up</button>
			        </div>
			        
			    </form>
			</div>
		</div>		
	</div>
	<script>
	$('.form').submit(function(s){
		s.preventDefault();
		var form = $('.form');
		var url = form.attr("action");
		var method = form.attr("method");
		
		var pass = $('#pass').val();
		var conf = $('#conf').val();
		
		console.log(pass);
		
		if(pass != conf){
			alert("passwords do not match");
		} else {
			$.ajax({
				type: method,
				url: url,
				data: form.serialize(),
				success: function(data){
					alert(data);
					if (data){
						window.location.replace('/');
					} else {
						alert('There was a problem registering new account!');
					}
				}
			});	
		}
	});
	  
	</script>
</body>
</html>