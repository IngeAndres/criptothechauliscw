/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.operacionotrascuentas;

import com.google.gson.Gson;
import dao.OperacionesotrascuentasJpaController;
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
 * @author Jeff
 */
@WebServlet(name = "ListarTransferencias", urlPatterns = {"/listarTransferencias"})
public class ListarTransferencias extends HttpServlet {

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
            OperacionesotrascuentasJpaController objTransfer = new OperacionesotrascuentasJpaController();
            List<Object[]> transferencias = objTransfer.listTransfer();
            List<Map<String, Object>> transferMapList = new ArrayList<>();

            for (Object[] transferData : transferencias) {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("CODTRANSFER", transferData[0]);
                mapa.put("NUMECUENTAORIGEN", transferData[1]);
                mapa.put("APPAORIGEN", transferData[2]);
                mapa.put("APMAORIGEN", transferData[3]);
                mapa.put("NOMBORIGEN", transferData[4]);
                mapa.put("NUMECUENTADESTINO", transferData[5]);
                mapa.put("APPADESTINO", transferData[6]);
                mapa.put("APMADESTINO", transferData[7]);
                mapa.put("NOMBDESTINO", transferData[8]);
                mapa.put("MONTO", transferData[9]);
                mapa.put("MONEDA", transferData[10]);
                mapa.put("FECHA", transferData[11]);
                transferMapList.add(mapa);
            }

            Gson gson = new Gson();
            String resultado = gson.toJson(transferMapList);
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
