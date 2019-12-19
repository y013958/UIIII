<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

$.ajax({

    type:'post',
    url:'${url}',

    success:function(data){
	  	$('#content').html(data);
    }

});



</script>


</head>
<body>

<div id="content">
	<h4>정지 사유</h4>
	<textarea rows="20" cols="50" id="content" ></textarea>
<!-- 	<input type="button" class="btn btn-dark" style="padding: .2rem .45rem; font-size: 0.8rem;" value="처리" onclick="ban_regist()"/> -->
<!-- 	<input type="button" class="btn btn-dark" style="padding: .2rem .45rem; font-size: 0.8rem;" value="취소" onclick="closeContent()"/> -->
	<a href="#" onclick="closeContent()">목록으로</a> 
</div>

</body>
</html>