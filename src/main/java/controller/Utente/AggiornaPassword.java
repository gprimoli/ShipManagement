package controller.Utente;

import model.Utente.Utente;
import model.Utente.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet(
        urlPatterns = "/aggiornapassword"
)
public class AggiornaPassword extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        String password = req.getParameter("password");
        String confermapassword = req.getParameter("confermapassword");

        if(password.compareTo(confermapassword) == 0){
            UtenteDAO.doChangePassword(u, password);
            req.setAttribute("notifica", "Password modificata con successo!");
            req.setAttribute("tipoNotifica", "success");
        }else {
            req.setAttribute("notifica", "Ops! Abbiamo riscontrato problemi con la password, non rispetta le regole");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher("profilo.jsp").forward(req, resp);
    }

}
