<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%
 	session.setAttribute("location", "challenge");
 	String name = (String) session.getAttribute("name");
 	String id = (String) session.getAttribute("id");
 	String grade = (String) session.getAttribute("grade");
 %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지</title>

<!-- <script src="//code.jquery.com/jquery-3.3.1.min.js"></script> -->
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
</script>

<style>
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




<!-- lightbox css -->

<style>

.mw_layer {

display: none;
position: fixed;
_position: absolute;
top: 0;
left: 0;
z-index: 0;
width: 100%;
height: 100%
}

.mw_layer.open {
display: block
}

.mw_layer .bg {
position: absolute;
top: 0;
left: 0;
width: 100%;
height: 100%;
background: #000;
opacity: .5;
filter: alpha(opacity = 50)
}

#layer {
position: absolute;
top: 30%;
left: 80%;
width: 460px;
height: 700px;
margin: -150px 0 0 -194px;
padding: 28px 28px 0 28px;
border: 2px solid #555;
background: #fff;
font-size: 12px;
font-family: Tahoma, Geneva, sans-serif;
color: #767676;
line-height: normal;
white-space: normal
}

</style>




<!-- 라이트박스 -->

<script type="text/javascript">

 function openContent(num){
  $('.mw_layer').addClass('open');

  $.ajax({
	  type:'post',
	  url: "challenge_content_popup",
	  data: ({num:num}),
	  async: true,
	 }).done(function(data) {
		 $('#layer').html(data);
	 });
  
  
//   $.ajax({
//     type:'post',
//     url:'approval_content',
//     data: ({num:num}),

//     success:function(data){
//   		$('#layer').html(data);
// 	}

//  });
 }

 function closeContent(){$('.mw_layer').removeClass('open');}
 

 jQuery(function($){
  var layerWindow = $('.mw_layer');
 

  // ESC Event

  $(document).keydown(function(event){

   if(event.keyCode != 27) return true;
   if (layerWindow.hasClass('open')) {
    layerWindow.removeClass('open');
   }
   return false;
  });

  // Hide Window

  layerWindow.find('>.bg').mousedown(function(event){
   layerWindow.removeClass('open');
   return false;
  });

  //$("tr:even").addClass("odd");
 });
 
 
// //Initialization
//  $('#my-element').datepicker([options])
//  // Access instance of plugin
//  $('#my-element').data('datepicker')

</script>







</head>
<body>
	<jsp:include page="../header.jsp" />

	
		
<div class="row" style="margin: 50px; min-width: 1050px;">


<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="register()">등록 관리</a>
      <a class="list-group-item list-group-item-action active" id="list-vote-list" data-toggle="list" href="#" role="tab" aria-controls="vote" onclick="vote()">투표중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="ongoing()">진행중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-reward-list" data-toggle="list" href="#" aria-controls="reward" onclick="reward()">상금 지급</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="end()">지난 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="state()">챌린지 현황</a>
    </div>
  </div>
  
  
  <div class="col-10"> 
    
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">...</div>
      
      <div class="tab-pane fade show active" id="list-vote" role="tabpanel" aria-labelledby="list-vote-list">
      	
 <table class="table" style="text-align: center;">
    <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="6">공개 챌린지 상세정보</th>
    </tr>
    
  </thead>
  <tbody>
			<tr>
				<td colspan="1">주제</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_title} (${challenge.challenge_category})</td>
			</tr>
			<tr>
				<td>인증 방법</td>
				<td colspan="3" style="vertical-align: middle;">인증 방법 : ${challenge.challenge_type}</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">내용</td>
				<td colspan="3" rowspan="2" height="200px;" style="vertical-align: middle;">${challenge.challenge_detail}</td>
			</tr>
			<tr></tr>
			
			<tr>
				<td colspan="4" align="right">
					<button class="btn btn-outline-primary" type="button" onclick="openContent('${challenge.challenge_num}');" >승인</button>
					<button class="btn btn-outline-primary" type="button" onclick="postpone();" >보류</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='register_challenge?page=<%= session.getAttribute("cpage") %>';" >목록보기</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"></td>
			</tr>
			
  </tbody>
  </table>
      
      

	<table class="table">
	
		<tr>
				<td colspan="1" style="vertical-align: middle;">- 사용자 댓글 -</td>
				<td colspan="4" width="200px"></td>
				<td colspan="2"></td>
				<td colspan="1" width="80px"></td>
				<td colspan="1" width="80px"></td>
		</tr>	
		
			<tr style="background-color: #007BFF;">
				<td colspan="1">닉네임</td>
				<td colspan="4">댓글내용</td>
				<td colspan="2">작성일</td>
				<td colspan="1">추천수</td>
				<td colspan="1" align="center">삭제</td>
			</tr>
			
			
<!-- 			댓글 DB 받아오면  싹 수정! -->
			<c:forEach items="${vote_comment}" var="cdto">
			<tr>
<%-- 				<c:if test="${content_view.id == cdto.cId}"> --%>
<%-- 					<th scope="row"colspan="1"><span class="badge badge-warning">작성자</span>  ${cdto.member_id}</th> --%>
<%-- 				</c:if> --%>
				<th scope="row"colspan="1">${cdto.member_id}</th>
				<td colspan="4">${cdto.comment_content}</td>
				<td colspan="2">${cdto.commment_date}</td>
				<td colspan="1">추천수</td>
			<!--삭제버튼 -->
				<td colspan="1" align="center">
					<button class="btn btn-outline-primary" style="font-size:6px; padding: 5px; padding-top:3px; padding-bottom: 3px ;" onclick="javascript:window.location='/zzzz/cdelete.do?bid=${content_view.bId}&cNo=${cdto.comment_num}'">X</button>
				</td>
				
			</tr>
			</c:forEach>
			
			<tr align="right">
			<td colspan="9"><a id="MOVE_TOP_BTN" href="#">TOP</a></td>
			</tr>
	</table>
      
      </div>
      <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">...</div>
      <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">...</div>
      <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">...</div>
    </div>
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
</body>
</html>