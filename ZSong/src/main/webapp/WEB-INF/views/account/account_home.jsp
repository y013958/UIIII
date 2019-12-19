<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.study.springboot.dto.JoinDateDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "account_home");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
	
	ArrayList<JoinDateDto> joindate = new ArrayList<>();
	
	joindate = (ArrayList)request.getAttribute("joindate");

%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script> -->

    
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

</script>

 
</head>
<body>
	<jsp:include page="../header.jsp" />

	
		
<div class="row" style="margin: 50px; min-width: 1050px;">


<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action active" id="list-ahome-list" data-toggle="list" href="#" role="tab" aria-controls="ahome" onclick="home()">회원 현황</a>
      <a class="list-group-item list-group-item-action" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="member()">회원 계정</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="manager()">관리자 계정</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="ban()">정지 계정</a>
      <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="withdraw()">탈퇴 계정</a>
    </div>
  </div>
  <div class="col-10"> 
    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="list-ahome" role="tabpanel" aria-labelledby="list-ahome-list">   		
    	
    	<h4>사용자 현황 그래프랑 오늘 접속횟수 이런거</h4>
 
	<div id="Line_Controls_Chart">
      <!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea" style="padding:0px 20px 0px 0px;"></div>
      <!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea" style="padding:0px 20px 0px 0px;"></div>
	</div>
    		
    		
    	</div>
    
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">...</div>
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
<div id="layer">
<!-- 	<h4>정지 사유</h4> -->
<!-- 	<textarea rows="20" cols="50"></textarea> -->
<!-- 	<input type="button" value="처리"/> -->
</div>
</div>




<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>



  <script type="text/javascript">
 
  
  var chartDrowFun = {
 
    chartDrow : function(){

        var chartData = '';
 
        //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
        var chartDateformat     = 'yyyy년MM월dd일';
        //라인차트의 라인 수
        var chartLineCount    = 10;
        //컨트롤러 바 차트의 라인 수
        var controlLineCount    = 10;
 
 
        function drawDashboard() {
 
          var data = new google.visualization.DataTable();
          //그래프에 표시할 컬럼 추가
          data.addColumn('datetime' , '날짜');
          data.addColumn('number'   , '회원가입자 수');
          data.addColumn('number'   , '전체 회원수');
          data.addColumn('number'   , '카카오계정');
          data.addColumn('number'   , '일반 계정');
  
 
          //그래프에 표시할 데이터
          var dataRow = [];

          <% for(int i=0 ; i < joindate.size() ; i++){
        	  String str = joindate.get(i).getMember_joindate().toString();
          %>
          	
          	 var y = <%=str.substring(0, 4)%>
 			 var m = <%=str.substring(5, 7)%>
 			 var d = <%=str.substring(8, 10)%>

			 var tcount =  <%=joindate.get(i).getTcount()%>
			 var kcount =  <%=joindate.get(i).getKcount()%>	
 			 
 			 var count = <%=joindate.get(i).getCount()%>
          	 var total = <%=joindate.get(i).getSum()%>

 			dataRow = [new Date(y, m-1, d , '10'), count, total, kcount, tcount];


            data.addRow(dataRow);
          <%}%>

 
            var chart = new google.visualization.ChartWrapper({
              chartType   : 'LineChart',
              containerId : 'lineChartArea', //라인 차트 생성할 영역
              options     : {
                              isStacked   : 'percent',
                              focusTarget : 'category',
                              height          : 500,
                              width              : '100%',
                              legend          : { position: "top", textStyle: {fontSize: 13}},
                              pointSize        : 5,
                              tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
                              hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
                                                                  years : {format: ['yyyy년']},
                                                                  months: {format: ['MM월']},
                                                                  days  : {format: ['dd일']},
                                                                  hours : {format: ['HH시']}}
                                                                },textStyle: {fontSize:12}},
                vAxis              : {minValue: 20,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
                animation        : {startup: true,duration: 1000,easing: 'in' },
                annotations    : {pattern: chartDateformat,
                                textStyle: {
                                fontSize: 15,
                                bold: true,
                                italic: true,
                                color: '#871b47',
                                auraColor: '#d799ae',
                                opacity: 0.8,
                                pattern: chartDateformat
                              }
                            }
              }
            });
 
            var control = new google.visualization.ControlWrapper({
              controlType: 'ChartRangeFilter',
              containerId: 'controlsArea',  //control bar를 생성할 영역
              options: {
                  ui:{
                        chartType: 'LineChart',
                        chartOptions: {
                        chartArea: {'width': '60%','height' : 80},
                          hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
                            gridlines:{count:controlLineCount,units: {
                                  years : {format: ['yyyy년']},
                                  months: {format: ['MM월']},
                                  days  : {format: ['dd일']},
                                  hours : {format: ['HH시']}}
                            }}
                        }
                  },
                    filterColumnIndex: 0
                }
            });
 
            var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
            date_formatter.format(data, 0);
 
            var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
            window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
            dashboard.bind([control], [chart]);
            dashboard.draw(data);
 
        }
          google.charts.setOnLoadCallback(drawDashboard);
 
      }
    }
 
$(document).ready(function(){
  google.charts.load('current', {'packages':['line','controls']});
  chartDrowFun.chartDrow(); //chartDrow() 실행
});
  </script>

</html>

