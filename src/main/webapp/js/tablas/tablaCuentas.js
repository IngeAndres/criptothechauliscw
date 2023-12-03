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

    $('#dataTableCuentas').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            type: "GET",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.cuenta/listarcuentas",
            dataSrc: "",
            headers: {
                Authorization: `Bearer ${token}`
            }
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
                        <button class="btn btn-danger btn-sm btnInhabilitar" data-idcuenta="${idCuenta}">
                            <i class="fa fa-trash text-white"></i>
                        </button>
                    </td>`;
                }
            }
        ],
        initComplete: function (settings, json) {
            if (json && json.resultado === "ok") {
                $('#dataTableCuentas').DataTable().clear().rows.add(json.datos).draw();
            } else if (json && json.resultado === "error") {
                $('#dataTableCuentas').DataTable().clear().draw();
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
