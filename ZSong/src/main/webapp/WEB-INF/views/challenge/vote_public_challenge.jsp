<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "register_challenge");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>

    <style>

/*     	tbody{ */
/*     		background-color: #E9ECEF; */
/*     	}    	 */
    	
    	ul{
    		margin: auto;
    	}
    	
    	td{
    		text-align: center;
    	}

		.custom-select {
			width: 110px;
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
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">챌린지 번호</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="200px;">카테고리</th>
      <th scope="col" colspan="2" style="vertical-align:middle; font-size: 10pt;" width="330px;">제목</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">추천수</th>
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">인증빈도</th> -->
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">시작일</th> -->
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">종료일</th> -->
      <th scope="col" colspan="1" style="vertical-align:middle; font-size: 10pt;" width="200px;">신청날짜</th>
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">체크박스</th> -->
    </tr>
    
  </thead>

  <tbody>
    		<c:forEach items="${challenge}" var="dto">
			
			<tr>
				<th scope="row" style="vertical-align: middle;">${dto.challenge_num}			    
				<td style="vertical-align: middle;">${dto.challenge_category}</td>
				<td style="vertical-align: middle;" colspan="2"><a href="/challenge_vote_content?num=${dto.challenge_num}">${dto.challenge_title}</a></td>
				<td style="vertical-align: middle;">
					${dto.count}
				</td>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_frequency}</td> --%>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_start}</td> --%>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_end}</td> --%>
				<td style="vertical-align: middle;">${dto.challenge_date}</td>
			</tr>

			</c:forEach>

			
			<tr>
				
					<td colspan="1">
					</td>
					
				<form action="/register_challenge?page=${page.curPage}" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>옵션</option>
							<option value="카테고리">카테고리</option>
							<option value="제목">제목</option>
							<option value="날짜">날짜</option>
							<option value="구분">구분</option>
						</select>
						</td>
						<td colspan="1">
						<div class="input-group">
							<input type="text" class="form-control" name="findtext" id="findtext">
							<button type="submit">검색</button>
						</div>	
						</td>
				</form>
				<td colspan="3"></td>	
<!-- 				글작성 로그인 상태에서 가능 조건 -->
<!-- 				<td colspan="2" style="text-align: right;"> -->
<!-- 					<button onclick="login_check()">글작성</button> -->
<!-- 				</td> -->
			</tr>
			
			
			
			<tr>
				<td colspan="5" aria-label="Page navigation example" align="center">
				<div class="row align-items-center">
				<ul class="pagination">
				    <li class="page-item">
					   	<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link" href="#">[ &lt;&lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/register_challenge?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/register_challenge?page=${page.curPage - 1}">[ &lt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>				    
				    
				 
				    
					    <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
					    	<li class="page-item" >
							<c:choose>
							<c:when test="${page.curPage == fEach}">
								<a class="page-link"href="#">[ ${fEach} ]</a>
							</c:when>
							<c:otherwise>
								<a class="page-link"href="/register_challenge?page=${fEach}">[ ${fEach} ]</a>
							</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
				    
				   

				    <li class="page-item" style="display: inline; float: left;">
	    				<c:choose>
						<c:when test="${(page.curPage +1) > page.totalPage }">
							<a class="page-link"href="#">[ &gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/register_challenge?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/register_challenge?page=${page.totalPage}">[ &gt;&gt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>


				  </ul>
				
				</div>
			
				</td>
			</tr>
  </tbody>
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


<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>