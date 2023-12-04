package session;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Ing. Andres Gomez
 */
public class Session {

    public static void crearsesion(HttpSession session) {
        session.setAttribute("logueadoAuth", "1");
    }

    public static boolean sesionvalidaAuth(HttpSession session) {
        String logueado = (String) session.getAttribute("logueadoAuth");
        return logueado.equals("1");
    }
    
    public static boolean sesionvalidaPrin(HttpSession session) {
        String logueado = (String) session.getAttribute("logueadoPrin");
        return logueado.equals("1");
    }

    public static void cerrarsesion(HttpSession session) {
        session.removeAttribute("logueadoAuth");
        session.removeAttribute("logueadoPrin");
        session.invalidate();
    }
}
