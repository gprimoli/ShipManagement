package controller.Mediazione;

import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
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
        urlPatterns = "/visualizza-mediazione-contratto"
)
public class VisualizzaContratto extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        int id = Integer.parseInt(req.getParameter("id"));
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/richiesta.jsp";

        try {
            Mediazione r = MediazioneDAO.doRetriveById(id);

            if (u.isBroker() || r.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {

                resp.setContentType("application/pdf");

                byte[] buffer = new byte[20 * 1024];
                OutputStream outputStream = resp.getOutputStream();
                while (true) {
                    int readSize = r.getDocumento().read(buffer);
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
                if (tmp) {

                    resp.setContentType("application/pdf");

                    byte[] buffer = new byte[20 * 1024];
                    OutputStream outputStream = resp.getOutputStream();
                    while (true) {
                        int readSize = r.getDocumento().read(buffer);
                        if (readSize == -1)
                            break;
                        outputStream.write(buffer, 0, readSize);
                    }
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    forward = "/WEB-INF/errore.jsp";
                    req.setAttribute("descrizione", "Non hai i permessi per visualizzare l'imbarcazione!");
                }
            }
        } catch (NoEntryException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Imbarcazione inesistente");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
