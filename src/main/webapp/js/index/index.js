$(document).ready(function () {
    ["logi", "token", "auth"].forEach(cookie => {
        document.cookie = `${cookie}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    });
    localStorage.removeItem('chatMessages');
    
    
    $("#btnIniciar").click(function () {
        const logi = $("#txtLogi").val();
        const pass = $("#txtPass").val();

        if (!logi || !pass) {
            showErrorMessage("Por favor, complete todos los campos.");
            return;
        }

        const parametros = {logi: logi, pass: encrypt(pass)};

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/iniciarsesion",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            success: function (data) {
                switch (data.status) {
                    case 200:
                        document.cookie = "logi=" + logi + "; path=/";
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
            error: function () {
                showErrorMessage("Error interno del servidor. Inténtelo más tarde.");
            },
            timeout: 3000
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


