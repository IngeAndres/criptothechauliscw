$(document).ready(function () {
    const usuario = getCookie("usuario");
    const token = getCookie("token");

    $.getJSON("validarsessionprin", function (data) {
        if (data.resultado === "ok") {
            $("body").show();
            document.getElementById('txtUsuario').textContent = usuario;
        } else {
            window.location.href = "index.html";
            return;
        }
    });

    $('#btnPPersonales').click(function () {
        window.location.href = 'insertarPrestamos.html';
        sessionStorage.setItem("Ptipo", "Personal");

    });

    $('#btnPVivienda').click(function () {
        window.location.href = 'insertarPrestamos.html';
        sessionStorage.setItem("Ptipo", "Mi Vivienda");

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

