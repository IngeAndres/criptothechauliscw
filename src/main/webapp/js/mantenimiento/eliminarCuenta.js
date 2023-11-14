$(document).ready(function () {
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
});
