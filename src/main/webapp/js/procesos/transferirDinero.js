$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnIniciarTransfer").click(function () {
        let cuentaOrigen = $("#NumeroCuentaOrigen").val();
        let cuentaDestino = $("#NumeroCuentaDestino").val();
        let monto = $("#Monto").val();

        let parametro = {
            cuentaOrigen: cuentaOrigen,
            cuentaDestino: cuentaDestino,
            monto: monto
        };

        $.getJSON("confirmardatos", parametro, function (data) {
            if (data.resultado === "ok") {
                let numeCuentaOrigen = data.numeCuentaOrigen;
                let nombCuentaOrigen = data.nombCuentaOrigen;
                let numeCuentaDestino = data.numeCuentaDestino;
                let nombCuentaDestino = data.nombCuentaDestino;

                $('#numeCuentaOrigen').text(numeCuentaOrigen);
                $('#nombCuentaOrigen').text(nombCuentaOrigen);
                $('#numeCuentaDestino').text(numeCuentaDestino);
                $('#nombCuentaDestino').text(nombCuentaDestino);

                $('#datoMonto').text(monto);

                $('#datosTransferencia').show();
                $('#mensajeErrorDatos').hide();
                $('#divBtnTransferConfir').show();
                $('#divBtnTransferError').hide();
            } else {
                if (data.resultado === "error1") {
                    $('#mensajeErrorDatos').text("La cuenta Origen no existe. (Error 01)");

                } else if (data.resultado === "error2") {
                    $('#mensajeErrorDatos').text("La cuenta Destino no existe. (Error 02)");

                } else {
                    $('#mensajeErrorDatos').text("No se ha podido cargar los datos. (Error 03)");

                }

                $('#datosTransferencia').hide();
                $('#mensajeErrorDatos').show();
                $('#divBtnTransferConfir').hide();
                $('#divBtnTransferError').show();
            }

            $('#confirmarTransferenciaModal').modal('show');
        });
    });

    $("#btnAceptar").click(function () {
        let cuentaOrigen = $("#NumeroCuentaOrigen").val();
        let cuentaDestino = $("#NumeroCuentaDestino").val();
        let monto = $("#Monto").val();

        let parametro = {
            cuentaOrigen: cuentaOrigen,
            cuentaDestino: cuentaDestino,
            monto: monto
        };

        $.getJSON("transferirdinero", parametro, function (data) {
            if (data.resultado === "ok") {
                //alert("God");
                $('#mensajeTransferencia').text("La transferencia se ha realizado correctamente.");

            } else if (data.resultado === "error1") {
                //alert("Error Insertar Movimiento en la tabla");
                $('#mensajeTransferencia').text("La transferencia no se ha podido realizar. (Error 01)");

            } else if (data.resultado === "error2") {
                //alert("Error Origen/Destino");
                $('#mensajeTransferencia').text("No cuenta con fondos suficientes. (Error 02)");

            } else if (data.resultado === "error3") {
                //alert("Error Origen");
                $('#mensajeTransferencia').text("La cuenta Origen no existe. (Error 03)");

            } else if (data.resultado === "error4") {
                //alert("Error Destino");
                $('#mensajeTransferencia').text("La cuenta Destino no existe. (Error 04)");

            } else {
                //alert("Error ambos");
                $('#mensajeTransferencia').text("La transferencia no se ha podido realizar. (Error 05");
            }

            $('#confirmarTransferenciaModal').modal('hide');
            $('#modalTransferenciaCompleta').modal('show');
        });
    });

    $("#btnConfirTransf").click(function () {
        window.location.href = "tablesTransferencias.html";
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



