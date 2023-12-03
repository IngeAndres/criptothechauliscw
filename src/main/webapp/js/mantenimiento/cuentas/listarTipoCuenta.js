$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.tipocuenta/listartipocuenta",
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            try {
                var listado = response;
                var combobox = document.getElementById("denoTipoCuenta");

                for (var i = 0; i < listado.length; i++) {
                    var item = listado[i];
                    if (item && item.denoTipoCuenta) {
                        var option = document.createElement("option");
                        option.text = item.denoTipoCuenta;
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
