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

    $('#dataTableCuentasTransferir').DataTable({
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
            {data: "docuPersona"},
            {data: "denoTipoCuenta"},
            {data: "numbCuenta"},
            {data: "saldoDisponible"},
            {
                "data": "fechaApertura",
                "render": function (data) {
                    var date = new Date(data);
                    var fechaFormateada = date.toLocaleDateString("es-ES", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit"
                    });
                    return fechaFormateada;
                }
            }
        ],
        initComplete: function (settings, json) {
            if (json && json.resultado === "ok") {
                $('#dataTableCuentasTransferir').DataTable().clear().rows.add(json.datos).draw();
            } else if (json && json.resultado === "error") {
                $('#dataTableCuentasTransferir').DataTable().clear().draw();
                $("#modalSesionExpirada").modal('show');
            }
        }
    });
});
