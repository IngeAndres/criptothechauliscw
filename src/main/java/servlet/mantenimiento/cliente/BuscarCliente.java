/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.mantenimiento.cliente;

import com.google.gson.Gson;
import dao.ClienteJpaController;
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
 * @author Ing. Andres Gomez
 */
@WebServlet(name = "BuscarCliente", urlPatterns = {"/buscarcliente"})
public class BuscarCliente extends HttpServlet {

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
            int Codigo = Integer.parseInt(request.getParameter("codigo"));

            ClienteJpaController objClienteJpaController = new ClienteJpaController();

            List<Object[]> clientes = objClienteJpaController.listarClientesPorCodigo(Codigo);

            List<Map<String, Object>> clientesMapList = new ArrayList<>();

            for (Object[] clienteData : clientes) {
                int estadoCliente = (int) clienteData[7];

                if (estadoCliente == 1) {
                    Map<String, Object> clienteMap = new HashMap<>();
                    clienteMap.put("DOCUMENTO", clienteData[0]);
                    clienteMap.put("NUMERODOC", clienteData[1]);
                    clienteMap.put("APELLIDO_PATERNO", clienteData[2]);
                    clienteMap.put("APELLIDO_MATERNO", clienteData[3]);
                    clienteMap.put("NOMBRE", clienteData[4]);
                    clienteMap.put("DISTRITO", clienteData[5]);
                    clienteMap.put("DIRECCION", clienteData[6]);
                    clienteMap.put("CODIGOCLIENTE", clienteData[8]);
                    clienteMap.put("ESTADOCLIENTE", estadoCliente);
                    clienteMap.put("TELEFONO", clienteData[9]);
                    clientesMapList.add(clienteMap);
                }
            }

            Gson gson = new Gson();

            // Convertir a JSON y retornamos
            String jsonClientes = gson.toJson(clientesMapList);
            out.print(jsonClientes);
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
