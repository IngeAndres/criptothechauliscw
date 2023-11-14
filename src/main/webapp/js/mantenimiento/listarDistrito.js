$(document).ready(function () {
    $.ajax({
        url: '/CriptoTheChaulisCW/listardistrito',
        type: 'GET',
        success: function (response) {
            console.log('Respuesta del servidor:', response);

            try {
                var distritos = JSON.parse(response);
                var combobox = document.getElementById("Distrito");
                
                for (var i = 0; i < distritos.length; i++) {
                    var distrito = distritos[i];
                    if (distrito && distrito.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = distrito.NOMBRE;
                        combobox.appendChild(option);
                        console.log("Nombre del distrito: " + distrito.NOMBRE);
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