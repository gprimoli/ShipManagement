package controller.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(
        name = "Logout",
        description = "Questa servlet si occupa del logout dell'utente",
        urlPatterns = "/logout"
)

public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        s.invalidate();
        resp.sendRedirect("index.jsp");
    }
}
