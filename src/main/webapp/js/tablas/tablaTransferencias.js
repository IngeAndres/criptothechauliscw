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

    $('#dataTableTransferencias').DataTable({
        language: {
            url: "/CriptoTheChaulisCW/json/es-ES.json"
        },
        ajax: {
            type: "GET",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.operacionesotrascuentas/listartransferencias",
            dataSrc: "",
            headers: {
                Authorization: `Bearer ${token}`
            }
        },
        columns: [
            {data: "CODTRANSFER"},
            {data: "NUMECUENTAORIGEN"},
            {data: "APPAORIGEN"},
            {data: "APMAORIGEN"},
            {data: "NOMBORIGEN"},
            {data: "NUMECUENTADESTINO"},
            {data: "APPADESTINO"},
            {data: "APMADESTINO"},
            {data: "NOMBDESTINO"},
            {data: "MONTO"},
            {data: "MONEDA"},
            {
                "data": "FECHA",
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
                $('#dataTableTransferencias').DataTable().clear().rows.add(json.datos).draw();
            } else if (json && json.resultado === "error") {
                $('#dataTableTransferencias').DataTable().clear().draw();
                $("#modalSesionExpirada").modal('show');
            }
        }
    });

    //Listar en Servlet ListarTransferencias
    /*$('#dataTableTransferencias').DataTable({
        "language": {
            "url": "/CriptoTheChaulisCW/json/es-ES.json"
        },
        "ajax": {
            "url": "/CriptoTheChaulisCW/listarTransferencias",
            "dataSrc": ""
        },
        "columns": [
            {"data": "CODTRANSFER"},
            {"data": "NUMECUENTAORIGEN"},
            {"data": "APPAORIGEN"},
            {"data": "APMAORIGEN"},
            {"data": "NOMBORIGEN"},
            {"data": "NUMECUENTADESTINO"},
            {"data": "APPADESTINO"},
            {"data": "APMADESTINO"},
            {"data": "NOMBDESTINO"},
            {"data": "MONTO"},
            {"data": "MONEDA"},
            {
                "data": "FECHA",
                "render": function (data) {
                    var date = new Date(data);
                    var fechaFormateada = date.toLocaleDateString("es-ES", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit",
                        hour: "2-digit",
                        minute: "2-digit",
                        second: "2-digit"
                    });
                    return fechaFormateada;
                }
            }
        ]
    });*/

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
