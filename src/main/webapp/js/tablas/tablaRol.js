$(document).ready(function () {
    const usuario = getCookie("usuario");
    const token = getCookie("token");

    $.getJSON("validarsessionprin", function (data) {
        if (data.resultado === "ok") {
            $("body").show();
            document.getElementById('txtUsuario').textContent = usuario;
        } else {
            window.location.href = "index.html";
            return;
        }
    });

    $('#dataTableDatosPersonales').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            type: "GET",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rol/listarrol",
            dataSrc: "",
            headers: {
                Authorization: `Bearer ${token}`
            }
        },
        columns: [
            {data: "idRol"},
            {data: "NombreRol"},
            {data: "FechaRol"},

            {
                render: function (data, type, full, meta) {
                    var idPersona = full.idRol;
                    return `<td style="text-align: center;">
                        <button class="btn btn-info btn-sm btnEditar" data-idpersona="${idPersona}">
                            <i class="far fa-edit text-white"></i> 
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
        window.location.href = `editarRol.html?idPersona=${idPersona}`;
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
