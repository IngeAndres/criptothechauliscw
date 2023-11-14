/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.transferencia;

import dao.CuentaJpaController;
import dao.TransferenciaJpaController;
import dto.Cuenta;
import dto.Transferencia;
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
@WebServlet(name = "TransferirDinero", urlPatterns = {"/transferirdinero"})
public class TransferirDinero extends HttpServlet {

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
            TransferenciaJpaController objTransferenciaJpaController = new TransferenciaJpaController();
            
            String numeroCuentaOrigen = request.getParameter("cuentaOrigen");
            String numeroCuentaDestino = request.getParameter("cuentaDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));
            Date fecha = new Date();
            
            Cuenta cuentaOrigen = objCuentasJpaController.obtenerCodigoCuenta(numeroCuentaOrigen);
            Cuenta cuentaDestino = objCuentasJpaController.obtenerCodigoCuenta(numeroCuentaDestino);
            
            if (cuentaOrigen != null && cuentaDestino != null) {
                Transferencia transferencia = new Transferencia();
                transferencia.setCodigoTransferencia(0);
                transferencia.setCodigoCuentaOrigen(cuentaOrigen);
                transferencia.setMontoTransferencia(monto);
                transferencia.setCodigoCuentaDestino(cuentaDestino);
                transferencia.setFechTransferencia(fecha);

                boolean resultado1 = false;
                boolean resultado2 = false;
                boolean resultado3 = false;
            
                resultado1 = objCuentasJpaController.retirarDinero(numeroCuentaOrigen, monto);

                if (resultado1) {
                    resultado2 = objCuentasJpaController.ingresarDinero(numeroCuentaDestino, monto);

                    if (resultado2) {
                        resultado3 = objTransferenciaJpaController.insertTransfer(transferencia);
                        out.print("{\"resultado\":\"ok\"}");
                    } else {
                        out.print("{\"resultado\":\"error1\"}");
                    }
                } else {
                    out.print("{\"resultado\":\"error2\"}");
                }
            } else {
                if (cuentaOrigen == null) {
                    out.print("{\"resultado\":\"error3\"}");
                } else if (cuentaDestino == null) {
                    out.print("{\"resultado\":\"error4\"}");
                }
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
