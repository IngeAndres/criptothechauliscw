$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listartipodocumento',
        type: 'GET',
        success: function (response) {

            try {
                var documento = JSON.parse(response);
                var combobox = document.getElementById("Documento");

                for (var i = 0; i < documento.length; i++) {
                    var listarDocumento = documento[i];
                    if (listarDocumento && listarDocumento.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarDocumento.NOMBRE;
                        combobox.appendChild(option);
                      
                    }
                }
            } catch (error) {
              
            }
        },
        error: function (xhr, status, error) {
            console.log('Error', status, error);
        }
    });
});
