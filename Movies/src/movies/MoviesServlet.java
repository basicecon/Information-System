package movies;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MoviesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.sendRedirect("movies.jsp?moviedbName=Purdue");
	}
}
