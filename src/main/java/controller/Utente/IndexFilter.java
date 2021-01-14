package controller.Utente;

import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.MediazioneDAO;
import model.Notifica.NotificaDAO;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/index.jsp")
public class IndexFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest) req;
        HttpSession s = httpreq.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if (u != null) {
            httpreq.setAttribute("notifiche", NotificaDAO.doRetriveBy(u));
            httpreq.setAttribute("mediazioni", MediazioneDAO.doRetriveBy(u));
            if (u.isCliente())
                httpreq.setAttribute("richieste", RichiestaDAO.doRetriveBy(u));
            else if (u.isArmatore())
                httpreq.setAttribute("imbarcazioni", ImbarcazioneDAO.doRetriveBy(u));
        }

        chain.doFilter(req, resp);
    }
}
