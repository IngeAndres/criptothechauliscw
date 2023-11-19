$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

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
