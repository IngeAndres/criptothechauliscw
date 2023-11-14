$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listartipodocumento',
        type: 'GET',
        success: function (response) {
            console.log('Respuesta del servidor:', response);

            try {
                var documento = JSON.parse(response);
                var combobox = document.getElementById("Documento");

                for (var i = 0; i < documento.length; i++) {
                    var listarDocumento = documento[i];
                    if (listarDocumento && listarDocumento.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarDocumento.NOMBRE;
                        combobox.appendChild(option);
                        console.log("Nombre del distrito: " + listarDocumento.NOMBRE);
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
