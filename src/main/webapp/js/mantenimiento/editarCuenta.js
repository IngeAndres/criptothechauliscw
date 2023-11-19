$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnEditar").click(function () {
        var urlParams = new URLSearchParams(window.location.search);
        var codigocuenta = urlParams.get('codigoCuentas');
        let documentocliente = $("#DocumentoCliente").val();
        let numerocuenta = $("#numerocuenta").val();
        let tipocuenta = $("#TipoCuenta").val();
        let saldocuenta = $("#saldo").val();
        let estadocuenta = $("#Estado").val();


        if (!documentocliente || !numerocuenta || !tipocuenta || !saldocuenta || !estadocuenta) {
            $("#alertCamposIncompletos").modal('show');
            return;
        }

        let parametro = {
            codigocuenta: codigocuenta,
            documentoCliente: documentocliente,
            numeroCuenta: numerocuenta,
            tipoCuenta: tipocuenta,
            saldoCuenta: saldocuenta,
            estadoCuenta: estadocuenta
        };

        $.getJSON("editarcuenta", parametro, function (data) {
            if (data.resultado === "ok") {
                $("#alertCuentaEditado").modal('show');
                setTimeout(function () {
                    $("#alertCuentaEditado").modal('hide');
                    setTimeout(function () {
                        window.location.href = "tablesCuentas.html";
                    }, 1000);
                }, 2000);
            } else {
                alert("Error");
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
