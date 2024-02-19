
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author islam
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("login".equals(request.getParameter("act"))) {

            try (Connection c = DAO.getConnection()) {
                PreparedStatement st = c.prepareStatement("SELECT * FROM users WHERE umail=? AND upwd=?;");
                st.setString(1, request.getParameter("umail"));
                st.setString(2, request.getParameter("upwd"));
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    System.out.println("ENTERED IF STATEMENT");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("userPanel.jsp");
                    HttpSession session = request.getSession(true);
                    session.setAttribute("uname", rs.getString("uname"));
                    session.setAttribute("upwd", rs.getString("upwd"));
                    session.setAttribute("umail", rs.getString("umail"));
                    session.setAttribute("uphone", rs.getString("uphone"));
                    dispatcher.forward(request, response);
                } else {
                    System.out.println("ENTERED ELSE STATEMENT");
                    PrintWriter out = response.getWriter();
                    out.print("<h2>Sorry UserName or Password Error OR not registered!</h2>");
                    RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                    response.setContentType("text/html");
                    rd.include(request, response);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ("signup".equals(request.getParameter("act"))) {
            if (!request.getParameter("upwd").equals(request.getParameter("upwd2"))) {
                PrintWriter out = response.getWriter();
                out.print("<h2>Passwords are not same Error!</h2>");
                RequestDispatcher rd = request.getRequestDispatcher("/signup.html");
                response.setContentType("text/html");
                rd.include(request, response);
                return;
            }

            try (Connection c = DAO.getConnection()) {
                PreparedStatement st = c.prepareStatement("INSERT INTO users(`uname`,`umail`,`upwd`,`uphone`) values(?,?,?,?);");
                st.setString(1, request.getParameter("uname"));
                st.setString(2, request.getParameter("umail"));
                st.setString(3, request.getParameter("upwd"));
                st.setString(4, request.getParameter("uphone"));
                int affectedRows = st.executeUpdate();
                if (affectedRows > 0) {
                    PrintWriter out = response.getWriter();
                    out.print("<h2>Account created Successfully!</h2>");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
                    dispatcher.forward(request, response);
                } else {
                    PrintWriter out = response.getWriter();
                    out.print("<h2>Something went wrong!</h2>");
                    RequestDispatcher rd = request.getRequestDispatcher("/signup.html");
                    response.setContentType("text/html");
                    rd.include(request, response);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ("logout".equals(request.getParameter("act"))) {
            request.getSession(false).invalidate();
            PrintWriter out = response.getWriter();
            out.print("<h2>Something went wrong!</h2>");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.forward(request, response);
        }
    }

}
