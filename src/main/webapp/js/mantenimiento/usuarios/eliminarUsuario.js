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
