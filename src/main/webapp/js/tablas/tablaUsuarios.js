$(document).ready(function () {
    const usuario = getCookie("usuario");
    const token = getCookie("token");

    $.getJSON("/CriptoTheChaulisCW/validarsessionprin", function (data) {
        if (data.resultado === "ok") {
            console.log("ok");
        } else if (data.resultado === "error") {
            window.location.href = "index.html";
        }
    });

    document.getElementById('txtUsuario').textContent = usuario;

    $('#dataTableUsuarios').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            type: "GET",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/listarclientes",
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
            {
                render: function (data, type, full, meta) {
                    var idUsuario = full.idUsuario;
                    return `<td style="text-align: center;">
                        <button class="btn btn-warning btn-sm btnInformacion" data-idusuario="${idUsuario}">
                            <i class="far fa-eye text-white"></i>
                        </button> 
                        <button class="btn btn-danger btn-sm btnEliminar" data-idusuario="${idUsuario}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ],
        initComplete: function (settings, json) {
            if (json && json.resultado === "ok") {
                $('#dataTableUsuarios').DataTable().clear().rows.add(json.datos).draw();
            } else if (json && json.resultado === "error") {
                $('#dataTableUsuarios').DataTable().clear().draw();
                $("#modalSesionExpirada").modal('show');
            }
        }
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
