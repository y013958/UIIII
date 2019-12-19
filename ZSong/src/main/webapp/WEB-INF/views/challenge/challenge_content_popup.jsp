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
<title>Insert title here</title>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<link href="/dist/css/datepicker.min.css" rel="stylesheet" type="text/css">
<script src="/dist/js/datepicker.min.js"></script>

<!-- Include English language -->
<script src="/dist/js/i18n/datepicker.en.js"></script>


<script type="text/javascript">

// 	$("#title > option[@value='${challenge.challenge_category}']").attr("selected", "true");
// 	$("#title").val('${challenge.challenge_category}').attr("selected", "selected");
	
	function approval(num){
		
// 		var content = document.getElementById('content').value;

		var name="";
		var title="";
		var check="";
		var frequency="";
		var date = "";
		var money = "";
		var time = "";
		var statr_time = "";
		var end_time = "";
		var detail = "";
		var exp = "";		
		
		//카테고리
		title = $("select[name=title]").val();

		 //인증방법
	      $('input:checkbox[name=check]').each(function() {
	         if($(this).is(':checked'))
	        	 check += " "+$(this).val()

	      });
	      
		//인증빈도
		
		frequency = $("select[name=frequency]").val();

		name = document.getElementById('name').value;
		date = document.getElementById('date').value;
		money = document.getElementById('money').value;
		statr_time = document.getElementById('start_time').value;
		end_time = document.getElementById('end_time').value;

		time = statr_time + '-' + end_time;
		
		detail = document.getElementById('detail').value;
		exp = $("#exp").text();
		

		if(check.length == 0){
			alert("인증방법을 선택해주세요.");
		}else if(money.length == 0){
			alert("참가비를 입력하세요.");
		}else if(date.length == 0){
			alert("기간을 입력하세요.");
		}else if(time.length == 0){
			alert("인증시간을 입력하세요.");
		}else if(detail.length == 0){
			alert("규칙을 적어주세요.");
		}
		else{

// 	 	     alert(title+" "+check +" " + frequency + " " + date + " "+ money + " " + time + " " + detail);

			  $.ajax({
				    type:'post',
				    url:'approval_content',
				    data: ({num:num, name:name, title:title, check:check, frequency:frequency, date:date, money:money, time:time, detail:detail, exp:exp}),
				    success:function(data){
					    if(data == 0)
						    alert("승인 실패");
					    else
					    {
						    closeContent();
						    document.location.href="vote_public_challenge?page=1";
					    }
				    }

				 });
		}
		
	}
	
	function postpone(){

		
	}


</script>


<script type="text/javascript">

// 실시간 반영
$('#exp').html("7exp");

$(document).ready(function() {
	var exp="";
	var exp2=""		
	
	$('#frequency').on('change',function (e){
	    var frequency = $(this).val();
		
		if(frequency == '매일'){
			exp = 7;
		}else if(frequency == '주중'){
			exp = 5;
		}else if(frequency == '월수금'){
			exp = 3;
		}else if(frequency == '주말'){
			exp = 2;
		}


	    if(exp2.length == 0){
	    	$('#exp').html(exp+"exp");    //글자수 실시간 카운팅
	    }else{
	    	$('#exp').html(exp2*exp+"exp");    //글자수 실시간 카운팅
		}
	});


	$("#date").on('propertychange change keyup paste input', function() {
	    var dateinput = $(this).val();
	    var DateArr = dateinput.split(' - ');

	     var startDateArr = DateArr[0].split('/');
	     var endDateArr = DateArr[1].split('/');
	     
	     var startDateCompare = new Date(startDateArr[0], Number(startDateArr[1])-1, startDateArr[2]);
	     var endDateCompare = new Date(endDateArr[0], Number(endDateArr[1])-1, endDateArr[2]);

	     var day = ((endDateCompare.getTime() - startDateCompare.getTime()) /(1000*60*60*24)) / 7 ;
	     exp2 = day;
	     
		    if(exp.length == 0){
		    	$('#exp').html(day+"exp");    //글자수 실시간 카운팅
		    }else{
		    	$('#exp').html(exp*day+"exp");    //글자수 실시간 카운팅
			}
	});
});


</script>


</head>
<body>


<table class="table" style="text-align: center; width: 400px; text-align: left;">
	<thead class="table" style="background-color: #007BFF;">
		<tr style="color: white;">
      		<th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="3">추가 정보 입력</th>
    	</tr>    
 	</thead>
 	<tbody>
			<tr style="background-color: #E0F1FF">
				<td colspan="1" width="130px;">제목</td>
				<td colspan="3" style="vertical-align: middle;" width="280px;">
					<input type="text" style="resize: none;" id="name" value="${challenge.challenge_title}"/>
				</td>
			</tr>
			<tr>
				<td>카테고리</td>
				<td colspan="3" style="vertical-align: middle;">		
					<select class="custom-select" name="title" id="title" style="width: 120px; height: 38px">
			       		   <option value="역량">역량</option>
					       <option value="건강">건강</option>
					       <option value="관계">관계</option>
					       <option value="자산">자산</option>	
					       <option value="생활">생활</option>	
					       <option value="취미">취미</option>	
				    </select>
				
				</td>
			</tr>
			<tr style="background-color: #E0F1FF">
				<td>인증방법</td>
				<td colspan="3" style="vertical-align: middle;">
					<input type="checkbox" name="check" value='카메라'/>카메라    
					<input type="checkbox" name="check" value='갤러리'/>갤러리    
					<input type="checkbox" name="check" value='녹음'/>음성    
					<input type="checkbox" name="check" value='지도'/>지도    
					<input type="checkbox" name="check" value='영화'/>영화
				</td>
			</tr>
			<tr>
				<td>인증빈도</td>
				<td colspan="3" style="vertical-align: middle;">
					<select class="custom-select" name="frequency" style="width: 120px; height: 38px" id="frequency">
			       			<option value="매일">매일</option>
					       <option value="주중">주중</option>
					       <option value="월수금">월수금</option>
					       <option value="주말">주말</option>	
				    </select>
				</td>
			</tr>
			<tr style="background-color: #E0F1FF">
				<td>기간</td>
				<td colspan="3" style="vertical-align: middle;">
					<input type="text"
					    data-range="true"
					    data-multiple-dates-separator=" - "
					    data-language="en"
					    class="datepicker-here"
					    placeholder="클릭하세요."
					    style="width: 200px;"
					    id="date"
					    name="date"/>
				</td>
			</tr>
			<tr>
				<td>참가비</td>
				<td colspan="3" style="vertical-align: middle;">
					<input type="text" id="money" style="width: 25px; text-align: center;"/>만원
<!-- 					<textarea rows="1" cols="5" placeholder="" style="resize: none;" ></textarea> -->
				</td>
			</tr>
			<tr style="background-color: #E0F1FF">
				<td>인증시간</td>
				<td colspan="3" style="vertical-align: middle;">
					<input type="text" class="timepicker" id="start_time" name="timepicker" placeholder="00:00" style="width: 50px; text-align: center;"/>
					 ~ 
					<input type="text" class="timepicker" id="end_time" name="timepicker" placeholder="00:00" style="width: 50px; text-align: center;"/>
       			</td>
			</tr>
			<tr>
				<td>챌린지 규칙</td>
				<td colspan="3" style="vertical-align: middle;">
					<textarea rows="4" cols="30" style="resize: none;" id="detail">${challenge.challenge_detail}</textarea>
				</td>
			</tr>
			<tr style="background-color: #E0F1FF">
				<td>메인 이미지</td>
				<td colspan="3" style="vertical-align: middle;">
					<input type="file" class="file" name="boardFile">
				</td>
			</tr>
			<tr>
				<td>경험치</td>
				<td colspan="3" style="vertical-align: middle;">
					<label id="exp" name="exp"></label>
				</td>
			</tr>

			
			<tr>
				<td colspan="4" align="right" style="background-color: #E0F1FF">
					<button class="btn btn-outline-primary" type="button" onclick="approval('${challenge.challenge_num}');" >승인</button>
					<button class="btn btn-outline-primary" type="button" onclick="closeContent();" >취소</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"></td>
			</tr>
			
 	</tbody>
</table>


<script type="text/javascript">
// 	$("#title").val("${challenge.challenge_category}").prop("selected", true);
	$("#title").val('${challenge.challenge_category}').attr("selected", "selected");
</script>


</body>
</html>