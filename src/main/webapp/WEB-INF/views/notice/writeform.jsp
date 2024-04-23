<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="notice-page wrap">
		<h2>글작성</h2>
		<form method="post" action="/notice/write">
			<table>
				<tr>
					<th>제목</th>
					<td><input type="text" name="no_subject" id="no_subject" required /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="no_content" id="no_content" required></textarea></td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="작성하기">
				<input type="button" value="목록" onclick="location='/notice/list?page=1&keyword='">
			</div>
		</form>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>