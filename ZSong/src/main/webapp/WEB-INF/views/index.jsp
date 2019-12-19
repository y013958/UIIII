<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@page import="com.study.jsp.MemberDAO"%> --%>
<%
	String userID = null;
	String toID = null;
	userID = (String)session.getAttribute("id");
	toID = (String)request.getParameter("toID");
	
// 	if(userID.equals(URLDecoder.decode(toID, "UTF-8"))){
// 		session.setAttribute("messageType", "오류메시지");
// 		session.setAttribute("messageContent", "오류메시지");
// 		response.sendRedirect("home.jsp");
// 		return;
// 	}

// 	String fromProfile = new MemberDAO().getprofilePath(userID);
// 	String toProfile = new MemberDAO().getprofilePath(toID);
	String fromProfile = "/img/profile.png";
	String toProfile = "/img/profile.png";
%>
    
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="./css/bootstrap.css">
	<link rel="stylesheet" href="./css/custom.css">
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="./js/bootstrap.js"></script>


	<script type="text/javascript">
		function autoClosingAlert(selector, delay){
			var alert = $(selector).alert();
			alert.show();
			window.setTimeout(function(){ alert.hide() }, delay);
		}
		
		function submitFunction() {
			var fromID = '<%= userID %>';
			var toID = '<%= toID %>';
			var chatContent = $('#chatContent').val();	
			$.ajax({
				type: "POST",
				url: "/chatSubmitServlet",
				data: {
					fromID: encodeURIComponent(fromID),
					toID: encodeURIComponent(toID),
					chatContent: encodeURIComponent(chatContent),
				},
				success: function(result){
					if(result == 1){
						autoClosingAlert('#successMessage', 2000);
					}else if(result == 0){
						autoClosingAlert('#dangerMessage', 2000);
					}else{
						autoClosingAlert('#warningMessage', 2000);
					}
				}
			});
			$('#chatContent').val('');
		}
		
		var lastID = 0;
		function chatListFunction(type){
			var fromID = '<%= userID %>';
			var toID = '<%= toID %>';
			$.ajax({
				type: "POST",
				url: "/chatListServlet",
				data: {
					fromID: encodeURIComponent(fromID),
					toID: encodeURIComponent(toID),
					listType: type
				},
				success: function(data){
					if(data == "") return;
					
					var parsed = JSON.parse(data);
					var result = parsed.result;
					for(var i = 0 ; i < result.length; i++){
						if(result[i][0].value == fromID){
							result[i][0].value = '나';
						}
						addChat(result[i][0].value, result[i][2].value, result[i][3].value);
					}
					lastID = Number(parsed.last);
				}
			});	
		}
		
		function addChat(chatName, chatContent, chatTime){
			if(chatName=='나'){
			$('#chatlist').append('<div class="row">' +
					'<div class="col-lg-12">' +
					'<div class="media">' + 
					'<a class="pull-left" href="#">' + 
					'<img class ="media-object img-circle" style="width:30px; height:30px;" src="<%=fromProfile%>" alt="">' + 
					'</a>' + 
					'<div class="media-body">' + 
					'<h4 class="media-heading">' + 
					chatName + 
					'<span class="small pull-right">' + 
					chatTime + 
					'</span>' + 
					'</h4>' + 
					'<p>' + 
					chatContent + 
					'</p>' + 
					'</div>' + 
					'</div>' + 
					'</div>' + 
					'</div>' + 
					'<hr>');
			}
			else{
				$('#chatlist').append('<div class="row">' +
						'<div class="col-lg-12">' +
						'<div class="media">' + 
						'<a class="pull-left" href="#">' + 
						'<img class ="media-object img-circle" style="width:30px; height:30px;" src="<%=toProfile%>" alt="">' + 
						'</a>' + 
						'<div class="media-body">' + 
						'<h4 class="media-heading">' + 
						chatName + 
						'<span class="small pull-right">' + 
						chatTime + 
						'</span>' + 
						'</h4>' + 
						'<p>' + 
						chatContent + 
						'</p>' + 
						'</div>' + 
						'</div>' + 
						'</div>' + 
						'</div>' + 
						'<hr>');
				}
			var objDiv = document.getElementById("chatlist");
			objDiv.scrollTop = objDiv.scrollHeight;

		}
		function getInfiniteChat(){
			setInterval(function() {
				chatListFunction(lastID);
			},2000);
		}
	</script>
</head>
<body>

<div class="container bootstrap snippet" style="width: 100%; padding: 0;" >
	<div class="row" style="margin: 0;">
		<div class="col-xs-12" style="padding: 0;">
			<div class="portlet portlet-blue" style="margin: 0;">
				<div class="portlet-heading">
					<div class="portlet-title">
						<h4><img onclick="location.href='/box_list'" alt="" src="/img/arrow_back2.png"><i class="fa fa-circle text-green"></i>채팅창</h4>
					</div>
					<div class="clearfix"></div>
				</div>
				<div id="chat" class="panel-collapse collapse in" >
					<div id="chatlist" class="portlet-body chat-widget" style="overflow-y:auto; width: auto; height: 370px;">
						
					</div>
					<div class="portlet-footer">
						
							<div class="row" style="height: 90px;">
								<div class="form-group col-xs-10" style="padding-left: 10px;">
									<textarea style="height: 80px;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요." maxlength="100"></textarea>
								</div>
								<div class="form-group col-xs-2" style="padding:0; padding-right:10px; text-align: center;">
									<button style="height:80px; width: 100%;" type="button" class="btn btn-default pull-center" onclick="submitFunction();">전송</button>
									<div class="clearfix"></div>
								</div>
							</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- <div class="alert alert-success" id="successMessage" style="display:none; margin: 0; padding: 0;"> -->
<!-- 	<strong>메세지 전송 성공</strong> -->
<!-- </div> -->
<!-- <div class="alert alert-danger" id="dangerMessage" style="display:none; margin: 0; padding: 0;"> -->
<!-- 	<strong>이름과 내용 입력하세요</strong> -->
<!-- </div> -->
<!-- <div class="alert alert-warning" id="warningMessage" style="display:none; margin: 0; padding: 0;"> -->
<!-- 	<strong>데이터베이스 오류 발생</strong> -->
<!-- </div> -->
<%
// 	String messageContent = null;
// 	if(session.getAttribute("messageContent") != null){
// 		messageContent = (String) session.getAttribute("messageContent");
// 	}
// 	String messageType = null;
// 	if(session.getAttribute("messageContent") != null){
// 		messageType = (String) session.getAttribute("messagetype");
// 	}
// 	if(messageContent != null){
%>
<!-- 	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true"> -->
<!-- 		<div class="vertical-alignment-helper"> -->
<%-- 			<div class="modal-content <% if(messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("pannel-success"); %>"> --%>
<!-- 				<div class="modal-header panel-heading"> -->
<!-- 					<button type="button" class="close" data-dismiss="modal"> -->
<!-- 						<span aria-hidden="ture">&time</span> -->
<!-- 						<span class="sr-only">Close</span> -->
<!-- 					</button>	 -->
<!-- 					<h4 class="modal-title"> -->
<%-- 						<%= messageType %> --%>
<!-- 					</h4> -->
<!-- 				</div> -->
<!-- 				<div	class="modal-body"> -->
<%-- 					<%= messageType %> --%>
<!-- 				</div> -->
<!-- 				<div	class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div>	 -->
<!-- 	</div> -->
	<script>
// 		$('#messageModal').modal("show");
	</script>
		<%
// 			session.removeAttribute("messageContent");
// 			session.removeAttribute("messageType");
// 			}	
		%>
		
<!-- 지속해서 실행 되어야 하는 부분 -->
		<script type="text/javascript">
			$(document).ready(function() {
				chatListFunction(0);
				getInfiniteChat();
			});
		</script>

</body>
</html>