$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnInsertar").click(function () {
        let documentoCliente = $("#DocumentoCliente").val();
        let numeroCuenta = $("#NumeroCuenta").val();
        let tipoCuenta = $("#TipoCuenta").val();
        let saldo = $("#Saldo").val();
        let estado = $("#Estado").val();

        if (!documentoCliente || !numeroCuenta || !tipoCuenta || !estado) {
            $("#alertCamposIncompletos").modal('show');
            return;
        }

        let parametro = {
            documentoCliente: documentoCliente,
            numeroCuenta: numeroCuenta,
            tipoCuenta: tipoCuenta,
            saldoCuenta: saldo,
            estadoCuenta: estado
        };

        $.getJSON("insertarcuentas", parametro, function (data) {
            if (data.resultado === "ok") {
                $("#alertCuentaRegistrado").modal('show');
                setTimeout(function () {
                    $("#alertCuentaRegistrado").modal('hide');
                    setTimeout(function () {
                        window.location.href = "tablesCuentas.html";
                    }, 1000);
                }, 2000);
            } else {
                alert("error");
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