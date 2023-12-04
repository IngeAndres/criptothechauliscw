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

    $('#dataTableDatosPersonales').on('click', '.btnEliminar', function () {
        var idPersona = $(this).data('idpersona');
        $('#modalEliminarRegistro').modal('show');
        $('#confirmarEliminar').on('click', function () {
            let parametro = {idPersona: idPersona};
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/eliminardatospersonales",
                contentType: "application/json",
                data: JSON.stringify(parametro),
                dataType: "json",
                headers: {
                    Authorization: `Bearer ${token}`
                },
                success: function (data) {
                    if (data.resultado === "ok") {
                        if (data.success === true) {
                            location.reload();
                        } else {
                            alert("Error");
                        }
                    } else if (data.resultado === "error") {
                        $("#modalSesionExpirada").modal('show');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error al eliminar los datos personales: ' + error);
                }
            });
        });
    });
});
