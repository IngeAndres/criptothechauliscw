$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.tipodocumento/listartipodocumento",
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            try {
                var listado = response;
                var combobox = document.getElementById("denoTipoDocumento");

                for (var i = 0; i < listado.length; i++) {
                    var tipoDocumento = listado[i];
                    if (tipoDocumento && tipoDocumento.denoTipoDocumento) {
                        var option = document.createElement("option");
                        option.text = tipoDocumento.denoTipoDocumento;
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
