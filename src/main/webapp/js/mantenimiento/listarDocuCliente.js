$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listarcliente',
        type: 'GET',
        success: function (response) {
         

            try {
                var distritos = JSON.parse(response);
                var combobox = document.getElementById("DocumentoCliente");

                for (var i = 0; i < distritos.length; i++) {
                    var distrito = distritos[i];
                    if (distrito && distrito.NUMERODOC) {
                        var option = document.createElement("option");
                        option.text = distrito.NUMERODOC;
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