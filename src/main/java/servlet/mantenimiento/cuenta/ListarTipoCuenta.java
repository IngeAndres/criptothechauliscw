package servlet.mantenimiento.cuenta;

import com.google.gson.Gson;
import dao.TipocuentaJpaController;
import dto.Tipocuenta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antho
 */
@WebServlet(name = "ListarTipoCuenta", urlPatterns = {"/listartipocuenta"})
public class ListarTipoCuenta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            TipocuentaJpaController objTipocuentaJpaController = new TipocuentaJpaController();
            List<Tipocuenta> cuenta = objTipocuentaJpaController.listarTipoCuenta();

            List<Map<String, Object>> cuentaTipoMap = new ArrayList<>();
            
             for (Tipocuenta cuentatipo : cuenta) {
                Map<String, Object> distritoMap = new HashMap<>();
                distritoMap.put("ID", cuentatipo.getCodigoTipoCuenta());
                distritoMap.put("NOMBRE", cuentatipo.getNombTipoCuenta());

                cuentaTipoMap.add(distritoMap);
            }

            Gson gson = new Gson();

            String jsonDistritos = gson.toJson(cuentaTipoMap);
            out.print(jsonDistritos);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}