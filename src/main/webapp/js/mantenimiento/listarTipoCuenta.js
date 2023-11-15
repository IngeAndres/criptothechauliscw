$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listartipocuenta',
        type: 'GET',
        success: function (response) {
           

            try {
                var tipoCuenta = JSON.parse(response);
                var combobox = document.getElementById("TipoCuenta");

                for (var i = 0; i < tipoCuenta.length; i++) {
                    var listarTipoCuenta = tipoCuenta[i];
                    if (listarTipoCuenta && listarTipoCuenta.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarTipoCuenta.NOMBRE;
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
