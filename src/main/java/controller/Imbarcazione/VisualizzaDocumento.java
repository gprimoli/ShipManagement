package controller.Imbarcazione;

import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;


@WebServlet(
        urlPatterns = "/visualizza-imbarcazione-documento"
)
public class VisualizzaDocumento extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        String imo = req.getParameter("imo");
        Utente u = (Utente) s.getAttribute("utente");

        if (u == null || imo == null) {
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/imbarcazione.jsp";

        try {
            Imbarcazione im = ImbarcazioneDAO.doRetriveById(imo);

            if ((u.isBroker() || im.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) && im.isCaricato()) {

                resp.setContentType("application/pdf");

                byte[] buffer = new byte[20 * 1024];
                OutputStream outputStream = resp.getOutputStream();
                while (true) {
                    int readSize = im.getDocumento().read(buffer);
                    if (readSize == -1)
                        break;
                    outputStream.write(buffer, 0, readSize);
                }
                return;
            } else {
                LinkedList<Mediazione> propMediazini = MediazioneDAO.doRetriveBy(u);
                boolean tmp = false;
                for (Mediazione m : propMediazini) {
                    try {
                        LinkedList<Imbarcazione> imbarcazioni = MediazioneDAO.doRetriveImbarcazioniFrom(m);
                        for (Imbarcazione i : imbarcazioni) {
                            if (i.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                                tmp = true;
                                break;
                            }
                        }
                        if (tmp)
                            break;
                    } catch (NoEntryException ignored) {
                    }
                }
                if (tmp && im.isCaricato()) {

                    resp.setContentType("application/pdf");

                    byte[] buffer = new byte[20 * 1024];
                    OutputStream outputStream = resp.getOutputStream();
                    while (true) {
                        int readSize = im.getDocumento().read(buffer);
                        if (readSize == -1)
                            break;
                        outputStream.write(buffer, 0, readSize);
                    }
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    req.setAttribute("descrizione", "Non hai i permessi per visualizzare l'imbarcazione!");
                    forward = "/WEB-INF/error.jsp";
                }
            }
        } catch (NoEntryException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Imbarcazione inesistente");
            forward = "/WEB-INF/error.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
