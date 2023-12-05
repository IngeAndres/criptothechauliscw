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

    $.ajax({
        url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rol/llenarcomboboxrol",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var selectTipoPrestamo = $('#TipoPrestamos');
            $.each(data.Roles, function (index, item) {
                selectTipoPrestamo.append('<option value="' + item.ID + '">' + item.NOMBRE + '</option>');
            });

        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText);
        }
    });





    $("#btnInsertar").click(function () {

        var tipoPrestamo = $('#TipoPrestamos').val();
        let nombPersona = $("#nombPersona").val();

        if (!nombPersona

                ) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }
        let parametros = {
            tipoPrestamo: tipoPrestamo,
            nombPersona: nombPersona
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rolusuario/asignarrol",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            headers: {
                Authorization: `Bearer ${token}`
            },
            success: function (data) {
                if (data.resultado === "ok") {
                    if (data.success === true) {
                        $("#modalRegistroCompletado").modal('show');
                        setTimeout(function () {
                            $("#modalRegistroCompletado").modal('hide');
                            setTimeout(function () {
                                window.location.href = "tablaRol.html";
                            }, 1000);
                        }, 2000);
                    } else {
                        alert("Error al registrar el rol");
                    }
                } else if (data.resultado === "error") {
                    $("#modalSesionExpirada").modal('show');
                } else if (data.resultado === "Usuario ya registrado en Rolusuario") {
                    alert("El usuario ya está registrado en Rolusuario");
                    // Puedes agregar aquí cualquier lógica adicional si se requiere
                }
            }
        });
    });
    
    $("#btnQuitar").click(function () {

        let nombPersona = $("#nombUsuario").val();

        if (!nombPersona

                ) {
            $("#modalCamposIncompletos").modal('show');
            return;
        }
        let parametros = {
            nombPersona: nombPersona
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.rolusuario/quitarrol",
            contentType: "application/json",
            data: JSON.stringify(parametros),
            dataType: "json",
            headers: {
                Authorization: `Bearer ${token}`
            },
            success: function (data) {
                if (data.resultado === "ok") {
                    if (data.success === true) {
                        $("#modalRegistroCompletado").modal('show');
                        setTimeout(function () {
                            $("#modalRegistroCompletado").modal('hide');
                            setTimeout(function () {
                                window.location.href = "tablaRol.html";
                            }, 1000);
                        }, 2000);
                    } else {
                        alert("Error al registrar el rol");
                    }
                } else if (data.resultado === "error") {
                    $("#modalSesionExpirada").modal('show');
                } else if (data.resultado === "Usuario ya registrado en Rolusuario") {
                    alert("El usuario ya está registrado en Rolusuario");
                    // Puedes agregar aquí cualquier lógica adicional si se requiere
                }
            }
        });
    });
});
