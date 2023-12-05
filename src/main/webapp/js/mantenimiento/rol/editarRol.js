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

    $("#btnEditar").click(function () {
        var urlParams = new URLSearchParams(window.location.search);
        var idPersona = urlParams.get('idPersona');
        let nombPersona = $("#nombPersona").val();
        let fechPersona = $("#fechPersona").val();


        if (!nombPersona

                || !fechPersona) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }

        let parametros = {
            idPersona: idPersona,
            nombPersona: nombPersona,

            fechPersona: fechPersona


        };



        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rol/editarrol",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            headers: {
                Authorization: `Bearer ${token}`
            },
            success: function (data) {
                if (data.resultado === "ok") {
                    if (data.success === true) {
                        $("#modalDatosActualizados").modal('show');
                        setTimeout(function () {
                            $("#modalDatosActualizados").modal('hide');
                            setTimeout(function () {
                                window.location.href = "tablaRol.html";
                            }, 1000);
                        }, 2000);
                    } else {
                        alert("Error");
                    }
                } else if (data.resultado === "error") {
                    $("#modalSesionExpirada").modal('show');
                }
            }
        });
    });
});
