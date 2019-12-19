<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
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
</script>




</head>
<body>
	<jsp:include page="../header.jsp" />

	
		
<div class="row" style="margin: 50px; min-width: 1050px;">


<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="register()">등록 관리</a>
      <a class="list-group-item list-group-item-action" id="list-vote-list" data-toggle="list" href="#" role="tab" aria-controls="vote" onclick="vote()">투표중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="ongoing()">진행중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-reward-list" data-toggle="list" href="#" aria-controls="reward" onclick="reward()">상금 지급</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="end()">지난 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="state()">챌린지 현황</a>
    </div>
  </div>
  
  
  <div class="col-10"> 
    
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
      	
 <table class="table" style="text-align: center;">
    <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="6">비공개 챌린지 상세정보</th>
    </tr>
    
  </thead>
  <tbody>
  
<!-- 제목(카테고리) dd-->
<!-- 인증방법 -ㅇㅇ->
<!-- 인증빈도 ㅇㅇ-->
<!-- 시작일 종료일ㅇㅇ -->
<!-- 참가비 -->
<!-- 인증시간 ㅇㅇ-->
<!-- 챌린지 규칙 ㅇㅇ-->
<!-- 이미지 -->
<!-- 챌린지 경험치 -->
<!-- 등록 날짜 -->
<!-- 신청자 -->
  
			<tr>
<%-- 			${dto.challenge_first_image} --%>
				<td rowspan="8" width="350px;" height="400px" style="border-right: 1px solid #dee2e6;"><img alt="" src="img/book.jpg" width="100%" height="100%"></td>
				<td colspan="1" style="vertical-align: middle; border-right: 1px solid #dee2e6;">주제(카테고리)</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_title} (${challenge.challenge_category})</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; border-right: 1px solid #dee2e6;">기간</td>
				<td colspan="3" style="vertical-align: middle;"> ${challenge.challenge_start} ~ ${challenge.challenge_end}</td>
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
				<td colspan="4"></td>
			</tr>
			
  </tbody>
  </table>

      	
     
      </div>
      <div class="tab-pane fade" id="list-vote" role="tabpanel" aria-labelledby="list-vote-list">...</div>
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