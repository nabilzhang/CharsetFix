<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="me.nabil.app.charset.encoding.fix.CharsetDecoder;"%>
<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
</head>
<h1>乱码转换</h1>
<input type="text" name="name" id="name-text">
<button onclick="tijiao();">转换</button>
<p><span>输入乱码字符串，点击提交，不要包含标点符号，特殊符号</span></p>

<form id="name-form" action="" method="get">
	<input type="hidden" name="name" id="name">
</form>
<script>
	function tijiao() {
		var xx = document.getElementById("name-text").value;
		xx = encodeURIComponent(xx);
		document.getElementById("name").value = xx;
		document.getElementById("name-form").submit();

	}
</script>

<%
	if (request.getParameter("name") != null) {
		String interactName = request.getParameter("name");
		interactName = URLDecoder.decode(interactName, "utf-8");
		System.out.print(interactName);
		Map<String, String> map = CharsetDecoder.decode(interactName);
		Iterator<Entry<String, String>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String outstr = "<p>" + entry.getKey() + " --- "
					+ entry.getValue() + "</p>";
			out.println(outstr);
			System.out.println(outstr);
		}
	}
%>
</html>