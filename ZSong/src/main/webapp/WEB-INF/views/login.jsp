<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <% if(session.getAttribute("ValidMem") != null) { %> --%>
<%--    <jsp:forward page="main.jsp"></jsp:forward> --%>
<%-- <% } %>   --%>

<!doctype html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


<!-- <html lang="en"><head> -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/docs/4.1/assets/img/favicons/favicon.ico">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.1/examples/sign-in/">


    <title>로그인</title>
    
    <style>
		
		.text-center {
			text-align: center !important;
		}
		
		body {
			display: -ms-flexbox;
			display: flex;
			-ms-flex-align: center;
			align-items: center;
			padding-top: 40px;
			padding-bottom: 40px;
			background-color: #f5f5f5;
		}
		
		html, body {
			height: 100%;
		}
		
		.form-signin {
		    width: 100%;
		    max-width: 330px;
		    padding: 15px;
		    margin: auto;
		}
		
		#area {
		margin-top:3px;
		margin:8x;
/* 		  margin: 0; */
/* 		  padding: 0; */
/* 		  display: -webkit-box; */
/* 		  display: -moz-box; */
/* 		  display: -ms-flexbox; */
/* 		  display: -moz-flex; */
/* 		  display: -webkit-flex; */
		  display: flex;
		  justify-content: space-between;
/* 		  list-style: none; */
		}
</style>

  </head>


<body class="text-center" cz-shortcut-listen="true">
    
    <form class="form-signin" action="loginOk">
      <img class="mb-4" src="../img/security_key.png" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">관리자 로그인</h1>
<!--       Please sign in -->
      <label for="inputEmail" class="sr-only">ID</label>
      <input type="text" id="id" name="id" class="form-control" placeholder="ID" required="" autofocus="">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" name="pw" id="pw" class="form-control" placeholder="Password" required="">
      <div class="checkbox mb-3" id="area">
        <label>
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

      <p class="mt-5 mb-3 text-muted">© 2019</p>
    </form>
    
</body>

<!-- </html> -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>