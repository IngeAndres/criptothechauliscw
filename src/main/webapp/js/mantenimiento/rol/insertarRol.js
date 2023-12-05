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

    $("#btnInsertar").click(function () {

        let nombPersona = $("#nombPersona").val();
        let fechPersona = $("#fechPersona").val();


        if ( !nombPersona
                
                || !fechPersona ) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }

        let parametros = {

            nombPersona: nombPersona,

            fechPersona: fechPersona


        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rol/insertarrol",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            headers: {
                Authorization: `Bearer ${token}`
            },
            success: function (data) {
                if (data.resultado === "ok") {
                    if (data.success === true) {
                        $("#modalRegistroCompletado").modal('show');
                        setTimeout(function () {
                            $("#modalRegistroCompletado").modal('hide');
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
