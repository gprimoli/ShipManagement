package controller.Mediazione;

import lombok.SneakyThrows;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(
        urlPatterns = "/rimuovi-mediazione"
)
public class EliminaMediazione extends HttpServlet {
    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String forward = "index";
        Mediazione m = MediazioneDAO.doRetriveById(id);

        if (m.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
            if (m.getStato().compareTo("Default") == 0) {

                MediazioneDAO.doDelete(m);

                req.setAttribute("notifica", "Mediazione Eliminata!");
                req.setAttribute("tipoNotifica", "danger");
            } else if (m.getStato().compareTo("In Attesa di Firma") == 0 || m.getStato().compareTo("Richiesta Modifica") == 0) {
                Notifica n = Notifica.builder().oggetto("Mediazione " + m.getNome() + " eliminata").corpo("La mediazione " + m.getNome() + " di cui facevi parte &egrave; stata eliminata").build();

                NotificaDAO.doSaveAll(m, n);
                MediazioneDAO.doDelete(m);

                req.setAttribute("notifica", "Mediazione Eliminata!");
                req.setAttribute("tipoNotifica", "danger");
            } else {
                req.setAttribute("errore", "422");
                req.setAttribute("back", "index.jsp");
                forward = "/WEB-INF/errore.jsp";
                req.setAttribute("descrizione", "Non puoi eliminare una mediazione avviata!");
            }
        } else {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Non hai i permessi per visualizzare la mediazione perchè non ne fai parte!");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
