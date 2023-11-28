$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.tipousuario/listartipousuario",
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            try {
                var listado = response;
                var combobox = document.getElementById("denoTipoUsuario");

                for (var i = 0; i < listado.length; i++) {
                    var item = listado[i];
                    if (item && item.denoTipoUsuario) {
                        var option = document.createElement("option");
                        option.text = item.denoTipoUsuario;
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
