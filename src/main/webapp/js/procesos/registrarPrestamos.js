$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

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
