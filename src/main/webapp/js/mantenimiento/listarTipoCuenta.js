$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    $.ajax({
        url: '/CriptoTheChaulisCW/listartipocuenta',
        type: 'GET',
        success: function (response) {
            try {
                var tipoCuenta = JSON.parse(response);
                var combobox = document.getElementById("TipoCuenta");

                for (var i = 0; i < tipoCuenta.length; i++) {
                    var listarTipoCuenta = tipoCuenta[i];
                    if (listarTipoCuenta && listarTipoCuenta.NOMBRE) {
                        var option = document.createElement("option");
                        option.text = listarTipoCuenta.NOMBRE;
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
