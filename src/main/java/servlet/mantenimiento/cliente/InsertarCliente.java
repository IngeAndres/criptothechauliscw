/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.mantenimiento.cliente;

import dao.ClienteJpaController;
import dao.DistritoJpaController;
import dao.EstadoJpaController;
import dao.TipodocumentoJpaController;
import dto.Cliente;
import dto.Distrito;
import dto.Estado;
import dto.Tipodocumento;
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
@WebServlet(name = "InsertarCliente", urlPatterns = {"/insertarcliente"})
public class InsertarCliente extends HttpServlet {

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
            ClienteJpaController objClienteJpaController = new ClienteJpaController();
            TipodocumentoJpaController objTipodocJpaController = new TipodocumentoJpaController();
            DistritoJpaController objDistritoJpaController = new DistritoJpaController();
            EstadoJpaController objEstadoJpaController = new EstadoJpaController();
            
            String documento = request.getParameter("documento");
            String numeroDoc = request.getParameter("numerodoc");
            String paterno = request.getParameter("paterno");
            String materno = request.getParameter("materno");
            String nombre = request.getParameter("nombre");
            String distrito = request.getParameter("distrito");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            int estadoCliente = Integer.parseInt(request.getParameter("estadocliente"));

            Tipodocumento tipoDocumento = objTipodocJpaController.obtenerCodigoTipoDoc(documento);
            Distrito nombDist = objDistritoJpaController.obtenerCodigoDistrito(distrito);
            Estado estado = objEstadoJpaController.obtenerCodigoEstado(estadoCliente);
            Cliente nuevoCliente = new Cliente();

            nuevoCliente.setCodigoTipoDocumento(tipoDocumento);
            nuevoCliente.setDocuClie(numeroDoc);
            nuevoCliente.setAppaClie(paterno);
            nuevoCliente.setApmaClie(materno);
            nuevoCliente.setNombClie(nombre);
            nuevoCliente.setCodigoDistrito(nombDist);
            nuevoCliente.setDireClie(direccion);
            nuevoCliente.setTelfClie(telefono);
            nuevoCliente.setCodigoEstado(estado);

            boolean resultado = objClienteJpaController.insertarCliente(nuevoCliente);
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
