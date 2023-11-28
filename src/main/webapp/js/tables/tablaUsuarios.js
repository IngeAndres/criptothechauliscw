$(document).ready(function () {
    const idUsuario = getCookie("id");
    const usuario = getCookie("usuario");
    const token = getCookie("token");
    const auth = getCookie("auth");

    if (!idUsuario || !usuario || !token || !auth) {
        $("#sesionExpiradaModal").modal('show');
        return;
    }

    document.getElementById('txtUsuario').textContent = usuario;

    $('#dataTableClientes').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/listarusuarios",
            dataSrc: ""
        },
        columns: [
            {data: "denoTipoDocumento"},
            {data: "docuPersona"},
            {data: "apPaPersona"},
            {data: "apMaPersona"},
            {data: "nombPersona"},
            {
                render: function (data, type, full, meta) {
                    var codigoPersona = full.idPersona;
                    return `<td style="text-align: center;">
                        <button class="btn btn-info btn-sm btnEditar" data-codigopersona="${codigoPersona}">
                            <i class="far fa-edit text-white"></i> 
                        </button> 
                        <button class="btn btn-warning btn-sm btnInformacion" data-codigopersona="${codigoPersona}">
                            <i class="far fa-eye text-white"></i>
                        </button> 
                        <button class="btn btn-danger btn-sm eliminarPersona" data-codigopersona="${codigoPersona}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ]
    });

    $('#dataTable').on('click', '.btnEditar', function () {
        var codigoPersona = $(this).data('codigopersona');
        window.location.href = 'editarClientes.html?codigoPersona=' + codigoPersona;
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
