/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.proceso.prestamo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.CuentaJpaController;
import dao.DetalleprestamoJpaController;
import dao.PrestamoJpaController;
import dao.TipocomprobanteJpaController;
import dao.TipoinformacionbienJpaController;
import dao.TipoprestamoJpaController;

import dto.Prestamo;
import dto.Cuenta;
import dto.Detalleprestamo;
import dto.Tipocomprobante;
import dto.Tipoinformacionbien;
import dto.Tipoprestamo;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        try ( PrintWriter out = response.getWriter()) {

            CuentaJpaController objCuentasJpaController = new CuentaJpaController();
            PrestamoJpaController objPrestamosJpaController = new PrestamoJpaController();
            DetalleprestamoJpaController objDetallePrestamosController = new DetalleprestamoJpaController();
            TipoprestamoJpaController objTipoPrestamoController = new TipoprestamoJpaController();
            TipocomprobanteJpaController objTipoComprobanteController = new TipocomprobanteJpaController();
            TipoinformacionbienJpaController objTipoInformacionController = new TipoinformacionbienJpaController();

            int IdTipoInfo = Integer.parseInt(request.getParameter("IdTipoInformacionBien"));
            String CuotasAdicionales = (request.getParameter("CuotasAdicionales"));
            Double Monto = Double.valueOf(request.getParameter("Monto"));
            String MonedaName = (request.getParameter("Moneda"));
            Double Tasa = Double.valueOf(request.getParameter("Tasa"));
            int Tiempo = Integer.parseInt(request.getParameter("Tiempo"));
            String FechaPago = (request.getParameter("FechaPago"));
            int IdTipoPrestamo = Integer.parseInt(request.getParameter("IdTipoPrestamo"));
            String NumbCuenta = request.getParameter("NumeroCuenta");
            int IdTipoComprobante = Integer.parseInt(request.getParameter("IdTipoComprobante"));
            Character cuotasAdicionales = null;
            if (CuotasAdicionales != null && !CuotasAdicionales.isEmpty()) {
                char primerCaracter = CuotasAdicionales.charAt(0);
                cuotasAdicionales = primerCaracter;
            }

            LocalDate fechaPagoDate = null;

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fechaPagoDate = LocalDate.parse(FechaPago, formatter);
            } catch (DateTimeParseException e) {
               
                 System.out.println("Error al convertir la fecha. Fecha proporcionada: " + FechaPago);// Manejo de la excepción si ocurre un error al parsear la fecha
            }

            Tipoinformacionbien IngTipoInfo = objTipoInformacionController.findTipoinformacionbien(IdTipoInfo);
            Cuenta IngCuenta = objCuentasJpaController.obtenerPorNumCuenta(NumbCuenta);
            Tipoprestamo IngTipoPres = objTipoPrestamoController.findTipoprestamo(IdTipoPrestamo);
            Tipocomprobante IngTipoComp = objTipoComprobanteController.findTipocomprobante(IdTipoComprobante);

            Detalleprestamo registrarDetPrest = new Detalleprestamo();
            registrarDetPrest.setIdTipoInformacionBien(IngTipoInfo);
            registrarDetPrest.setCuotasAdicionales((cuotasAdicionales));
            registrarDetPrest.setMonto(Monto);
            registrarDetPrest.setMoneda(MonedaName);
            registrarDetPrest.setTasa(Tasa);
            registrarDetPrest.setTiempo(Tiempo);
           registrarDetPrest.setFechaPago(java.sql.Date.valueOf(fechaPagoDate));


            int resultadoDet = objDetallePrestamosController.registrarDetPrestamos(registrarDetPrest);

            if (resultadoDet != 0) {
                Prestamo registrarPrestamo = new Prestamo();

                Detalleprestamo IngDetalle = objDetallePrestamosController.findDetalleprestamo(resultadoDet);

                registrarPrestamo.setIdTipoPrestamo(IngTipoPres);
                registrarPrestamo.setIdCuenta(IngCuenta);
                registrarPrestamo.setIdTipoComprobante(IngTipoComp);
                registrarPrestamo.setIdDetallePrestamo(IngDetalle);

                boolean resultado = objPrestamosJpaController.registrarPrestamos(registrarPrestamo);

                Gson g = new Gson();
                JsonObject responses = new JsonObject();

                if (resultado) {
                    responses.addProperty("resultado", "ok");
                    responses.addProperty("idDetalle", resultadoDet);
                    responses.addProperty("TipoPrest", IngTipoPres.getDenoTipoPrestamo());
                    out.print(responses);

                } else {
                    responses.addProperty("resultado", "error");
                    out.print(responses);
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
