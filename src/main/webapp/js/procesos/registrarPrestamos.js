$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnRegistrarP").click(function (e) {
        e.preventDefault();

        var numeroDocumento = $('#NumeroDoc').val();
        var plazoPrestamo = $('#plazoPrestamo').val();
        var tazaPrestamo = $('#tazaPrestamo').val();
        var montoPrestamo = $('#montoPrestamo').val();
        var montoPrestamoTot = $('#montoPrestamoTot').val();

        let parametro = {
            numeroDoc: numeroDocumento,
            plazoPrestamo: plazoPrestamo,
            tasasPrestamo: tazaPrestamo,
            montoPrestamo: montoPrestamo,
            montoPrestamoTot: montoPrestamoTot
        };

        $.getJSON("registrarprestamo", parametro, function (data) {
            if (data.resultado === "ok") {
                $('#NumeroDoc').val('');
                $('#plazoPrestamo').val('');
                $('#tazaPrestamo').val('');
                $('#montoPrestamo').val('');
                $('#montoPrestamoTot').val('');
                $('#prestamoRegis').modal('show');

                setTimeout(function () {
                    window.location.href = 'tablesPrestamos.html';
                }, 2000);
            } else {
                $('#NumeroDoc').val('');
                $('#plazoPrestamo').val('');
                $('#tazaPrestamo').val('');
                $('#montoPrestamo').val('');
                $('#montoPrestamoTot').val('');
                $('#prestamoFallido').modal('show');
            }
        });
    });
});
