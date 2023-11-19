$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

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

    function getCookie(name) {
        const cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            const cookiePair = cookies[i].split("=");
            if (cookiePair[0] === name) {
                return cookiePair[1];
            }
        }
        return null;
    }
});
