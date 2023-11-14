/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.mantenimiento.cuenta;

import dao.ClienteJpaController;
import dao.CuentaJpaController;
import dao.EstadoJpaController;
import dao.TipocuentaJpaController;
import dto.Cliente;
import dto.Cuenta;
import dto.Estado;
import dto.Tipocuenta;
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
 * @author Antho
 */
@WebServlet(name = "EditarCuenta", urlPatterns = {"/editarcuenta"})
public class EditarCuenta extends HttpServlet {

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

            CuentaJpaController objCuentasJpaController = new CuentaJpaController();
            ClienteJpaController objClienteJpaController = new ClienteJpaController();
            EstadoJpaController objEstadocuentaJpaController = new EstadoJpaController();
            TipocuentaJpaController objTipocuentaJpaController = new TipocuentaJpaController();
            
            int codigoCuenta = Integer.parseInt(request.getParameter("codigocuenta"));
           
            String documentoCliente = request.getParameter("documentoCliente");
            String numeroCuenta = request.getParameter("numeroCuenta");
            String tipoCuenta =  request.getParameter("tipoCuenta");
            Double saldo = Double.valueOf(request.getParameter("saldoCuenta"));
            String estado = request.getParameter("estadoCuenta");
            Date fecha = new Date();

            Cliente obtenerCodigoCliente = objClienteJpaController.obtenerCodigoCliente(documentoCliente);
            Estado obtenerestadoCuenta = objEstadocuentaJpaController.obtenerEstadoCuenta(estado);
            Tipocuenta obtenerTipoCuenta = objTipocuentaJpaController.obtenerTipoCuenta(tipoCuenta);

            Cuenta nuevaCuenta = new Cuenta();

            nuevaCuenta.setCodigoCliente(obtenerCodigoCliente);
            nuevaCuenta.setNumeCuenta(numeroCuenta);
            nuevaCuenta.setCodigoTipoCuenta(obtenerTipoCuenta);
            nuevaCuenta.setSaldoCuenta(saldo);
            nuevaCuenta.setCodigoEstado(obtenerestadoCuenta);
            nuevaCuenta.setFechApertCuenta(fecha);
            
            System.out.println("datos" + " obtenercodigocliente: "+obtenerCodigoCliente + " numerocuenta "+ numeroCuenta +  
                    " obtenertipocuenta " +obtenerTipoCuenta+  "saldo" +  saldo + "obtenerestadocuenta " + obtenerestadoCuenta + "fecha " +fecha
            
            );
            boolean resultado = objCuentasJpaController.editarCuenta(codigoCuenta, nuevaCuenta);
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
