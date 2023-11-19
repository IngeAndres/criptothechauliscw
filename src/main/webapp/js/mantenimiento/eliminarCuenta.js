$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    $('#dataTable').on('click', '.eliminarCuenta', function () {
        var codigoCuenta = $(this).data('codigocuenta');

        $('#eliminarRegistroModal').modal('show');


        $('#confirmarEliminar').on('click', function () {
            var estadoCuenta = 2;
            location.reload();
            $.ajax({
                type: 'POST',
                url: '/CriptoTheChaulisCW/eliminarcuenta',
                data: {codigocuenta: codigoCuenta, estadocuenta: estadoCuenta},
                success: function (data) {
                    if (data.resultado === "ok") {
                        location.reload();
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error al eliminar el cliente: ' + error);
                }
            });
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
