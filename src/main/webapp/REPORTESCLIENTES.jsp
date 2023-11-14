<%--
    Document   : resultado
    Created on : 20/05/2023, 04:01:28 PM
    Author     : Andres
--%>

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
    ClienteJpaController objClienteJpaController = new ClienteJpaController();
    List<Object[]> clientes = objClienteJpaController.listarClientes();
    String nombreArchivo = "CLIENTES.pdf";
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

    
    com.itextpdf.text.Document archivo = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4.rotate());
    PdfWriter.getInstance(archivo, response.getOutputStream());
    archivo.open();
    
    Paragraph titulo = new Paragraph("LISTA DE CLIENTES\n\n");
    titulo.setAlignment(Element.ALIGN_CENTER);
    archivo.add(titulo);
    
    PdfPTable tabla = new PdfPTable(8);
    tabla.setWidthPercentage(100);
    
    tabla.addCell("DOCUMENTO");
    tabla.addCell("NUMERO DOCUMENTO");
    tabla.addCell("APELLIDO PATERNO");
    tabla.addCell("APELLIDO MATERNO");
    tabla.addCell("NOMBRE");
    tabla.addCell("DISTRITO");
    tabla.addCell("DIRECCION");
    tabla.addCell("TELEFONO");

    for (Object[] cliente : clientes) {
        tabla.addCell(String.valueOf(cliente[0]));
        tabla.addCell(String.valueOf(cliente[1]));
        tabla.addCell(String.valueOf(cliente[2]));
        tabla.addCell(String.valueOf(cliente[3]));
        tabla.addCell(String.valueOf(cliente[4]));
        tabla.addCell(String.valueOf(cliente[5]));
        tabla.addCell(String.valueOf(cliente[6]));
        tabla.addCell(String.valueOf(cliente[9]));
    }
    archivo.add(tabla);
    archivo.close();
%>
</body>
</html>
