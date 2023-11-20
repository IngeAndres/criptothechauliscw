/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.prestamo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.DetalleprestamoJpaController;
import dao.PrestamoJpaController;
import dto.Detalleprestamo;
import dto.Prestamo;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
@WebServlet(name = "ListarAporteMensual", urlPatterns = {"/listaraportemensual"})
public class ListarAporteMensual extends HttpServlet {

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
            DetalleprestamoJpaController objDetalle = new DetalleprestamoJpaController();
          
            
            int idDetalle = Integer.parseInt(request.getParameter("idDetallePres"));

           
            Detalleprestamo detalle = objDetalle.findDetalleprestamo(idDetalle);
            Gson g = new Gson();
            JsonObject responses = new JsonObject();

            if (detalle != null) {
                Date fechaDate = detalle.getFechaPago();
                Instant instant = fechaDate.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate fechaLocalDate = instant.atZone(zoneId).toLocalDate();

// Formatear LocalDate al formato "yyyy-MM-dd"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fechaFormateada = fechaLocalDate.format(formatter);
                responses.addProperty("resultado", "ok");
                responses.addProperty("Fecha", fechaFormateada);
                responses.addProperty("Meses", detalle.getTiempo());
                responses.addProperty("Monto", detalle.getMonto());
                responses.addProperty("Tasa", detalle.getTasa());

                out.print(responses);

            } else {
                responses.addProperty("resultado", "error");
                out.print(responses);
            }

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
