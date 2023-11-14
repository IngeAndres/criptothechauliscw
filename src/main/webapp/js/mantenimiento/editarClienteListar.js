$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var codigoCliente = urlParams.get('codigoCliente');

    $.ajax({
        type: "GET",
        url: "buscarcliente",
        data: {codigo: codigoCliente},
        dataType: "json",
        success: function (data) {

            $("#Nombre").val(data[0].NUMERODOC);
            $("#ApellidoPaterno").val(data[0].APELLIDO_PATERNO);
            $("#ApellidoMaterno").val(data[0].APELLIDO_MATERNO);
            $("#NombreCliente").val(data[0].NOMBRE);
            $("#Distrito").val(data[0].DISTRITO);
            $("#Direccion").val(data[0].DIRECCION);
            $("#Telefono").val(data[0].TELEFONO);


            $("#Documento").val(data[0].DOCUMENTO);
            $("#Distrito").val(data[0].DISTRITO);

        },
        error: function () {
            alert("Error al obtener los datos del cliente.");
        }
    });

    if (!sessionStorage.getItem('reloaded')) {
        location.reload();
        sessionStorage.setItem('reloaded', 'true');
    }
});
