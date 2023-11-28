$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/listardocupersona",
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            try {
                var listado = response;
                var combobox = document.getElementById("docuPersona");

                for (var i = 0; i < listado.length; i++) {
                    var item = listado[i];
                    if (item && item.docuPersona) {
                        var option = document.createElement("option");
                        option.text = item.docuPersona;
                        combobox.appendChild(option);
                    }
                }
                
                $("#passUsuario").val(listado[0].docuPersona);
            } catch (error) {
                console.log('Error ', error);
            }
        },
        error: function (xhr, status, error) {
            console.log('Error', status, error);
        }
    });

    $("#docuPersona").change(function () {
        $("#passUsuario").val($(this).val());
    });
});
