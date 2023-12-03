$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

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

function redirectToPrestamosCuenta(numCuenta) {
    localStorage.setItem("codigocuenta", numCuenta);
    window.location.href = "prestamosCuenta.html";
}

