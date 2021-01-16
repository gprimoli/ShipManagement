package controller.Utente;

import model.CompagniaBroker.CompagniaBrokerDAO;
import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@WebServlet(
        urlPatterns = "/aggiornautente"
)
public class AggiornaUtente extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        String email = req.getParameter("email");
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String dataDiNascita = req.getParameter("dataDiNascita");
        String luogoDiNascita = req.getParameter("luogoDiNascita");
        try {
            Utente nu = Utente.builder()
                    .clone(u)
                    .email(email)
                    .nome(nome)
                    .cognome(cognome)
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataDiNascita).getTime()))
                    .luogoNascita(luogoDiNascita)
                    .build();

            UtenteDAO.doUpdate(nu);

            s.setAttribute("utente", nu);

            req.setAttribute("notifica", "Account modificato con successo!");
            req.setAttribute("tipoNotifica", "success");

        } catch (InvalidParameterException | ParseException e) {
            req.setAttribute("notifica", "Parametri inviati non validi!");
            req.setAttribute("tipoNotifica", "danger");
        } catch (DuplicateException e) {
            req.setAttribute("notifica", "È già presente il numero di telefono o questa mail nel database");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.setAttribute("compagniaBroker", CompagniaBrokerDAO.doRetriveBy(u));
        req.getRequestDispatcher("profilo.jsp").forward(req, resp);
    }
}
