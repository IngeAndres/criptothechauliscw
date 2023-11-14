$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

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
});
