$(document).ready(function () {
    $('#dataTable').DataTable({
        "language": {
            "url": "/CriptoTheChaulisCW/json/es-ES.json"
        },
        "ajax": {
            "url": "/CriptoTheChaulisCW/listarcuentas",
            "dataSrc": ""
        },
        "columns": [
            {"data": "DOCUMENTO"},
            {"data": "NUMERO"},
            {"data": "TIPO"},
            {"data": "SALDO"},
            {
                "data": "FECHA",
                "render": function (data) {
                    var date = new Date(data);
                    var fechaFormateada = date.toLocaleDateString("es-ES", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit"
                    });
                    return fechaFormateada;
                }
            }
        ]
    });

    $('#dataTable').on('click', '.btnEditar', function () {
        var codigocuenta = $(this).data('codigocuenta');
        window.location.href = 'editarCuentas.html?codigoCuentas=' + codigocuenta;
    });
});
