$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listartipocuenta',
        type: 'GET',
        success: function (response) {
            console.log('Respuesta del servidor:', response);

            try {
                var tipoCuenta = JSON.parse(response);
                var combobox = document.getElementById("TipoCuenta");

                for (var i = 0; i < tipoCuenta.length; i++) {
                    var listarTipoCuenta = tipoCuenta[i];
                    if (listarTipoCuenta && listarTipoCuenta.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarTipoCuenta.NOMBRE;
                        combobox.appendChild(option);
                        console.log("Nombre del distrito: " + listarTipoCuenta.NOMBRE);
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
