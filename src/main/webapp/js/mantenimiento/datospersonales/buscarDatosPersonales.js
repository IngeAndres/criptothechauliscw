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

    var urlParams = new URLSearchParams(window.location.search);
    var idPersona = urlParams.get('idPersona');

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/obtenerdatospersonales",
        contentType: "application/json",
        data: idPersona,
        dataType: "json",
        headers: {
            Authorization: `Bearer ${token}`
        },
        success: function (data) {
            if (data.resultado === "ok") {
                $("#docuPersona").val(data.datos.docuPersona);
                $("#ruc").val(data.datos.ruc);
                $("#apPaPersona").val(data.datos.apPaPersona);
                $("#apMaPersona").val(data.datos.apMaPersona);
                $("#nombPersona").val(data.datos.nombPersona);
                if (data.datos.genePersona === "M") {
                    $("#generoM").prop("checked", true);
                } else {
                    $("#generoF").prop("checked", true);
                }
                var date = new Date(data.datos.fechPersona);
                var fechPersona = date.toISOString().split('T')[0];
                $("#fechPersona").val(fechPersona);
                $("#direPersona").val(data.datos.direPersona);
                $("#celuPersona").val(data.datos.celuPersona);
                $("#emailPersona").val(data.datos.emailPersona);
            } else if (data.resultado === "error") {
                $("#sesionExpiradaModal").modal('show');
            }
        },
        error: function () {
            alert("Error al obtener los datos del cliente.");
        }
    });

    if (!sessionStorage.getItem('reloaded')) {
        location.reload();
        sessionStorage.setItem('reloaded', 'true');
    }
});
