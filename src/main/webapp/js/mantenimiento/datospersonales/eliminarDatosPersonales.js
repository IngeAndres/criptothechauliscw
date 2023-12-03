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
