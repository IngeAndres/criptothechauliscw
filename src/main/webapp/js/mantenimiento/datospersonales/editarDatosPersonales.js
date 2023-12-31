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
        let denoTipoDocumento = $("#denoTipoDocumento").val();
        let docuPersona = $("#docuPersona").val();
        let ruc = $("#ruc").val();
        let nombPersona = $("#nombPersona").val();
        let apPaPersona = $("#apPaPersona").val();
        let apMaPersona = $("#apMaPersona").val();
        let genePersona = $("input[name='genero']:checked").val();
        let fechPersona = $("#fechPersona").val();
        let direPersona = $("#direPersona").val();
        let celuPersona = $("#celuPersona").val();
        let emailPersona = $("#emailPersona").val();
        let denoDistrito = $("#denoDistrito").val();

        if (!denoTipoDocumento || !docuPersona || !ruc || !nombPersona
                || !apPaPersona || !apMaPersona || !genePersona
                || !fechPersona || !direPersona || !celuPersona
                || !emailPersona || !denoDistrito) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }

        let parametros = {
            idPersona: idPersona,
            denoTipoDocumento: denoTipoDocumento,
            docuPersona: docuPersona,
            ruc: ruc,
            nombPersona: nombPersona,
            apPaPersona: apPaPersona,
            apMaPersona: apMaPersona,
            genePersona: genePersona,
            fechPersona: fechPersona,
            direPersona: direPersona,
            celuPersona: celuPersona,
            emailPersona: emailPersona,
            denoDistrito: denoDistrito
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/editardatospersonales",
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
                                window.location.href = "tablaDatosPersonales.html";
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
