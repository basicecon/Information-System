<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>

<%
    pageContext.setAttribute("moviedbName", "Purdue");
    
    String display = request.getParameter("display");
    if ( display == null ) {
    	display = "";
    }
    pageContext.setAttribute("display", display);
%>

	<p>Please enter a command in the text area below.</p>
	<p>${display}</p>

        

    <form action="/command" method="post">
      <div><textarea name="command" rows="3" cols="60"></textarea></div>
      <div><input type="submit" value="Send command" /></div>
      <input type="hidden" name="moviedbName" value="$moviedbName$"/>
    </form>

  </body>
</html>