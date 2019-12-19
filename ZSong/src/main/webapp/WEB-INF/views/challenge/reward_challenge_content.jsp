<%@page import="com.study.springboot.dto.RecordDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%	
	session.setAttribute("location", "challenge");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>진행중인 챌린지</title>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>


    <style>

/*     	tbody{ */
/*     		background-color: #E9ECEF; */
/*     	}    	 */
    	
/*     	ul{ */
/*     		margin: auto; */
/*     	} */
    	
/*     	td{ */
/*     		text-align: center; */
/*     	} */

/* 		.custom-select { */
/* 			width: 110px; */
/* 		} */

		::-webkit-scrollbar { 
		
		    display: none; 
		
		}
    </style>

<script type="text/javascript">
	function register(){
		javascript:window.location='register_challenge?page=1';
	}
	function vote(){
		javascript:window.location='vote_public_challenge?page=1';
	}
	function ongoing(){
		javascript:window.location='ongoing_challenge?page=1';
	}
	function reward(){
		javascript:window.location='reward_challenge?page=1';
	}
	function end(){
		javascript:window.location='end_challenge?page=1';
	}
	function state(){
		javascript:window.location='state_challenge';
	}

	function approval(num){
		var result = confirm('해당 챌린지를 승인 하시겠습니까?');

		if(result) { 
			  $.ajax({
				    type:'post',
				    url:'approval_private_content',
				    data: ({num:num}),
				    success:function(data){
					    if(data == 0)
						    alert("승인 실패");
					    else
					    {
						    document.location.href="register_challenge?page=1";
// 						     page최근 페이지로 옮기기
					    }
				    }

				 });
		} else{
		}

	}


	
	
	function chatListFunction(){
		$('#participant_list').empty();
		
		var num = ${challenge.challenge_num};
		$.ajax({
			type: "POST",
			url: "read_participant",
			data: {num:num},
			success: function(data){
// 				alert(data);
				var parsed = JSON.parse(data);
// 				alert(parsed);
				var result = parsed.result;
// 				alert(result);
				
				$('#party').text("참가자 "+result.length+"명");
				for(var i = 0 ; i < result.length; i++){
					addparticipan(result[i][0].value, result[i][1].value);
// 					alert(result[i][0].value +"    "+ result[i][1].value);	
				}
			}
		});
	}
	
	function addparticipan(nickname, profile){

		$('#participant_list').append('<div class="row" style="padding-left:20px; margin:0;">' +
				'<div class="media">' + 
				'<a class="pull-left" href="#">' + 
// 				'<img class ="media-object img-circle" style="width:30px; height:30px;" src='+profile+' alt="">' +
				'<img class ="media-object img-circle" style="width:30px; height:30px;" src="/img/profile.png" alt="">' +	
				'</a>' + 
				'<div class="media-body">' + 
				'<h5 class="media-heading" style="padding-left: 5px;">' + 
				nickname + 
				'</h4>' + 
				'</div>' + 
				'</div>' + 
				'</div>' + 
				'<hr>');
		
// 		var objDiv = document.getElementById("participant_list");
// 		objDiv.scrollTop = objDiv.scrollHeight;
	}


	function imagelist(){
		$('#image').empty();
				
		var num = ${challenge.challenge_num};
			$.ajax({
				type: "POST",
				url: "read_imagelist",
				data: {num:num},
				success: function(data){
		//				alert(data);
					var parsed = JSON.parse(data);
		//				alert(parsed);
					var result = parsed.result;
		//				alert(result);
					
					for(var i = 0 ; i < result.length; i++){
						addimage(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, i);
		//					alert(result[i][0].value +"    "+ result[i][1].value);	
					}
				}
			});
	}

	function addimage(title, comment, image, time,i){

		$('#image').append('<div class="col-md-4" id='+i+'>' +
				'<div class="card">' +
				'<img src="'+image+'" class="card-img-top" alt="...">' +
				'<div class="card-body">' +
				'<h5 class="card-title">'+title+'</h5>' +
				'<p class="card-text">'+comment+'</p>' +
				'</div>' +
				'<div class="card-footer">' +
				'<small class="text-muted">'+time+'</small>' +
				'</div>' +
				'</div>' +
				'</div>'
				);

// 		var objDiv = document.getElementById("participant_list");
// 		objDiv.scrollTop = objDiv.scrollHeight;

	}


	function getInfiniteChat(){
		setInterval(function() {
			chatListFunction();
			imagelist();
		},10000);
	}
	


</script>


<style>
    .menu a{cursor:pointer;}
    .menu .hide{display:none;}

</style>
  
<script type="text/javascript">
    // html dom 이 다 로딩된 후 실행된다.
    $(document).ready(function(){
        // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
        $(".menu>button").click(function(){
        	var submenu = $(this).next("ul");
        	
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( submenu.is(":visible") ){
                submenu.slideUp();
                $("#tab").text("▼");
            }else{
                submenu.slideDown();
                $("#tab").text("▲");
            }
        });
    });

</script>



<style type="text/css">
	    
    	a #MOVE_TOP_BTN {
	    position: fixed;
	    right: 2%;
	    bottom: 50px;
	    display: none;
	    z-index: 999;
	}

</style>

<script type="text/javascript">
$(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 300) {
            $('#MOVE_TOP_BTN').fadeIn();
        } else {
//             $('#MOVE_TOP_BTN').fadeOut();
        }
    });
    
    $("#MOVE_TOP_BTN").click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 400);
        return false;
    });
});
</script>



<script type="text/javascript">

	$(window).scroll(function(event){
	if(jQuery(window).scrollTop() > jQuery(".banner").offset().top) {
	jQuery("#chase").css("position", "fixed");
	}
	else if((jQuery(window).scrollTop() < jQuery(".banner").offset().top)) {
	jQuery("#chase").css("position", "static");
	}
	});
</script>



</head>
<body>



<jsp:include page="../header.jsp" />

	
		
<div class="row" style="margin: 50px; min-width: 1050px;">




<div class="col-12">

<div class="row">
  <div class="col-2">
  
  
  <div class="banner">
<div id="chase" style="top:0px;">
  
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="register()">등록 관리</a>
      <a class="list-group-item list-group-item-action" id="list-vote-list" data-toggle="list" href="#" role="tab" aria-controls="vote" onclick="vote()">투표중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="ongoing()">진행중인 챌린지</a>
      <a class="list-group-item list-group-item-action active" id="list-reward-list" data-toggle="list" href="#" aria-controls="reward" onclick="reward()">상금 지급</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="end()">지난 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="state()">챌린지 현황</a>
    </div>
    
    
    </div>
</div>
    
    
  </div>
  
  <div class="col-8"> 
    
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">... </div>
      <div class="tab-pane fade" id="list-vote" role="tabpanel" aria-labelledby="list-vote-list">...</div>
      <div class="tab-pane fade  show active" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">
      
 <table class="table" style="text-align: center;">
    <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="6">비공개 챌린지 상세정보</th>
    </tr>
    
  </thead>
  <tbody>
 
			<tr>
<%-- 			${dto.challenge_first_image} --%>
				<td rowspan="8" width="350px;" height="400px" style="border-right: 1px solid #dee2e6;"><img alt="" src="img/book.jpg" width="100%" height="100%"></td>
				<td colspan="1" style="vertical-align: middle; border-right: 1px solid #dee2e6;">주제(카테고리)</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_title} (${challenge.challenge_category})</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">기간</td>
<%-- 				<td colspan="3" style="vertical-align: middle;"> ${challenge.challenge_start} ~ ${challenge.challenge_end}</td> --%>
				<td colspan="3" style="vertical-align: middle;">
					<c:set var="start" value="${fn:substring(challenge.challenge_start,0,10)}"/> 
					<c:set var="end" value="${fn:substring(challenge.challenge_end,0,10)}"/>
					${start} ~ ${end}
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 방법</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_type}</td>
			</tr>
			
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 빈도</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_frequency}(매일,월수금,주중,주말로 입력받음)</td>
			</tr>
			
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 시간</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_time}</td>
			</tr>
			
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">참가비</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_fee} 원</td>
			</tr>
			
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">신청일</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_date}</td>
			</tr>
			
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">개설자</td>
				<td colspan="3" style="vertical-align: middle;">사용자번호,아이디,이름</td>
			</tr>
			
			
			<tr>
				<td colspan="4">
					
					<script type="text/javascript">

	 					<jsp:useBean id="now" class="java.util.Date" />
	 					<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
	 					
	 	 				<fmt:parseDate var="day" value="${today}"  pattern="yyyy-MM-dd"/>
	 	 				
	 					<fmt:parseDate var="startDate_D"  value="${start}" pattern="yyyy-MM-dd"/>
	 					<fmt:parseDate var="endDate_D" value="${end}"  pattern="yyyy-MM-dd"/>
	 					
	 					 
	 					<fmt:parseNumber var="nowdate" value="${day.time / (1000*60*60*24)}" integerOnly="true" />
	 					<fmt:parseNumber var="strDate" value="${startDate_D.time / (1000*60*60*24)}" integerOnly="true" />
	 					<fmt:parseNumber var="endDate" value="${endDate_D.time / (1000*60*60*24)}" integerOnly="true" />
	 						
					</script>
					
					<div class="progress">
						<c:if test="${ ((nowdate - strDate) / (endDate - strDate)) * 100 >= 0}">
							<div id="bar" class="progress-bar progress-bar-striped bg-danger progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%"><fmt:formatNumber type="number" pattern="0.0" value="${ ((nowdate - strDate) / (endDate - strDate)) * 100}"/>% 진행중</div>
						</c:if>
						<c:if test="${ ((nowdate - strDate) / (endDate - strDate)) * 100 < 0}">
							<div style="width: 100%">참가자 모집중</div>
						</c:if>
					</div>
				 	
				 	<script type="text/javascript">
						var c = ${ ((nowdate - strDate) / (endDate - strDate)) * 100};

				 		if( c >= 100){
				 			$("#bar").css('width','100%');
				 			$("#bar").text("100% 완료");
					 	}
				 		else{
				 			$("#bar").css('width','${ ((nowdate - strDate) / (endDate - strDate)) * 100}%');
						}
				 	</script>
					
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="4" style="vertical-align: middle; background-color: #3EBDFF;">챌린지 규칙</td>
			</tr>
			
			<tr>
				<td colspan="4" rowspan="2" height="200px;" style="vertical-align: middle;">${challenge.challenge_detail}</td>
			</tr>

	
			<tr></tr>
			
			<tr>
				<td colspan="4" align="right">
					<button class="btn btn-outline-primary" type="button" onclick="approval('${challenge.challenge_num}');">승인</button>
					<button class="btn btn-outline-primary" type="button" onclick="postpone();" >보류</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='register_challenge?page=<%= session.getAttribute("cpage") %>';" >목록보기</button>
				</td>
			</tr>
			<tr>
				<th scope="col" colspan="3" style="vertical-align:middle; background-color: #007BFF; font-size: 13pt; color: white;">상금 처리</th>
				<th scope="col" colspan="1" style="vertical-align:middle; background-color: #007BFF;"><button class="btn">상금 처리</button></th>
			</tr>
			<tr>
				<td>참가자 아이디</td>
				<td>달성률</td>
				<td>반환될 참가비</td>
				<td>상금</td>
			</tr>
			
			<c:forEach items="${Record}" var="record">
				<tr>
					<td>${record.member_id}</td>
					<td>
						<fmt:formatNumber value="${(record.certificate_count / record.all_count)*100}" pattern=".0" var="per"/>
						<c:if test="${per == 100.0}">
							100%
						</c:if>
						<c:if test="${per != 100.0}">
							${per}%
						</c:if>
					</td>
					<td>
						${record.challenge_fee * (per/100)}						
					</td>
					<td>${record.member_id}</td>
				</tr>			
			</c:forEach>
			
				
			<tr>
				<td colspan="4"></td>
			</tr>
			
			
			<tr>
				<td colspan="4" align="center">
					<li class="menu"  style="list-style: none; align-content: center;">
						<button class="btn btn-outline-primary" type="button" onclick="" style="margin-bottom: 15px;" id="tab">▼</button>
			            <ul class="hide" style="list-style: none; text-align: left; padding:0; margin: 0;">
			                <li>

								<div class="row">
									<div class="card-deck" id="image">
								
								  	</div>
								</div>
			                </li>
			            </ul>
			        </li>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right">
					<a id="MOVE_TOP_BTN" href="#">TOP</a>
				</td>
			</tr>
			
  </tbody>
  </table>

      
      </div>
      <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">...</div>
      <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">...</div>
    </div>
  </div>
  
  
<div class="col-2">  
	
	<!--참여자 리스트   -->
    <div style="line-height:37px; height:37px; color: white; font-size: 14pt; background-color: #007BFF; vertical-align:middle; text-align: center; border-top: 2px solid #dee1e6; border-left: 2px solid #dee1e6; border-right: 2px solid #dee1e6;" id="party">참가자 </div>
    <div id="participant" style="text-align: center; border: 2px solid #dee1e6;">
    	<div id="participant_list" style="overflow-y:auto; height: 300px;" >
    	
    	</div>
    </div>

<br/><br/><br/>
	

    <h3>채팅방</h3>
		<textarea rows="12" id="result" readonly style="border:2px solid #dee1e6; resize: none; width: 100%;"></textarea>

	
<br/><br/><br/>
	
	
	<table style="border: 2px solid #dee1e6; width: 100%">
		<tr align="center"><th colspan="2">상금 계산</th></tr>
		<tr>
			<td>총 금액</td>
			<td>2000000</td>
		</tr>
		<tr>
			<td>미달액</td>
			<td>50000</td>
		</tr>
		<tr>
			<td>상금액</td>
			<td>미달액/ 100%달성자</td>
		</tr>
	</table>

	
</div>

</div>
</div>
</div>


 <!-- light box -->

<div class="mw_layer">
<div class="bg"></div>
<div id="layer"></div>
</div>





<!-- http://rwdb.kr/datepicker/ -->


<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
	
	
	<!-- 지속해서 실행 되어야 하는 부분 -->
		<script type="text/javascript">
			$(document).ready(function() {
				getInfiniteChat();
// 				chatListFunction();
// 				imagelist();
			});

			$(window).on('load', function(){
				chatListFunction();
				imagelist();
			});
		</script>
		
		
</body>




<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-analytics.js"></script>

  <!-- Add Firebase products that you want to use -->
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-auth.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-firestore.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-database.js"></script>
  
<script>
  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyDdhCOZel4_q8LAWJaRSsMn1ACigJ9JrBk",
    authDomain: "project-71fd1.firebaseapp.com",
    databaseURL: "https://project-71fd1.firebaseio.com",
    projectId: "project-71fd1",
    storageBucket: "project-71fd1.appspot.com",
    messagingSenderId: "19758304536",
    appId: "1:19758304536:web:03ec3565bbc7884b18a26d",
    measurementId: "G-PGJ9N4E202"
  };
  
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();

  
  var a = document.getElementById("result");
  
  var database = firebase.database();

  
		var messageRef = database.ref("1");
		messageRef.on('child_added', function(snapshot) {
		  var data = snapshot.val();
		  var name = data.id;
		  var message = data.text;

		  var b = name + "  :  " + message +"\n";
			
			  a.value += b;
			  document.getElementById("result").scrollTop = document.getElementById("result").scrollHeight;
		});
  

  
</script>


</html>