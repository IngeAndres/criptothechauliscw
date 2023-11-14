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
                    var codigoCliente = full.CODIGOCLIENTE;
                    return `<td style="text-align: center;">
                        <button class="btn btn-info btn-sm btnEditar" data-codigocliente="${codigoCliente}">
                            <i class="far fa-edit text-white"></i> 
                        </button> 
                        <button class="btn btn-warning btn-sm btnInformacion" data-codigocliente="${codigoCliente}">
                            <i class="far fa-eye text-white"></i>
                        </button> 
                        <button class="btn btn-danger btn-sm eliminarCliente" data-codigocliente="${codigoCliente}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ]
    });

    $('#dataTable').on('click', '.btnEditar', function () {
        var codigoCliente = $(this).data('codigocliente');
        window.location.href = 'editarClientes.html?codigoCliente=' + codigoCliente;
    });

});
 