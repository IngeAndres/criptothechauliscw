$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

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

    $('#dataTable').on('click', '.btnEditar', function () {
        var codigocuenta = $(this).data('codigocuenta');
        window.location.href = 'editarCuentas.html?codigoCuentas=' + codigocuenta;
    });
});
