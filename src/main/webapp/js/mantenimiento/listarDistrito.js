$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    $.ajax({
        url: '/CriptoTheChaulisCW/listardistrito',
        type: 'GET',
        success: function (response) {
            try {
                var distritos = JSON.parse(response);
                var combobox = document.getElementById("Distrito");

                for (var i = 0; i < distritos.length; i++) {
                    var distrito = distritos[i];
                    if (distrito && distrito.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = distrito.NOMBRE;
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