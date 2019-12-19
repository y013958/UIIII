<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "cash_mileage");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입출금내역</title>

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
	function waitQnA(){
		javascript:window.location='waitQnA?page=1';
	}
	function EndQnA(){
		javascript:window.location='EndQnA?page=1';
	}
	function withdrawal_request(){
		javascript:window.location='withdrawal_request?page=1';
	}

	function QnAsubmit(num){
		var content = document.getElementById('content').value;
		var id = '<%=id%>';
		
		  $.ajax({
			    type:'post',
			    url:'/QnAsubmit',
			    data: ({num:num, content:content, id:id}),

			    success:function(data){
				    if(data == 0)
					    alert("내용을 입력하세요.");
				    else
				    {
					    document.location.href="waitQnA?page=1";
				    }
				    
			    }

			 });
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
        $(".menu>a").click(function(){
            var submenu = $(this).next("ul");
 
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( submenu.is(":visible") ){
                submenu.slideUp();
            }else{
                submenu.slideDown();
            }
        });
    });
</script>
 


</head>
<body>
	<jsp:include page="../header.jsp" />

	
		
<div class="row" style="margin: 50px; min-width: 1050px;">

<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="EndQnA()">Q&A</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="EndQnA()">지난 Q&A</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="withdrawal_request()">신고내역</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="#">???</a>
    </div>
  </div>
  <div class="col-10"> 
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
      			
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="80px;">번호</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="100px;">아이디</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="450px;">제목</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="100px;">날짜</th>
    </tr>
    
  </thead>

  <tbody>
    		<c:forEach items="${QnAList}" var="dto">
			
			<tr>
				<th scope="row" style="vertical-align: middle;">${dto.question_num}			    
				<td style="vertical-align: middle;">${dto.member_id}</td>
				<td style="vertical-align: middle;">
				
					<li class="menu"  style="list-style: none; align-content: center;">
			            <a href="#">${dto.question_title}</a>
			            <ul class="hide" style="list-style: none; text-align: left; padding:0; margin: 0;">
			                <li style="margin-bottom: 0;">
			                	<table style="vertical-align: middle; width: 100%;">
			                		<c:if test="${dto.question_Picture.length != null}">
				                		<tr style="width: 100%;">
				                			<td width="20%" style="background-color: #007BFF; vertical-align: middle;">내용</td>
				                			<td width="80%" style="text-align: left;">
				                				${dto.question_Content}
				                			</td>
				                		</tr>
			                		</c:if>
			                		
			                		<c:if test="${dto.question_Picture.length == null}">
				                		<tr style="width: 100%;">
					                		<td width="10%" style="background-color: #007BFF;  vertical-align: middle;">내용</td>
				                			<td width="60%" style="text-align: left;">
				                				${dto.question_Content}
				                			</td>
				                			<td width="40%" style="text-align: right;">
				                				<img alt="" width="200px;" height="150px;" src="img/book.jpg">
<!-- 				                				이미지 경로로 수정 -->
				                			</td>
				                		</tr>
			                		</c:if>
			                	</table>	
			                </li>
			                <li>
			                	<table style="vertical-align: middle; width: 100%;">
			                		<tr>
			                			<td style="width: 85%;">
			                				<textarea rows="3" cols="100%" style="resize: none;" id="content"></textarea>
			                			</td>
			                			<td style="width: 15%;">
			                				<input class="btn btn-primary" style="width: 100%; height: 78px;" type="button" value="등록" onclick="QnAsubmit('${dto.question_num}')">
			                			</td>
			                		</tr>
			                	</table>			                				                
			                </li>
			            </ul>
			        </li>
			        
				</td>
				<td style="vertical-align: middle;" width="250px;">${dto.question_date}</td>
			</tr>

			</c:forEach>

			
			<tr>
				
					<td colspan="1">
					</td>
					
				<form action="member?page=${page.curPage}" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>옵션</option>
							<option value="아이디">아이디</option>
							<option value="구분">구분</option>
							<option value="날짜">날짜</option>
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
							<a class="page-link" href="/member?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/member?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/member?page=${fEach}">[ ${fEach} ]</a>
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
							<a class="page-link" href="/member?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/member?page=${page.totalPage}">[ &gt;&gt; ]</a>
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