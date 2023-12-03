$(document).ready(function () {
    const idUsuario = getCookie("id");
    const usuario = getCookie("usuario");
    const token = getCookie("token");
    const auth = getCookie("auth");

    if (!idUsuario || !usuario || !token || !auth) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtUsuario').textContent = usuario;

    $('#dataTableDatosPersonales').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/listardatospersonales",
            dataSrc: "",
            headers: {
                Authorization: `Bearer ${token}`
            }
        },
        columns: [
            {data: "denoTipoDocumento"},
            {data: "docuPersona"},
            {data: "apPaPersona"},
            {data: "apMaPersona"},
            {data: "nombPersona"},
            {data: "celuPersona"},
            {data: "emailPersona"},
            {
                render: function (data, type, full, meta) {
                    var idPersona = full.idPersona;
                    return `<td style="text-align: center;">
                        <button class="btn btn-info btn-sm btnEditar" data-idpersona="${idPersona}">
                            <i class="far fa-edit text-white"></i> 
                        </button> 
                        <button class="btn btn-warning btn-sm btnInformacion" data-idpersona="${idPersona}">
                            <i class="far fa-eye text-white"></i>
                        </button> 
                        <button class="btn btn-danger btn-sm btnEliminar" data-idpersona="${idPersona}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ],
        initComplete: function (settings, json) {
            if (json && json.resultado === "ok") {
                $('#dataTableDatosPersonales').DataTable().clear().rows.add(json.datos).draw();
            } else if (json && json.resultado === "error") {
                $('#dataTableDatosPersonales').DataTable().clear().draw();
                $("#modalSesionExpirada").modal('show');
            }
        }
    });

    $('#dataTableDatosPersonales').on('click', '.btnEditar', function () {
        var idPersona = $(this).data('idpersona');
        window.location.href = `editarDatosPersonales.html?idPersona=${idPersona}`;
    });

    function getCookie(name) {
        const cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            const [cookieName, cookieValue] = cookies[i].split("=");
            if (cookieName === name) {
                return cookieValue;
            }
        }
        return null;
    }
});
