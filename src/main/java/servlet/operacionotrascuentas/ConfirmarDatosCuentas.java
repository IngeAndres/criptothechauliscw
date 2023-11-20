/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.operacionotrascuentas;

import dao.CuentaJpaController;
import dao.DatospersonalesJpaController;
import dao.UsuarioJpaController;
import dto.Cuenta;
import dto.Datospersonales;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jeff
 */
@WebServlet(name = "ConfirmarDatosCuentas", urlPatterns = {"/confirmarDatosCuentas"})
public class ConfirmarDatosCuentas extends HttpServlet {

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
            UsuarioJpaController objUsuario = new UsuarioJpaController();
            DatospersonalesJpaController objDatosPers = new DatospersonalesJpaController();
            
            String numeroCuentaOrigen = request.getParameter("cuentaOrigen");
            String numeroCuentaDestino = request.getParameter("cuentaDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));
            
            Cuenta cuentaOrigen = objCuenta.getCodCuenta(numeroCuentaOrigen);
            Cuenta cuentaDestino = objCuenta.getCodCuenta(numeroCuentaDestino);
            Usuario usuarioOrigen;
            Usuario usuarioDestino;
            String nombUsuarioOrigen = "";
            String nombUsuarioDestino = "";
            
            if (cuentaOrigen != null) {
                usuarioOrigen = objUsuario.findUsuario(
                        cuentaOrigen.getIdUsuario().getIdUsuario());
                Datospersonales datosOrigen = usuarioOrigen.getIdPersona();
                nombUsuarioOrigen = datosOrigen.getNombPersona() + " "
                        + datosOrigen.getApPaPersona() + " "
                        + datosOrigen.getApMaPersona();
            }
            
            if (cuentaDestino != null) {
                usuarioDestino = objUsuario.findUsuario(
                        cuentaDestino.getIdUsuario().getIdUsuario());
                Datospersonales datosDestino = usuarioDestino.getIdPersona();
                nombUsuarioDestino = datosDestino.getNombPersona() + " "
                        + datosDestino.getApPaPersona() + " "
                        + datosDestino.getApMaPersona();
            }
            
            if (cuentaOrigen != null && cuentaDestino != null) {
                out.print("{\"resultado\": \"ok\", "
                        + "\"numeCuentaOrigen\":\"" + cuentaOrigen.getNumbCuenta() + "\", "
                        + "\"nombCuentaOrigen\":\"" + nombUsuarioOrigen + "\", "
                        + "\"numeCuentaDestino\":\"" + cuentaDestino.getNumbCuenta() + "\", "
                        + "\"nombCuentaDestino\":\"" + nombUsuarioDestino + "\"}");
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
