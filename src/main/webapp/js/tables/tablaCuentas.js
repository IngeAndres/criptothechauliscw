$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $('#dataTableCuenta').DataTable({
        "language": {
            "url": "/CriptoTheChaulisCW/json/es-ES.json"
        },
        "ajax": {
            "url": "http://localhost:8080/CriptoTheChaulis/webresources/dto.cuenta/listarcuenta",
            "dataSrc": ""
        },
        "columns": [
            {"data": "apPaPersona"},
            {"data": "apMaPersona"},
            {"data": "nombPersona"},
            {"data": "denoTipoCuenta"},
            {"data": "numbCuenta"},
            {"data": "saldoDisponible"},
            {"data": "saldoContable"},
            {"data": "estadoCuenta"},
            {
                "data": "fechaApertura",
                "render": function (data, type, full, meta) {
                    var fechaFormateada = new Date(data);
                    var options = {year: 'numeric', month: '2-digit', day: '2-digit'};
                    return fechaFormateada.toLocaleDateString('es-ES', options);
                }
            },
            {
                "render": function (data, type, full, meta) {
                    var codigoCuenta = full.CODIGOCUENTA;
                    return  `<td align="center">
                        <button class="btn btn-info btn-sm btnEditar" data-codigocuenta="${codigoCuenta}">
                            <i class="far fa-edit text-white"></i>
                        </button>
                        <button class="btn btn-warning btn-sm btnInformacion" data-codigocuenta="${codigoCuenta}">
                            <i class="far fa-eye text-white"></i>
                        </button>
                        <button class="btn btn-danger btn-sm eliminarCuenta" data-codigocuenta="${codigoCuenta}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ]
    });

    $('#dataTableCuenta').on('click', '.btnEditar', function () {
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
