import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASS = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("studentName");
        String id = request.getParameter("studentId");
        String subject = request.getParameter("subject");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql = "INSERT INTO attendance (student_name, student_id, subject, date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);
            ps.setString(3, subject);
            ps.setDate(4, Date.valueOf(date));
            ps.setString(5, status);

            int rowsInserted = ps.executeUpdate();

            out.println("<html><body>");
            if (rowsInserted > 0) {
                out.println("<h3>Attendance saved successfully!</h3>");
            } else {
                out.println("<h3>Failed to save attendance.</h3>");
            }
            out.println("<a href='attendance.jsp'>Go Back</a>");
            out.println("</body></html>");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
