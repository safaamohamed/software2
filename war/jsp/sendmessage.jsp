<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/social/sendmessageconn" method="post">
  Name : <input type="text" name="uname" /> <br>
  rName : <input type="text" name="rname" /> <br>
message: <input type="text" name="message" /> <br>

  <input type="submit" value="send">
  
  </form>
</body>
</html>