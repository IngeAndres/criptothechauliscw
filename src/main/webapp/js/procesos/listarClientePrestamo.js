$(document).ready(function () {
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
});
 
