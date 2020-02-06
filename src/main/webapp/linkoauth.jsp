<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Link Account</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width"/>
    <base href="/"/>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script> 
	<style type="text/css">
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
		.email {
			text-align: center;
			font-weight: 600;
		}
		.close{
			position: relative;
		    min-height: 100%;
		    bottom: 20px;
		}
	</style>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</head>
<body>

	<div class="container">
		<div class="container unauthenticated">
		    <div class="login-form">
			    <form class="form" action="/linkaccount" method="post">
			        <p><strong>It seems like your email id is already registered!</strong></p>
			        Enter your password to <strong>link your accounts!</strong>
			        
					<div class="or-seperator"></div><br>
					<div class="email">Email: <span id="email"></span></div>
			        <div class="form-group">
			        	<input id="username" type="hidden" name="username">
			        </div>
					<div class="form-group">
			            <div class="input-group">
			                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
			                <input type="password" class="form-control" name="password" placeholder="Password" required="required">
			                <input type="hidden" name="link" value="yes">
			            </div>
			        </div>        
			        <div class="form-group">
			            <button type="submit" class="btn btn-success btn-block login-btn">Link</button>
			        </div>
			    </form>
			    <div class="hint-text small">Don't wanna link account? <a href="#" data-toggle="modal" data-target="#modalSkip" class="text-success">Skip linking</a></div>
			</div>
		</div>
		<div class="modal fade" id="modalSkip" tabindex="-1" role="dialog" aria-labelledby="modalSkipTitle" aria-hidden="true">
		    <div class="modal-dialog modal-dialog-centered" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h4 class="modal-title" id="ModalTitle">Skip Linking?</h4>
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
	                    </button>
		            </div>
		            <div class="modal-body">
		                <p>All your data will be deleted once you are logged out of the system!</p><br>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
		                <button id="skipLinkBtn" onclick="skipLink()" class="btn btn-primary">Yes</button>
		            </div>
		        </div>
		    </div>
		</div>		
	</div>
	<script type="text/javascript">
		$.get("/user", function(data) {
	  	  if(data.name != undefined){
	         	$('#email').html(data.email);
	         	document.getElementById("username").value = data.email;
	  	  }
	    });
		
		$('.form').submit(function(s){
			s.preventDefault();
			var form = $('.form');
			var url = form.attr("action");
			var method = form.attr("method");
			
			$.ajax({
				type: method,
				url: url,
				data: form.serialize(),
				success: function(data){
					if (data == "wrong pass"){
						alert("Wrong password!");
					} else if(data == "true"){
						location.replace("/");
					} else {
						alert("There was a problem processing your request!");
					}
				}	
			});
		});
		
		function skipLink(){
			var link = "no";
			var url = "/linkaccount";
			var method = "POST";
			
			$.ajax({
				type: method,
				url: url,
				data: {"link": "no"},
				success: function(data){
					if(data == "true"){
						console.log(data);
						$('#modalSkip').modal('hide');
						location.replace("/");
					} else{
						console.log(data);
						alert("There was a problem processing your request!");
					}
				}
			});
		}
	</script>
</body>
</html>