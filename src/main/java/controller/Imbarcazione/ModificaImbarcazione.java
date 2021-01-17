package controller.Imbarcazione;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

@WebServlet(
        urlPatterns = "/modifica-imbarcazione"
)
@MultipartConfig
public class ModificaImbarcazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/error.jsp";
        String codFiscale = u.getCodFiscale();
        String tipologia = req.getParameter("tipologia");
        String imo = req.getParameter("imo");
        String nome = req.getParameter("nome");
        float quantita = Float.parseFloat(req.getParameter("quantita"));
        String bandiera = req.getParameter("bandiera");
        int anno = Integer.parseInt(req.getParameter("anno"));
        float lunghezza = Float.parseFloat(req.getParameter("lunghezza"));
        float ampiezza = Float.parseFloat(req.getParameter("ampiezza"));
        float altezza = Float.parseFloat(req.getParameter("altezza"));
        Part p = req.getPart("documento");
        @Cleanup InputStream documento = null;
        boolean tmp = false;

        try {
            if (p.getSize() > 0 && p.getSize() < 4294967295.0 && p.getContentType().compareTo("application/pdf") == 0) {
                documento = p.getInputStream();
                tmp = true;
            }

            Imbarcazione origin = ImbarcazioneDAO.doRetriveById(imo);
            if (origin.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                if (origin.isDisponibile()) {
                    Imbarcazione i = Imbarcazione.builder()
                            .nome(nome)
                            .imo(imo)
                            .codFiscaleUtente(codFiscale)
                            .quantitaMax(quantita)
                            .bandiera(bandiera)
                            .annoCostruzione(anno)
                            .lunghezza(lunghezza)
                            .ampiezza(ampiezza)
                            .altezza(altezza)
                            .documento(documento)
                            .caricato(tmp)
                            .tipologia(tipologia)
                            .build();

                    ImbarcazioneDAO.doUpdate(i);

                    Notifica n = Notifica.builder().oggetto("Imbarcazione " + i.getImo() + " modificata").corpo("L'imbarcazione " + i.getNome() + " &egrave; stata modficata dall'armatore " + i.getCodFiscaleUtente()).build();

                    NotificaDAO.doSaveAll(i, n);

                    resp.sendRedirect("visualizza-imbarcazione?imo=" + imo);
                    return;
                }
            } else {
                req.setAttribute("errore", "422");
                req.setAttribute("descrizione", "Questa imbarcazione non ti appartiene");
                req.setAttribute("back", "index.jsp");
            }
        } catch (DuplicateException | InvalidParameterException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri errati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
