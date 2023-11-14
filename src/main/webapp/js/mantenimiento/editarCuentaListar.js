$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var codigocuenta = urlParams.get('codigoCuentas');

    $.ajax({
        type: "GET",
        url: "buscarcuenta",
        data: {codigocuentas: codigocuenta},
        dataType: "json",
        success: function (data) {
            $("#DocumentoCliente").val(data[0].DOCUMENTO);
            $("#numerocuenta").val(data[0].NUMERO);
            $("#TipoCuenta").val(data[0].TIPO);
            $("#saldo").val(data[0].SALDO);
            $("#Estado").val(data[0].ESTADO);
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
