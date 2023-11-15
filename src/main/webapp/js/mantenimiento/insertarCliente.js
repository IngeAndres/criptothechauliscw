$(document).ready(function () {
    const logi = sessionStorage.getItem("logi");
    const token = sessionStorage.getItem("token");

    if (!logi || !token) {
        window.location.href = "index.html";
        return;
    }

    document.getElementById('txtLogi').textContent = logi;

    $("#btnInsertar").click(function () {
        let documento = $("#Documento").val();
        let numerodoc = $("#Nombre").val();
        let paterno = $("#ApellidoPaterno").val();
        let materno = $("#ApellidoMaterno").val();
        let nombre = $("#NombreCliente").val();
        let distrito = $("#Distrito").val();
        let direccion = $("#Direccion").val();
        let telefono = $("#Telefono").val();
        let estadocliente = 1;

        if (!documento || !numerodoc || !paterno || !materno || !nombre || !distrito || !direccion || !telefono) {
            $("#alertCamposIncompletos").modal('show');
            return;
        }

        let parametro = {
            documento: documento,
            numerodoc: numerodoc,
            paterno: paterno,
            materno: materno,
            nombre: nombre,
            distrito: distrito,
            direccion: direccion,
            telefono: telefono,
            estadocliente: estadocliente
        };

        $.getJSON("insertarcliente", parametro, function (data) {
            if (data.resultado === "ok") {
                $("#alertClienteRegistrado").modal('show');
                setTimeout(function () {
                    $("#alertClienteRegistrado").modal('hide');
                    setTimeout(function () {
                        window.location.href = "tablesClientes.html";
                    }, 1000);
                }, 2000);
            } else {
                alert("Error");
            }
        });
    });
});