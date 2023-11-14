<%--
    Document   : resultado
    Created on : 20/05/2023, 04:01:28 PM
    Author     : Andres
--%>

<%@page import="dao.CuentaJpaController"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.Cliente"%>
<%@page import="dao.ClienteJpaController"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Usuarios</title>
    </head>
    <body>
        <%
            CuentaJpaController objCuentasJpaController = new CuentaJpaController();
            List<Object[]> cuentas = objCuentasJpaController.listarCuentas();
            String nombreArchivo = "CUENTAS.pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            com.itextpdf.text.Document archivo = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4.rotate());
            PdfWriter.getInstance(archivo, response.getOutputStream());
            archivo.open();

            Paragraph titulo = new Paragraph("LISTA DE CUENTAS\n\n");
            titulo.setAlignment(Element.ALIGN_CENTER);
            archivo.add(titulo);

            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);

            tabla.addCell("DOCUMENTO");
            tabla.addCell("NUMERO CUENTA");
            tabla.addCell("TIPO");
            tabla.addCell("SALDO");
            tabla.addCell("FECHA");
            tabla.addCell("ESTADO");


            for (Object[] cuenta : cuentas) {
                tabla.addCell(String.valueOf(cuenta[1]));
                tabla.addCell(String.valueOf(cuenta[2]));
                tabla.addCell(String.valueOf(cuenta[3]));
                tabla.addCell(String.valueOf(cuenta[4]));
                tabla.addCell(String.valueOf(cuenta[5]));
                tabla.addCell(String.valueOf(cuenta[6]));
            }
            archivo.add(tabla);
            archivo.close();
        %>
    </body>
</html>
