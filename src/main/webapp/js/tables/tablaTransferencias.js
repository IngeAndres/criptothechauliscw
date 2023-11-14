$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

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
            "url": "/CriptoTheChaulisCW/listartransferencias",
            "dataSrc": ""
        },
        "columns": [
            {"data": "CODTRANSFER"},
            {"data": "NUMECUENTAORIGEN"},
            {"data": "APPAORIGEN"},
            {"data": "APMAORIGEN"},
            {"data": "NOMBORIGEN"},
            {"data": "MONTO"},
            {"data": "NUMECUENTADESTINO"},
            {"data": "NOMBDESTINO"},
            {"data": "APPADESTINO"},
            {"data": "APMADESTINO"},
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
        ]
    });
});
