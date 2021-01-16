package controller.Imbarcazione;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet(
        urlPatterns = "/aggiungi-imbarcazione"
)
@MultipartConfig
public class CreaImbarcazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/error.jsp";
        String codFiscale = u.getCodFiscale();
        String tipologia = req.getParameter("tipologia");
        String imo = req.getParameter("imo");
        String nome = req.getParameter("nome");
        String xx = req.getParameter("quantita");
        float quantita = Float.parseFloat(req.getParameter("quantita"));
        String bandiera = req.getParameter("bandiera");
        int anno = Integer.parseInt(req.getParameter("anno"));
        float lunghezza = Float.parseFloat(req.getParameter("lunghezza"));
        float ampiezza = Float.parseFloat(req.getParameter("ampiezza"));
        float altezza = Float.parseFloat(req.getParameter("altezza"));
        Part p = req.getPart("documento");
        byte[] byteArray = null;
        Blob documento = null;
        boolean tmp = false;
        try {
            if (p.getSize() > 0) {
                @Cleanup InputStream in = p.getInputStream();
                byteArray = IOUtils.toByteArray(in);
                documento = new SerialBlob(byteArray);
                tmp = true;
            }
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

            ImbarcazioneDAO.doSave(i);

            req.setAttribute("notifica", "Imbarcazione aggiunta con successo");
            req.setAttribute("tipoNotifica", "success");
            forward = "index";
        } catch (InvalidParameterException | SQLException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri non validi");
            req.setAttribute("back", "index.jsp");
        } catch (DuplicateException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Imbarcazione già presente nel database contrallare i dati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

}
