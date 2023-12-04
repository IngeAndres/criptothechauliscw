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
        let denoTipoUsuario = $("#denoTipoUsuario").val();
        let idUsuario = $("#idUsuario").val();
        let passUsuario = $("#passUsuario").val();

        if (!denoTipoUsuario || !idUsuario || !passUsuario) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }

        let parametros = {
            denoTipoUsuario: denoTipoUsuario,
            idUsuario: idUsuario,
            passUsuario: encrypt(passUsuario)
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/insertarusuario",
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
                                window.location.href = "tablaUsuarios.html";
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

    function encrypt(message) {
        return CryptoJS.SHA256(message).toString(CryptoJS.enc.Hex);
    }

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
