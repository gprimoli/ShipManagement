package controller.Imbarcazione;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Util.InvalidParameterException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@MultipartConfig
public class CreaImbarcazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codFiscale = (String) req.getAttribute("codFiscale");
        String imo = (String) req.getAttribute("imo");
        String nome = (String) req.getAttribute("nome");
        float quantita = Float.parseFloat((String) req.getAttribute("quantita"));
        String bandiera = (String) req.getAttribute("bandiera");
        int anno = Integer.parseInt((String) req.getAttribute("anno"));
        float lunghezza = Float.parseFloat((String) req.getAttribute("lunghezza"));
        float ampiezza = Float.parseFloat((String) req.getAttribute("ampiezza"));
        float altezza = Float.parseFloat((String) req.getAttribute("altezza"));
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
                    .build();

            ImbarcazioneDAO.doSave(i);

            req.setAttribute("notifica", "Imbarcazione aggiunta con successo");
            req.setAttribute("tipoNotifica", "success");
        } catch (InvalidParameterException | SQLException e) {
            req.setAttribute("notifica", "Ops! Abbiamo riscontrato problemi con i campi inviati, riprova");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}
