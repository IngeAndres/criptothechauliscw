$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnCambiarClave").click(function () {
        let pass = $("#txtPass").val();
        let newPass1 = $("#txtNewPass1").val();
        let newPass2 = $("#txtNewPass2").val();

        if (!pass || !newPass1 || !newPass2) {
            showErrorMessage("Por favor, complete todos los campos.");
            return;
        }

        let parametros = {
            logi: logi,
            pass: encrypt(pass),
            newpass: encrypt(newPass1),
            token: token
        };

        $.ajax({
            type: "PUT",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/cambiarclave",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            success: function (data) {
                if (data.resultado === "equals") {
                    showErrorMessage("La contraseña nueva es igual a la actual.");
                    return;
                }
                if (newPass1 !== newPass2) {
                    showErrorMessage("La contraseña nueva no coincide.");
                    return;
                }
                if (data.resultado === "passinc") {
                    showErrorMessage("La contraseña actual es incorrecta.");
                    return;
                }
                if (data.resultado === "error") {
                    $("#sesionExpiradaModal").modal("show");
                    return;
                }
                if (data.resultado === "ok") {
                    $("#cambiarClaveModal").modal("hide");
                    $("#modalClaveActualizada").modal("show");
                    limpiarCampos();
                }
            },
            error: function () {
                showErrorMessage("Error interno del servidor. Inténtelo más tarde.");
            },
            timeout: 3000
        });
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
    $("#alertaError").show();
    setTimeout(function () {
        $("#alertaError").fadeOut();
    }, 3000);
}

