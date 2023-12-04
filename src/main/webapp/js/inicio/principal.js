$(document).ready(function () {
    const idUsuario = getCookie("id");
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

    $("#btnCambiarClave").click(function () {
        let passUsuario = $("#txtPassUsuario").val();
        let newPass1 = $("#txtNewPass1").val();
        let newPass2 = $("#txtNewPass2").val();

        if (!passUsuario || !newPass1 || !newPass2) {
            showErrorMessage("Por favor, complete todos los campos.");
            return;
        }

        let parametros = {
            idUsuario: idUsuario,
            passUsuario: encrypt(passUsuario),
            newPass1: encrypt(newPass1),
            newPass2: encrypt(newPass2)
        };

        $.ajax({
            type: "PUT",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/cambiarcontrasena",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            headers: {
                Authorization: `Bearer ${token}`
            },
            success: function (data) {
                switch (data.resultado) {
                    case "equals":
                        showErrorMessage("La contraseña nueva es igual a la actual.");
                        break;
                    case "newpassinc":
                        showErrorMessage("Las contraseñas nuevas no coinciden.");
                        break;
                    case "passinc":
                        showErrorMessage("La contraseña actual es incorrecta.");
                        break;
                    case "error":
                        $("#modalSesionExpirada").modal("show");
                        break;
                    case "ok":
                        $("#modalCambioContrasena").modal("hide");
                        $("#modalContrasenaActualizada").modal("show");
                        limpiarCampos();
                        break;
                }
            },
            error: function () {
                showErrorMessage("Error interno del servidor. Inténtelo más tarde.");
            },
            timeout: 3000
        });
    });

    function limpiarCampos() {
        $("#txtPass").val('');
        $("#txtNewPass1").val('');
        $("#txtNewPass2").val('');
    }

    function encrypt(message) {
        return CryptoJS.SHA256(message).toString(CryptoJS.enc.Hex);
    }

    function showErrorMessage(mensaje) {
        $("#mensajeError").text(mensaje);
        $("#alertaError").show().delay(3000).fadeOut();
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

function cerrarSesion() {
    $.getJSON("cerrarsession", function (data) {
        window.location.href = "index.html";
    });
}