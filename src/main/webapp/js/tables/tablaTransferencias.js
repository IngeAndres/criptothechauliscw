$(document).ready(function () {
    const logi = getCookie("logi");
    const token = getCookie("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $('#dataTable').DataTable({
        "language": {
            "url": "/CriptoTheChaulisCW/json/es-ES.json"
        },
        "ajax": {
            "url": "/CriptoTheChaulisCW/listarTransferencias",
            "dataSrc": ""
        },
        "columns": [
            {"data": "CODTRANSFER"},
            {"data": "NUMECUENTAORIGEN"},
            {"data": "APPAORIGEN"},
            {"data": "APMAORIGEN"},
            {"data": "NOMBORIGEN"},
            {"data": "NUMECUENTADESTINO"},
            {"data": "NOMBDESTINO"},
            {"data": "APPADESTINO"},
            {"data": "APMADESTINO"},
            {"data": "MONTO"},
            {"data": "MONEDA"},
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
            }
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
