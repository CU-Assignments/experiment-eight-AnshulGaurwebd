import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASS = "your_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchId = request.getParameter("searchId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            out.println("<html><body>");
            out.println("<h2>Employee List</h2>");
            
            if (searchId != null && !searchId.isEmpty()) {
                // Search employee by ID
                String sql = "SELECT * FROM employees WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(searchId));
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    out.println("<h3>Search Result:</h3>");
                    out.println("<p>ID: " + rs.getInt("id") + "</p>");
                    out.println("<p>Name: " + rs.getString("name") + "</p>");
                    out.println("<p>Department: " + rs.getString("department") + "</p>");
                    out.println("<p>Email: " + rs.getString("email") + "</p>");
                } else {
                    out.println("<p>No employee found with ID: " + searchId + "</p>");
                }

            } else {
                // Show all employees
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Department</th><th>Email</th></tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("</body></html>");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
