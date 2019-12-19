<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<style type="text/css">
    #tblBackground {
        /*
         *  화면에 보여야 할 Table의 가로크기를 잡아줍니다.
         */
        width: 790px;
        table-layout: fixed;
    }
    #divHeadScroll {
        /*
         *  안의 내용이 790보다 길게 적용이 되므로 overflow-x, overflow-y를 hidden을 걸어줘서 설정한 가로너비만큼만 화면에 보이도록 잡아줍니다.
         */
        width: 100%;
        overflow-x: hidden;
        overflow-y: hidden;
        border-top: 2px solid #d0d0d0;
    }
    #tblHead {
        /*
         *  Table의 필드명의 너비를 여기에서 지정합니다.
         *  필드명을 보여줄 테이블은 body보다 하나의 td를 더 가지며 마지막 td는 아무것도 입력하지 않고 크기만 잡아줍니다.
         *  이렇게 하는 이유는 데이터를 보여주는 테이블은 세로 스크롤의 가로두께만큼 더 이동하기 때문입니다.
         */
        table-layout: fixed;
        background-color: #efefef;
        padding-top: 4px;
        width:1117px;
        height: 30px;
        border-collapse: collapse;
    }
    #divBodyScroll {
        /*
         *  overflow-x, overflow-y는 scroll로 지정하고,
         *  height는 테이블의 데이터가 나올 기본 크기를 잡아줍니다.
         *  세로길이를 잡아주지 않으면 overflow-y가 걸리지 않습니다.
         */
        width: 100%;
        height: 350px;
        overflow-x: scroll;
        overflow-y: scroll;
        border-top: 1px solid #d0d0d0;
    }
    #tblBody {
        /*
         * tblBody의 데이터는 필요한 열의 수만큼만 만드시고 헤더와 col의 width는 동일해야 합니다.
         */
        width: 1100px;
        table-layout: fixed;
        height: 30px;
        border-collapse: collapse;
    }
 
    /* 테이블 스타일용 */
    .title {
        text-align: center;
        font-weight: bold;
    }
    .content {
        border-bottom: 1px solid #efefef;
        padding: 10px 10px 10px 10px;
        text-align: center;               
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
         
    }
</style>

</head>
<body>




<table id="tblBackground" cellspacing="0">
    <tr>
        <td>
            <div id="divHeadScroll">
                <table id="tblHead" border="0" >
                    <colgroup>
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:100px;" />
                        <col style="width:17px;" />
                    </colgroup>
                    <tr>
                        <td class="title">학번</td>
                        <td class="title">이름</td>
                        <td class="title">소속대학</td>
                        <td class="title">학과</td>
                        <td class="title">1학년</td>
                        <td class="title">2학년</td>
                        <td class="title">3학년</td>
                        <td class="title">4학년</td>
                        <td class="title">석사</td>
                        <td class="title">박사</td>
                        <td class="title">비고</td>
                        <td></td>
                    </tr>
                </table>
            </div>
            <div id="divBodyScroll">
                <table id="tblBody" border="0">
                    <colgroup>
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                        <col style="width:100px;" class="right_border" />
                    </colgroup>
                    <tr>
                        <td class="content right_border" >20062022</td>
                        <td class="content right_border" >홍길동</td>
                        <td class="content right_border" >공학대학</td>
                        <td class="content right_border" >컴퓨터공학</td>
                        <td class="content right_border" >A / B</td>
                        <td class="content right_border" >A+ / A</td>
                        <td class="content right_border" >B / B</td>
                        <td class="content right_border" >B / A</td>
                        <td class="content right_border" >없음</td>
                        <td class="content right_border" >없음</td>
                        <td class="content right_border" >없음</td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<input type="button" id="btnCheck" value="Click" />



</body>
</html>