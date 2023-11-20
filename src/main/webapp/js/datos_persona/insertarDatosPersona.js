$(document).ready(function () {
//    const logi = sessionStorage.getItem("logi");
//    const token = sessionStorage.getItem("token");
//
//    if (!logi || !token) {
//        window.location.href = "index.html";
//        return;
//    }
//
//    document.getElementById('txtLogi').textContent = logi;

    $("#btnInsertarDatosPersonales").click(function () {
        let documento = $("#TipoDocumentoDatoPersona").val();
        let numerodoc = $("#NumDocDatoPersona").val();
        let ruc = $("#RUCDatoPersona").val();
        let nombre = $("#NombreDatoPersona").val();
        let paterno = $("#ApellidoPaternoDatoPersona").val();
        let materno = $("#ApellidoMaternoDatoPersona").val();
        let genero = $("#GeneroDatoPersona").val();
        let fecha = $("#fechaNacimiento").val();
        let direccion = $("#DireccionDatoPersona").val();
        let celular = $("#Celular").val();
        let email = $("#Email").val();
        let distrito = $("#Distrito").val();

        if (!documento || !numerodoc || !ruc || !nombre || !paterno || !materno || !genero || !fecha || !direccion || !celular || !email || !distrito) {
            $("#alertCamposIncompletos").modal('show');
            return;
        }

        let parametro = {
            documento: documento,
            numerodoc: numerodoc,
            ruc: ruc,
            nombre: nombre,
            paterno: paterno,
            materno: materno,
            genero: genero,
            fecha: fecha,
            direccion: direccion,
            celular: celular,
            email: email,
            distrito: distrito
        };

        console.log(parametro);

        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/insertarDatos",
            contentType: "application/json",
            data: JSON.stringify(parametro),
            dataType: "json",
            success: function (data) {
                if (data.success === true) {
                    $("#alertClienteRegistrado").modal('show');
                    setTimeout(function () {
                        $("#alertClienteRegistrado").modal('hide');
                        setTimeout(function () {
                            window.location.href = "tblDatosPersonales.html";
                        }, 1000);
                    }, 2000);
                } else {
                    alert("Error");
                }
            }
            
        });
    });
});
