$(document).ready(function () {
    ["id", "usuario", "token", "auth"].forEach(cookie => {
        document.cookie = `${cookie}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    });
    localStorage.removeItem('chatMessages');

    $("#btnIniciarSesion").click(function () {
        const idUsuario = $("#txtIdUsuario").val();
        const passUsuario = $("#txtPassUsuario").val();

        if (!idUsuario || !passUsuario) {
            showErrorMessage("Por favor, complete todos los campos.");
            return;
        }

        const parametros = {
            idUsuario: idUsuario,
            passUsuario: encrypt(passUsuario)
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/iniciarsesion",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            success: function (data) {
                switch (data.status) {
                    case 200:
                        document.cookie = "id=" + idUsuario + "; path=/";
                        document.cookie = "usuario=" + data.usuario + "; path=/";
                        document.cookie = "token=" + data.token + "; path=/";

                        window.location.href = "autenticacion.html";
                        break;
                    case 401:
                        showErrorMessage("El usuario y/o contraseña son incorrectos.");
                        break;
                    case 404:
                        showErrorMessage("El usuario no existe.");
                        break;
                }
            },
            error: function (xhr, status, error) {
                console.error(xhr, status, error);
                showErrorMessage("Error interno del servidor. Por favor, inténtelo de nuevo más tarde.");
            },
            timeout: 10000
        });
    });

    function encrypt(message) {
        return  CryptoJS.SHA256(message).toString(CryptoJS.enc.Hex);
    }

    function showErrorMessage(message) {
        $("#mensajeError").text(message);
        $("#alertaError").show().delay(3000).fadeOut();
    }
});


