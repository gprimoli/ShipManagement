package controller.Imbarcazione;

import lombok.SneakyThrows;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
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
        urlPatterns = "/disponibile-indisponibile"
)
public class RendiIndisponibileDisponibile extends HttpServlet {

    @SneakyThrows
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        String forward = "index";

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Imbarcazione i = ImbarcazioneDAO.doRetriveById(id);

            if (i.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                if (i.isDisponibile()) {

                    Notifica n = Notifica.builder().oggetto("Imbarcazione " + i.getImo() + " resa indisponibile").corpo("L'imbarcazione " + i.getNome() + " &egrave; stata resa indisponibile dall'armatore " + i.getCodFiscaleUtente()).build();

                    NotificaDAO.doSaveAll(i, n);

                    ImbarcazioneDAO.doDisponibileIndisponibile(i);

                    req.setAttribute("notifica", "Imbarcazione resa indisponibile!");
                    req.setAttribute("tipoNotifica", "danger");
                } else {
                    if (!MediazioneDAO.doCheck(i)) {
                        ImbarcazioneDAO.doDisponibileIndisponibile(i);
                        req.setAttribute("notifica", "Imbarcazione resa disponibile!");
                        req.setAttribute("tipoNotifica", "success");
                    } else {
                        req.setAttribute("errore", "422");
                        req.setAttribute("back", "index.jsp");
                        forward = "/WEB-INF/errore.jsp";
                        req.setAttribute("descrizione", "L'imbarcazione fa parte di una mediazione non può essere disponibile.!");
                    }
                }
            } else {
                req.setAttribute("errore", "422");
                req.setAttribute("back", "index.jsp");
                forward = "/WEB-INF/errore.jsp";
                req.setAttribute("descrizione", "Non hai i permessi per rimuovere la richiesta perchè non ne fai parte!");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Parametri errati!");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
