$(document).ready(function () {
    var codigocuenta = localStorage.getItem("codigocuenta");
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $('#dataTableP').DataTable({
        "language": {
            "url": "/CriptoTheChaulisCW/json/es-ES.json"
        },
        "ajax": {
            "url": "/CriptoTheChaulisCW/listarprestamos",
            "dataSrc": "",
            "data": {
                codigocuenta: codigocuenta
            }
        },
        "columns": [
            {"data": "CODIGOPRESTAMO"},
            {"data": "PLAZO"},
            {"data": "TASAS"},
            {"data": "TOTAL"},
            {
                "data": "FECHA",
                "render": function (data) {
                    var date = new Date(data);
                    var fechaFormateada = date.toLocaleDateString("es-ES", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit",
                        hour: "2-digit",
                        minute: "2-digit",
                        second: "2-digit"
                    });
                    return fechaFormateada;
                }
            },
            {"data": "TOTALPRESTAMO"}
        ]
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
