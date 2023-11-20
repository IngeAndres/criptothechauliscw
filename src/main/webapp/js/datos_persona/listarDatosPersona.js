$(document).ready(function () {
//    const logi = sessionStorage.getItem("logi");
//    const token = sessionStorage.getItem("token");
//
//    if (!logi || !token) {
//        window.location.href = "index.html";
//        return;
//    }
//
//    document.getElementById('txtLogi').textContent = logi;

    $('#dataTableDatosPersonales').DataTable({
        "ajax": {
            "url": "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/listardatos",
            "dataSrc": ""
        },
        "columns": [
            {"data": "idPersona"},
            {"data": "denoTipoDocumento"},
            {"data": "docuPersona"},
            {"data": "apPaPersona"},
            {"data": "apMaPersona"},
            {"data": "nombPersona"},
            {"data": "celuPersona"},
            {"data": "emailPersona"}
        ]
    });

    $('#dataTable').on('click', '.btnEditar', function () {
        var codigoCliente = $(this).data('codigocliente');
        window.location.href = 'editarClientes.html?codigoCliente=' + codigoCliente;
    });
});
