$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById("txtLogi").textContent = logi;
    generarCodigoQR();

    $(".verification-input").on("input", function (e) {
        let $this = $(this);
        let inputValue = $this.val();

        if (/^\d$/.test(inputValue)) {
            let nextInput = $this.next(".verification-input");
            if (nextInput.length > 0) {
                nextInput.focus();
            } else {
                verificarYAutoclick();
            }
        } else {
            $this.val('');
        }
    });

    $("#btnVerificar").click(function () {
        verificarYAutoclick();
    });

    document.addEventListener("keydown", function (event) {
        if (event.keyCode === 8) {
            let focusedInput = document.activeElement;
            let $focusedInput = $(focusedInput);

            if ($focusedInput.val() === '' && focusedInput !== document.getElementById("digit1")) {
                let prevInput = $focusedInput.prev(".verification-input");
                if (prevInput.length > 0) {
                    prevInput.focus();
                }
            }
        }
    });

    function verificarYAutoclick() {
        let code = "";
        for (let i = 1; i <= 6; i++) {
            code += $("#digit" + i).val();
        }
        if (code.length === 6) {
            $("#btnVerificar").removeClass("btn-primary").addClass("btn-secondary");
            setTimeout(function () {
                let parametros = {logi: logi, code: code, token: token};
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/auth",
                    contentType: "application/json",
                    data: JSON.stringify(parametros),
                    dataType: "json",
                    success: function (data) {
                        if (data.resultado === true) {
                            window.location.href = "principal.html";
                        } else if(data.resultado === false) {
                            showErrorMessage("El código de autenticación es incorrecto.");
                        }else if (data.resultado === "error") {
                            $("#sesionExpiradaModal").modal("show");
                        }
                    },
                    error: function () {
                        showErrorMessage("Error interno del servidor. Inténtelo más tarde.");
                    },
                    timeout: 3000
                });
                $("#btnVerificar").removeClass("btn-secondary").addClass("btn-primary");
            }, 250);
        } else {
            showErrorMessage("Por favor, complete el código de autenticación.");
        }
    }


    function generarCodigoQR() {
        $("#codigoQR").empty();
        let parametros = {logi: logi};
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/otpauthurl",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            success: function (data) {
                if (data.auth === true) {
                    const qrcode = new QRCode(document.getElementById("codigoQR"), {
                        text: data.otpauthurl,
                        width: 228,
                        height: 228
                    });
                    $("#divMensajeQR").show();
                } else if (data.auth === false) {
                    $("#divMensajeQR").hide();
                }
            }
        });
    }

    function showErrorMessage(message) {
        $("#mensajeError").text(message);
        $("#alertaError").show();
        setTimeout(function () {
            $("#alertaError").fadeOut();
        }, 3000);
    }
});


