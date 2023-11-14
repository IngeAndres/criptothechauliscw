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
            "url": "/CriptoTheChaulisCW/listarcliente",
            "dataSrc": ""
        },
        "columns": [
            {"data": "DOCUMENTO"},
            {"data": "NUMERODOC"},
            {"data": "APELLIDO_PATERNO"},
            {"data": "APELLIDO_MATERNO"},
            {"data": "NOMBRE"},
            {"data": "DISTRITO"},
            {"data": "DIRECCION"},

            {
                "render": function (data, type, full, meta) {

                    var numCuenta = full.NUMERODOC;

                    return `<td align="center">
        <button class="btn btn-warning btn-sm btnInformacion" data-numcliente="${numCuenta}" onclick="redirectToPrestamosCuenta(${numCuenta})">

            <i class="far fa-eye text-white"></i>
        </button>
    </td>`;
                }
            }
        ]

    });


});

function redirectToPrestamosCuenta(numCuenta) {
    localStorage.setItem("codigocuenta", numCuenta);
    window.location.href = "prestamosCuenta.html";
}

