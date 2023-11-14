package servlet.mantenimiento.cuenta;

import com.google.gson.Gson;
import dao.CuentaJpaController;
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
@WebServlet(name = "ListarCuentas", urlPatterns = {"/listarcuentas"})
public class ListarCuentas extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            CuentaJpaController objCuentasJpaController = new CuentaJpaController();
            List<Object[]> cuentas = objCuentasJpaController.listarCuentas();
            List<Map<String, Object>> cuentasMapList = new ArrayList<>();

            for (Object[] cuentaData : cuentas) {
                String estadoCuenta = (String) cuentaData[6]; 
               if (estadoCuenta.equals("Activo")) {
                    Map<String, Object> cuentaMap = new HashMap<>();
                    cuentaMap.put("CODIGOCUENTA", cuentaData[0]);
                    cuentaMap.put("DOCUMENTO", cuentaData[1]);
                    cuentaMap.put("NUMERO", cuentaData[2]);
                    cuentaMap.put("TIPO", cuentaData[3]);
                    cuentaMap.put("SALDO", cuentaData[4]);
                    cuentaMap.put("FECHA", cuentaData[5]);
                    cuentaMap.put("ESTADO", estadoCuenta);
                    cuentasMapList.add(cuentaMap);
               }
            }

            Gson gson = new Gson();
            String resultado = gson.toJson(cuentasMapList);
            out.print(resultado);

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
