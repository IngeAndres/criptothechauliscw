/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.prestamo;

import dao.ClienteJpaController;
import dao.PrestamoJpaController;
import dto.Cliente;
import dto.Prestamo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ing. Andres Gomez
 */
@WebServlet(name = "RegistrarPrestamo", urlPatterns = {"/registrarprestamo"})
public class RegistrarPrestamo extends HttpServlet {

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
            ClienteJpaController objCuentasJpaController = new ClienteJpaController();
            PrestamoJpaController objPrestamosJpaController = new PrestamoJpaController();

            String numeroDoc = request.getParameter("numeroDoc");
            String plazoPrestamo = request.getParameter("plazoPrestamo");
            Double tasasPrestamo = Double.valueOf(request.getParameter("tasasPrestamo"));
            Double montoPrestamo = Double.valueOf(request.getParameter("montoPrestamo"));
            Double montoPrestamoTot = Double.valueOf(request.getParameter("montoPrestamoTot"));
            Date fechPrestamo = new Date();

            Cliente codigoCuenta = objCuentasJpaController.obtenerCodigoCliente(numeroDoc);

            Prestamo registrarPrest = new Prestamo();

            registrarPrest.setCodigoCliente(codigoCuenta);
            registrarPrest.setPlazoPrestamo(plazoPrestamo);
            registrarPrest.setTasasPrestamo(tasasPrestamo);
            registrarPrest.setTotalPagarPrestamo(montoPrestamo);
            registrarPrest.setFechPrestamo(fechPrestamo);
            registrarPrest.setTotalPedidoPrestamo(montoPrestamoTot);

            boolean resultado = objPrestamosJpaController.registrarPrestamos(registrarPrest);
            if (resultado) {
                out.print("{\"resultado\":\"ok\"}");
            } else {
                out.print("{\"resultado\":\"error\"}");
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
