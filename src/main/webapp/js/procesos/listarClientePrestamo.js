$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

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
            {"data": "NOMBRE"}
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

