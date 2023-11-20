/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.prestamo;

import com.google.gson.Gson;
import dao.TipocomprobanteJpaController;
import dao.TipoinformacionbienJpaController;
import dao.TipoprestamoJpaController;
import dto.Tipocomprobante;
import dto.Tipoinformacionbien;
import dto.Tipoprestamo;
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
 * @author carlo
 */
@WebServlet(name = "ListarTipoPrestamo", urlPatterns = {"/listarTipoPrestamo"})
public class ListarTipoPrestamo extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            TipoprestamoJpaController TipoprestamoController = new TipoprestamoJpaController();
            List<Tipoprestamo> Tipoprestamos = TipoprestamoController.listarTipoprestamo();

            List<Map<String, Object>> TipoprestamosMapList = new ArrayList<>();

            for (Tipoprestamo Tipoprestamo : Tipoprestamos) {
                Map<String, Object> TipoprestamoMap = new HashMap<>();
                TipoprestamoMap.put("ID", Tipoprestamo.getIdTipoPrestamo());
                TipoprestamoMap.put("NOMBRE", Tipoprestamo.getDenoTipoPrestamo());
                TipoprestamoMap.put("CATEGORIA", Tipoprestamo.getCategoriaTipoPrestamo());
                TipoprestamosMapList.add(TipoprestamoMap);
            }

            TipocomprobanteJpaController TipocomprobanteController = new TipocomprobanteJpaController();
            List<Tipocomprobante> Tipocomprobante = TipocomprobanteController.listarTipocomprobante();

            List<Map<String, Object>> TipocomprobanteMapList = new ArrayList<>();

            for (Tipocomprobante Tipoprestamo : Tipocomprobante) {
                Map<String, Object> TipocomprobanteMap = new HashMap<>();
                TipocomprobanteMap.put("IDT", Tipoprestamo.getIdTipoComprobante());
                TipocomprobanteMap.put("NOMBRET", Tipoprestamo.getDenoTipoComprobante());
                TipocomprobanteMapList.add(TipocomprobanteMap);
            }
            
             TipoinformacionbienJpaController TipoinformacionController = new TipoinformacionbienJpaController();
            List<Tipoinformacionbien> Tipoinformacion = TipoinformacionController.listarTipoinformacion();

            List<Map<String, Object>> TipoinformacionMapList = new ArrayList<>();

            for (Tipoinformacionbien Tipoinfo : Tipoinformacion) {
                Map<String, Object> TipoinformacionMap = new HashMap<>();
                TipoinformacionMap.put("IDI", Tipoinfo.getIdTipoInformacionBien());
                TipoinformacionMap.put("NOMBREI", Tipoinfo.getDenoTipoInformacionBien());
                TipoinformacionMapList.add(TipoinformacionMap);
            }
            // Crear un objeto que contenga ambas listas
            Map<String, List<?>> data = new HashMap<>();
            data.put("Tipoprestamos", TipoprestamosMapList);
            data.put("Tipocomprobante", TipocomprobanteMapList);
            data.put("Tipoinformacion", TipoinformacionMapList);
            // Convertir el objeto a JSON
            Gson gson = new Gson();
            String jsonData = gson.toJson(data);

            // Enviar el JSON como respuesta
            out.print(jsonData);
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
