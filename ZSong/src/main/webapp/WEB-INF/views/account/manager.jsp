<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "manager_page");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
	function home(){
		javascript:window.location='account_home';
	}
	function member(){
		javascript:window.location='member?page=1';
	}
	function manager(){
		javascript:window.location='manager?page=1';
	}
	function ban(){
		javascript:window.location='banMember?page=1';
	}
	function withdraw(){
		javascript:window.location='withdraw?page=1';
	}

	function grade_change(){
		 var data="";
		 var grade="";
	      $('input:checkbox[name=check]').each(function() {
	         if($(this).is(':checked'))
	            data += " "+$(this).val()

	      });
	      
	      grade = $("select[name=grade]").val();

	      if(data.length >= 1 && grade.length >= 1)
		  {

	    	  $.ajax({
				    type:'post',
				    url:'grade_change',
				    data: ({id:data, grade:grade}),

				    success:function(data){
					    if(data == 0)
						    alert("권한 변경 실패.");
					    else
					    {
					    	alert("권한이 변경 되었습니다.");
						    document.location.href="manager?page=1";
					    }
					    
				    }

				 });
		}
    	  
//     	  alert(data + grade);

	      $("input[name=check]").prop("checked",false);
	}


	function ban_regist(id){
		var content = document.getElementById('content').value;
		
	}
	
</script>

</head>
<body>
	<jsp:include page="../header.jsp" />


<!-- <div class="row" style="margin: 50px; min-width: 1050px;"> -->
<div class="row" style="margin: 50px;">

<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action" id="list-ahome-list" data-toggle="list" href="#" role="tab" aria-controls="ahome" onclick="home()">회원 현황</a>
      <a class="list-group-item list-group-item-action" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="member()">회원 계정</a>
      <a class="list-group-item list-group-item-action active" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="manager()">관리자 계정</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="ban()">정지 계정</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="withdraw()">탈퇴 계정</a>
    </div>
  </div>
  <div class="col-10"> 
    <div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade" id="list-ahome" role="tabpanel" aria-labelledby="list-ahome-list"></div>
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
      </div>
      
      <div class="tab-pane fade show active" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
      	
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col">선택</th>
      <th scope="col">아이디</th>
      <th scope="col">비밀번호</th>
      <th scope="col">이름</th>
      <th scope="col">권한</th>
      <th scope="col">최종접속일</th>
    </tr>	
  </thead>
  <tbody>
    		<c:forEach items="${manager}" var="dto">
			<tr>
				<td><input type="checkbox" name="check" value='${dto.manager_id}' /></td>
				<th scope="row">${dto.manager_id}
				<td>${dto.manager_pw}</td>
				<td>${dto.manager_name}</td>
				<td>${dto.manager_grade}</td>
				<td>${dto.manager_access}</td>
			</tr>
			</c:forEach>

			
			<tr>
				
				<td colspan="3" style="text-align: left;">
					<select class="custom-select" name="grade" style="width: 60px; height: 38px">
			       		<option value="A" selected>A</option>
					       <option value="B">B</option>
					       <option value="C">C</option>	
				    </select>
			        
			        <input type="button" class="btn btn-primary" onclick="grade_change()" value="수정" style="vertical-align: top;">
				</td>
				
				<form action="member.do" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>검색옵션</option>
							<option value="아이디">아이디</option>
							<option value="이름">이름</option>
						</select>
						</td>
						<td colspan="2">
						<div class="input-group">
							<input type="text" class="form-control" name="findtext" id="findtext">
							<button onclick="find()">검색</button>
						</div>	
						</td>
					
				</form>
<!-- 				<td colspan="1"></td>	 -->
<!-- 				글작성 로그인 상태에서 가능 조건 -->
<!-- 				<td colspan="2" style="text-align: right;"> -->
<!-- 					<button onclick="login_check()">글작성</button> -->
<!-- 				</td> -->
			</tr>
			
			
			
			<tr>
				<td colspan="6" aria-label="Page navigation example" align="center">
				<div class="row align-items-center">
				<ul class="pagination">
				    <li class="page-item">
					   	<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link" href="#">[ &lt;&lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/manager?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/manager?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/manager?page=${fEach}">[ ${fEach} ]</a>
							</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
				    
				   

				    <li class="page-item" style="display: inline; float: left;">
	    				<c:choose>
						<c:when test="${(page.curPage +1) >page.totalPage }">
							<a class="page-link"href="#">[ &gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/manager?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/manager?page=${page.totalPage}">[ &gt;&gt; ]</a>
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