$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listarestadocuenta',
        type: 'GET',
        success: function (response) {

            try {
                var estado = JSON.parse(response);
                var combobox = document.getElementById("Estado");

                for (var i = 0; i < estado.length; i++) {
                    var listarEstado = estado[i];
                    if (listarEstado && listarEstado.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarEstado.NOMBRE;
                        combobox.appendChild(option);
                     
                    }
                }
            } catch (error) {
                console.log('Error ', error);
            }
        },
        error: function (xhr, status, error) {
            console.log('Error', status, error);
        }
    });
});
