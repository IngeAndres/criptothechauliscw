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

    $('#dataTableCuenta').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.cuenta/listarcuenta",
            dataSrc: ""
        },
        columns: [
            {data: "apPaPersona"},
            {data: "apMaPersona"},
            {data: "nombPersona"},
            {data: "denoTipoCuenta"},
            {data: "numbCuenta"},
            {data: "saldoDisponible"},
            {data: "saldoContable"},
            {data: "estadoCuenta"},
            {
                data: "fechaApertura",
                render: function (data, type, full, meta) {
                    var fechaFormateada = new Date(data);
                    var options = {year: 'numeric', month: '2-digit', day: '2-digit'};
                    return fechaFormateada.toLocaleDateString('es-ES', options);
                }
            },
            {
                render: function (data, type, full, meta) {
                    var idCuenta = full.idCuenta;
                    return  `<td align="center">
                        <button class="btn btn-warning btn-sm btnInformacion" data-idcuenta="${idCuenta}">
                            <i class="far fa-eye text-white"></i>
                        </button>
                        <button class="btn btn-danger btn-sm btnEliminar" data-idcuenta="${idCuenta}">
                            <i class="fa fa-trash text-white"></i>
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
