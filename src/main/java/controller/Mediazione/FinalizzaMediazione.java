package controller.Mediazione;

import model.Imbarcazione.Imbarcazione;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Richiesta.Richiesta;
import model.Utente.Utente;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(urlPatterns = "/finalizza-mediazione")
public class FinalizzaMediazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        String forward = "";
        try {
            int idMediazione = Integer.parseInt(req.getParameter("id"));
            Mediazione origin = MediazioneDAO.doRetriveById(idMediazione);
            if (origin.isCaricato()) {
                Mediazione m = Mediazione.builder()
                        .clone(origin)
                        .stato("In Attesa di Firma")
                        .build();
                LinkedList<Imbarcazione> i = MediazioneDAO.doRetriveImbarcazioniFrom(m);
                LinkedList<Richiesta> r = MediazioneDAO.doRetriveRichiesteFrom(m);
                if (i.size() > 0 && r.size() > 0) {
                    MediazioneDAO.doUpdate(m);

                    Notifica n = Notifica.builder().oggetto("In attesa di Firma Mediazione " + m.getId()).corpo("<a href='visualizza-mediazione?id=" + idMediazione + "'><button class='btn btn-primary' type='submit'>Visualizza Mediazione</button>\n").build();
                    int notificaID = NotificaDAO.doSave(n);

                    NotificaDAO.doSendToBroker(m, notificaID);
                    for (Imbarcazione im : i)
                        NotificaDAO.doSendToPropietario(im, notificaID);
                    for (Richiesta ri : r)
                        NotificaDAO.doSendToPropietario(ri, notificaID);

                    resp.sendRedirect("index");
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    req.setAttribute("descrizione", "Mancano le richieste o le imbarcazione!");
                    forward = "/WEB-INF/error.jsp";
                }
            } else {
                req.setAttribute("errore", "422");
                req.setAttribute("back", "index.jsp");
                req.setAttribute("descrizione", "Contratto non caricato");
                forward = "/WEB-INF/error.jsp";
            }
        } catch (NumberFormatException | InvalidParameterException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Parametri errati!");
            forward = "/WEB-INF/error.jsp";
        } catch (NoEntryException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Nessuna mediazione con l'id passato!");
            forward = "/WEB-INF/error.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
