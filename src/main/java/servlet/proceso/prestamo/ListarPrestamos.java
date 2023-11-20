package servlet.proceso.prestamo;

import com.google.gson.Gson;
import dao.PrestamoJpaController;
import dto.Prestamo;
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
 * @author Ing. Andres Gomez
 */
@WebServlet(name = "ListarPrestamos", urlPatterns = {"/listarprestamos"})
public class ListarPrestamos extends HttpServlet {

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
            String codigoCuenta = request.getParameter("codigocuenta");

            PrestamoJpaController objPrestamosJpaController = new PrestamoJpaController();
            List<Prestamo> cuentas = objPrestamosJpaController.findPrestamoEntities();
            List<Map<String, Object>> cuentasMapList = new ArrayList<>();
/*
            for (Prestamo cuentaData : cuentas) {
                Map<String, Object> cuentaMap = new HashMap<>();
                if (codigoCuenta.equals(cuentaData.getCodigoCliente().getDocuClie())) {
                    cuentaMap.put("CODIGOPRESTAMO", cuentaData.getCodigoPrestamo());
                    cuentaMap.put("PLAZO", cuentaData.getPlazoPrestamo());
                    cuentaMap.put("TASAS", cuentaData.getTasasPrestamo());
                    cuentaMap.put("TOTAL", cuentaData.getTotalPagarPrestamo());
                    cuentaMap.put("FECHA", cuentaData.getFechPrestamo());
                    cuentaMap.put("TOTALPRESTAMO", cuentaData.getTotalPedidoPrestamo());
                    cuentasMapList.add(cuentaMap);
                }
            }

            // String json = gson.toJson(cuentas);
            Gson gson = new Gson();
            String resultado = gson.toJson(cuentasMapList);
            out.print(resultado);
            */
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
