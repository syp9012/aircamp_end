<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
	<script>
		function check() {
			alert("수정이 완료되었습니다.");
		}
	</script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="notice-page wrap">
		<h2>글수정</h2>
		<form method="post" action="/notice/update" onSubmit="check()">
		  <input type="hidden" name="no_no" value="${notice.no_no}" />
			<table>
				<tr>
					<th>제목</th>
					<td><input type="text" name="no_subject" id="no_subject"  value="${notice.no_subject}" required /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="no_content" id="no_content" required>${notice.no_content}</textarea></td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="수정하기">
				<input type="button" value="목록" onclick="location='/notice/list?page=${search.page}&keyword=${search.keyword}'">
			</div>
		</form>
	</main>
	<%@ include file="../header.jsp"%>
</body>
</html>