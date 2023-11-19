$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    $.ajax({
        url: '/CriptoTheChaulisCW/listarestadocuenta',
        type: 'GET',
        success: function (response) {

            try {
                var estado = JSON.parse(response);
                var combobox = document.getElementById("Estado");

                for (var i = 0; i < estado.length; i++) {
                    var listarEstado = estado[i];
                    if (listarEstado && listarEstado.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarEstado.NOMBRE;
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

    function getCookie(name) {
        const cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            const cookiePair = cookies[i].split("=");
            if (cookiePair[0] === name) {
                return cookiePair[1];
            }
        }
        return null;
    }
});
