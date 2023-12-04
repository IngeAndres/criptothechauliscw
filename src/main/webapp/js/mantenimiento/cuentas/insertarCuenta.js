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

    $('#saldoDisponible').on('input', function () {
        var saldoDisponibleValue = $(this).val();
        $('#saldoContable').val(saldoDisponibleValue);
    });

    $("#btnInsertar").click(function () {
        let idUsuario = $("#idUsuario").val();
        let denoTipoCuenta = $("#denoTipoCuenta").val();
        let saldoDisponible = $("#saldoDisponible").val();
        let saldoContable = $("#saldoContable").val();

        if (!idUsuario || !denoTipoCuenta || !saldoDisponible || !saldoContable) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }

        let parametros = {
            idUsuario: idUsuario,
            denoTipoCuenta: denoTipoCuenta,
            saldoDisponible: saldoDisponible,
            saldoContable: saldoContable
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.cuenta/insertarcuenta",
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
                                window.location.href = "tablaCuentas.html";
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
