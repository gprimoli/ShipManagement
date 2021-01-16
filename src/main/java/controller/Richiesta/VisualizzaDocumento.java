package controller.Richiesta;

import model.Imbarcazione.Imbarcazione;
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
        urlPatterns = "/visualizza-richiesta-documento"
)
public class VisualizzaDocumento extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Integer id = Integer.parseInt(req.getParameter("id"));
        Utente u = (Utente) s.getAttribute("utente");

        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/richiesta.jsp";

        try {
            Richiesta r = RichiestaDAO.doRetriveById(id);

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
                    req.setAttribute("notifica", "Non hai i permessi per visualizzare l'imbarcazione!");
                    req.setAttribute("tipoNotifica", "danger");
                    forward = "index";
                }
            }
        } catch (NoEntryException e) {
            req.setAttribute("notifica", "Imbarcazione inesistente");
            req.setAttribute("tipoNotifica", "danger");
            forward = "index";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
