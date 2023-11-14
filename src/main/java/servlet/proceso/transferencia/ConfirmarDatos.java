/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.transferencia;

import dao.ClienteJpaController;
import dao.CuentaJpaController;
import dto.Cliente;
import dto.Cuenta;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ing. Andres Gomez
 */
@WebServlet(name = "ConfirmarDatos", urlPatterns = {"/confirmardatos"})
public class ConfirmarDatos extends HttpServlet {

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
            CuentaJpaController objCuentaJpaController = new CuentaJpaController();
            ClienteJpaController objClienteJpaController = new ClienteJpaController();

            String numeroCuentaOrigen = request.getParameter("cuentaOrigen");
            String numeroCuentaDestino = request.getParameter("cuentaDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));

            Cuenta cuentaOrigen = objCuentaJpaController.obtenerCodigoCuenta(numeroCuentaOrigen);
            Cuenta cuentaDestino = objCuentaJpaController.obtenerCodigoCuenta(numeroCuentaDestino);
            Cliente clienteOrigen = new Cliente();
            Cliente clienteDestino = new Cliente();
            String nombClienteOrigen = "";
            String nombClienteDestino = "";

            if (cuentaOrigen != null) {
                clienteOrigen = objClienteJpaController.findCliente(cuentaOrigen.getCodigoCliente().getCodigoCliente());

                nombClienteOrigen = clienteOrigen.getNombClie() + " "
                        + clienteOrigen.getAppaClie() + " " + clienteOrigen.getApmaClie();
            }

            if (cuentaDestino != null) {
                clienteDestino = objClienteJpaController.findCliente(cuentaDestino.getCodigoCliente().getCodigoCliente());

                nombClienteDestino = clienteDestino.getNombClie() + " "
                        + clienteDestino.getAppaClie() + " " + clienteDestino.getApmaClie();
            }

            if (cuentaOrigen != null && cuentaDestino != null) {
                out.print("{\"resultado\": \"ok\", "
                        + "\"numeCuentaOrigen\":\"" + cuentaOrigen.getNumeCuenta() + "\", "
                        + "\"nombCuentaOrigen\":\"" + nombClienteOrigen + "\", "
                        + "\"numeCuentaDestino\":\"" + cuentaDestino.getNumeCuenta() + "\", "
                        + "\"nombCuentaDestino\":\"" + nombClienteDestino + "\"}");
            } else if (cuentaOrigen == null) {
                out.print("{\"resultado\":\"error1\"}");
            } else if (cuentaDestino == null) {
                out.print("{\"resultado\":\"error2\"}");
            } else {
                out.print("{\"resultado\":\"error3\"}");
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
