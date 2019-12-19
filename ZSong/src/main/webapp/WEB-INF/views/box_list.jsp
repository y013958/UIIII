<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userID = null;
	String toID = null;
	userID = (String)session.getAttribute("id");
	toID = (String)request.getParameter("toID");
	
// 	if(userID == null){
// 		session.setAttribute("messageType","오류메시지");
// 		session.setAttribute("messageContent","로그인 되어있지 않습니다.");
// 		response.sendRedirect("/zzzz/home.jsp");
// 		return;
// 	}
%>
    
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 	<link rel="stylesheet" href="/css/bootstrap.css"> -->
<!-- 	<link rel="stylesheet" href="/css/custom.css"> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- 	<script src="/js/bootstrap.js"></script> -->

<!-- <script src="http://code.jquery.com/jquery-1.7.js"></script> -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">


	<script type="text/javascript">
	listFunction();
		
		function getInfiniteChat(){
			setInterval(function() {
				chatListFunction(lastID);
			},3000);
		}
		function chatBoxFunction(){
			var userID = '<%=userID%>'
				$.ajax({
					type: "POST",
					url: "/chatBox",
					data: {
						userID: encodeURIComponent(userID),
					},
					success: function(data){
						if(data == "") return;
						$('#boxTable').html('');
						var parsed = JSON.parse(data);
						var result = parsed.result;
						for(var i = 0 ; i < result.length; i++){
							if(result[i][0].value == userID){
								result[i][0].value = result[i][1].value;
							}else{
								result[i][1].value = result[i][0].value;
							}
							addBox(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value, result[i][5].value);
						}
					}
				});
		}
		function addBox(lastID, toID, chatContent, chatTime, unread, profile){
			$('#boxTable').append('<tr onclick="location.href=\'/index?toID=' + encodeURIComponent(toID) + '\'">' +
					'<td style="width:130px;">' +
					'<img class ="media-object img-circle" style="margin:0 auto; max-width:30px; max-height:30px;" src="' + profile + '">' + 
					'<h5>' + lastID + '</h5></td>' + 
					'<td style="vertical-align: middle;">' + 
					'<h5>' + chatContent +
					'<span class="badge badge-danger">' + unread + '</span></h5>' + 
					'<div style="text-align: right; font-size: 13px;">' + chatTime + '</div>' +
					'</td>' + 					
					'</tr>');
		}

		function listFunction(){
			var userID = '<%=userID%>'
				$.ajax({
					type: "POST",
					url: "/manager_list",
					data: {
						userID: encodeURIComponent(userID),
					},
					success: function(data){
// 						alert(data);			
						if(data == "") return;
						$('#boxTable2').html('');
						var parsed = JSON.parse(data);
						var result = parsed.result;
						for(var i = 0 ; i < result.length; i++){
							addmanagerlist(result[i][0].value, result[i][1].value, '/img/profile.png');
						}
					}
				});
		}
		function addmanagerlist(toID, grade, profile){
			$('#boxTable2').append('<tr onclick="location.href=\'/index?toID=' + encodeURIComponent(toID) + '\'">' +
					'<td style="width:130px;">' +
					'<img class ="media-object img-circle" style="margin:0 auto; max-height:30px;" src="' + profile + '">' + 
					'<h5>' + toID + '</h5></td>' + 			
					'</tr>');
		}
		
		function getInfiniteBox(){
			setInterval(function() {
				chatBoxFunction();
			},2000);
		}
	</script>
	
	
	<style>
    	
    	.notice-box { padding:10px; background-color:rgb(249, 250, 252); text-align:left; } 
    	.notice-box p { -webkit-margin-before: .3em; -webkit-margin-after: .5em; } 
    	#close { z-index: 2147483647; position:absolute; padding:2px 5px; font-weight: 700; text-shadow: 0 1px 0 #fff; font-size: 1.3rem;  left: 290px; top: 440px;} 
    	#close:hover { border: 0; cursor:pointer; opacity: .75; }

    </style>
	
	<script>
		function SirenFunction(idMyDiv){
		     var objDiv = document.getElementById(idMyDiv);
		     if(objDiv.style.display=="block"){ objDiv.style.display = "none"; }
		      else{ objDiv.style.display = "block"; }
		}
	</script>
    
    
        <style>
        .sir_singo_msg{color:#934545;margin-bottom:30px}
        .sir_singo_msg button {cursor:pointer;font-family:Arial,'돋움',Dotum;border:none;padding:0;background:#fff; outline:0}
        .sir_singo_msg .blind_view{font-size:1.14em;font-weight:bold;color:#ff4343;margin-top:-3px;text-decoration:underline}
        .notice-box { display:none; }
		</style>
	
	
	
	
	
		<script type="text/javascript">
		function findFunction(){
			var userID = $('#findID').val();
				$.ajax({
					type: "POST",
					url: "/userCheck",
					data: { userID: userID },
					success: function(result){
						if(result == 0){
// 							$('#checkMessage').html('친구찾기 성공');
// 							$('#checkType').attr('class', 'modal-content panel-success');
// 							alert("성공");
							getFriend(userID);
						}else{
// 							$('#checkMessage').html('친구를 찾을 수 없습니다.');
// 							$('#checkType').attr('class', 'modal-content panel-warning');
							alert("실패");
							failFriend(userID);
						}
// 						$('#checkModal').modal("show");
					}
				});
		}
		function getFriend(findID){
			$('#friendResult').html('<thead style="background-color:#007BFF;">' + 
					'<tr>' + 
					'<th style="height: 32px; padding: 0;"><h4 style="color:white; margin: 5px;">검색결과</h4></th>' + 
					'</tr>' + 
					'</thead>' + 
					'<tbody>' + 
					'<tr>' + 
					'<td style="text-align: center;"><h3 style="margin-top:10px;">' + findID + '<a href="/index?toID=' + encodeURIComponent(findID) +'" class="btn btn-primary pull-right">' + '메시지 보내기</h3></a></td>' + 
					'</tr>' + 
					'</tbody>');
		}
		function failFriend(findID){
			$('#friendResult').html("");
		}
	</script>
	
</head>
<body>

<div class="container" style="padding: 0; margin: 0; width: 100%; border-color: #2980b9;">
	<table class="table table-bordered table-hover" style="margin: 0 auto;  border-color: #2980b9;">
		<thead style="background-color:#2980b9;">
			<tr style="border-color: #2980b9;" >
				<th colspan="2" style="border-color: #2980b9;"><h4 style="color: white;">관리자 메신저</h4></th>
			</tr>
		</thead>
	</table>
	
		<nav>
		  <div class="nav nav-tabs" id="nav-tab" role="tablist" style="background-color:#2980b9;">
		    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true" style="width: 50%; text-align: center; color: black;">관리자 목록</a>
		    <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false" style="width: 50%; text-align: center; color: black;">대화방</a>
		  </div>
		</nav>
		<div class="tab-content" id="nav-tabContent">	
		  <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
		  
		  
		  	<div style="overflow-y:auto; width: 100%; max-height: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable2">
					</tbody>
				</table>
			</div>	
		  
		  </div>
		  
		  <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
		  
		  	<div style="overflow-y:auto; width: 100%; max-height: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable">
					
					</tbody>
				</table>
			</div>	
				  
		  </div>
		</div>
</div>



		
<!-- 		세션과 상관없이 지속해서 실행 되어야 하는 부분 -->
		<script type="text/javascript">
			$(document).ready(function() {
				chatBoxFunction();
				getInfiniteBox();
			});
		</script>
		
		
		
		
	<span id='close' onclick="SirenFunction('SirenDiv'); return false;"><img alt="" src="/img/plus.png"></span>
	
	<div class="notice-box"  id="SirenDiv" style='padding:0;  border-color:#2980b9; border-color:#2980b9; z-index: 2147483647; position:absolute; left:50px; top:300px;'>	
<!-- 		<iframe scrolling="no" src="http://localhost:8081/zzzz/find.jsp" style="width: 300px; height: 208px; border: 2px solid; border-color: #2980b9; border-top-width: 15px; border-bottom-width: 3px; "> -->
				
<!-- 		</iframe> -->
				
<div class="container" style="padding: 0; margin: 0; width: 100%; height: 100%;">
	<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #2980b9; margin: 0 auto;">
		<thead style="background-color:#007BFF;">
			<tr style="height: 32px;">
				<th colspan="3" style="height: 32px; padding: 0;"><h4 style="color: white; margin: 5px"><img onclick="location.href='/box_list'" alt="" src="./img/arrow_back2.png">관리자 검색</h4></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="width: 110px;"><h5>회원 아이디</h5></td>
				<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 관리자 아이디"></td>
				<td style="width: 50px;"><button class="btn btn-primary pull-right" onclick="findFunction();">검색</button></td>
			</tr>
		</tbody>
	</table>
</div>

<div>
	<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
	</table>
</div>
		
</div>
		

	  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>  
	
</body>
</html>