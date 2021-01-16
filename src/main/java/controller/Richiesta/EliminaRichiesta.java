package controller.Richiesta;

import lombok.SneakyThrows;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(
        urlPatterns = "/rimuovi-richiesta"
)
public class EliminaRichiesta extends HttpServlet {

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        int id = Integer.parseInt((String) req.getAttribute("id"));
        String forward = "index";
        Richiesta r = RichiestaDAO.doRetriveById(id);

        if(r.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0){
            if(r.getStato().compareTo("Disponibile") == 0){

                Notifica n = Notifica.builder().corpo("Richiesta " + r.getId() + " eliminata").oggetto("La richiesta " + r.getId() + " &egrave; stata eliminata dal clinete " + r.getCodFiscaleUtente()).build();

                NotificaDAO.doSaveAll(r, n);

                RichiestaDAO.doDelete(r);
                req.setAttribute("notifica", "Richiesta Eliminata!");
                req.setAttribute("tipoNotifica", "danger");
            }else {
                req.setAttribute("notifica", "Non puoi eliminare una richiesta avviata!");
                req.setAttribute("tipoNotifica", "danger");
            }
        }else {
            req.setAttribute("notifica", "Non hai i permessi per rimuovere la richiesta perchè non ne fai parte!");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
