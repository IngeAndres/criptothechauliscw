$(document).ready(function () {
    $('#dataTable').on('click', '.eliminarCliente', function () {
        var codigoCliente = $(this).data('codigocliente');

        $('#eliminarRegistroModal').modal('show');

        $('#confirmarEliminar').on('click', function () {
            var estadocliente = 2;
            location.reload();
            $.ajax({
                type: 'POST',
                url: '/CriptoTheChaulisCW/eliminarcliente',
                data: {codigocliente: codigoCliente, estadocliente: estadocliente},
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
