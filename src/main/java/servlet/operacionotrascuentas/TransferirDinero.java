/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.operacionotrascuentas;

import dao.CuentaJpaController;
import dao.OperacionesotrascuentasJpaController;
import dao.TipooperacionJpaController;
import dto.Cuenta;
import dto.Operacionesotrascuentas;
import dto.Tipooperacion;
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
 * @author Jeff
 */
@WebServlet(name = "TransferirDinero", urlPatterns = {"/transferirDinero"})
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
            /* TODO output your page here. You may use following sample code. */
            CuentaJpaController objCuenta = new CuentaJpaController();
            OperacionesotrascuentasJpaController objTransfer = new OperacionesotrascuentasJpaController();
            TipooperacionJpaController objTipoOp = new TipooperacionJpaController();
            
            String numeroCuentaOrigen = request.getParameter("cuentaOrigen");
            String numeroCuentaDestino = request.getParameter("cuentaDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));
            String moneda = request.getParameter("moneda");
            Date fecha = new Date();
            
            Cuenta cuentaOrigen = objCuenta.getCodCuenta(numeroCuentaOrigen);
            Cuenta cuentaDestino = objCuenta.getCodCuenta(numeroCuentaDestino);
            Tipooperacion tipoOp = objTipoOp.findTipooperacion(3);
            
            if (cuentaOrigen != null && cuentaDestino != null) {
                Operacionesotrascuentas transfer = new Operacionesotrascuentas();
                transfer.setIdOperacionOC(0);
                transfer.setIdTipoOperacion(tipoOp);
                transfer.setIdCuentaOrigen(cuentaOrigen);
                transfer.setIdCuentaDestino(cuentaDestino);
                transfer.setMontoOperacionOC(monto);
                transfer.setMonedaOperacionOC(moneda);
                transfer.setFechaOperacionOC(fecha);

                boolean resultado1 = false;
                boolean resultado2 = false;
                boolean resultado3 = false;
            
                resultado1 = objCuenta.retirarDinero(numeroCuentaOrigen, monto);

                if (resultado1) {
                    resultado2 = objCuenta.ingresarDinero(numeroCuentaDestino, monto);

                    if (resultado2) {
                        resultado3 = objTransfer.insertTransfer(transfer);
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
