$(document).ready(function () {
    const idUsuario = getCookie("id");
    const usuario = getCookie("usuario");
    const token = getCookie("token");
    const auth = getCookie("auth");

    if (!idUsuario || !usuario || !token || !auth) {
        $("#sesionExpiradaModal").modal('show');
        return;
    }

    document.getElementById('txtUsuario').textContent = usuario;
    
    var urlParams = new URLSearchParams(window.location.search);
    var idPersona = urlParams.get('idPersona');
    let departamento;
    let provincia;
    let distrito;

    if (idPersona) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.datospersonales/obtenerdatospersonales",
            contentType: "application/json",
            data: idPersona,
            dataType: "json",
            headers: {
                    Authorization: `Bearer ${token}`
                },
            success: function (data) {
                departamento = data.datos.denoDepartamento;
                provincia = data.datos.denoProvincia;
                distrito = data.datos.denoDistrito;
                listarDepartamentos(departamento, provincia, distrito);
            },
            error: function () {
                alert("Error al obtener los datos del cliente.");
            }
        });
    } else {
        listarDepartamentos(null, null);
    }

    function listarDepartamentos(departamento, provincia, distrito) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.departamento/listardepartamento",
            contentType: "application/json",
            dataType: "json",
            success: function (response) {
                try {
                    var listado = response;
                    var combobox = document.getElementById("denoDepartamento");

                    for (var i = 0; i < listado.length; i++) {
                        var item = listado[i];
                        if (item && item.denoDepartamento) {
                            var option = document.createElement("option");
                            option.text = item.denoDepartamento;
                            combobox.appendChild(option);
                        }
                    }

                    if (departamento !== null) {
                        $("#denoDepartamento").val(departamento);
                    }

                    var primerDepartamento = departamento !== null ? departamento : listado[0].denoDepartamento;
                    listarProvincias(primerDepartamento, provincia, distrito);
                } catch (error) {
                    console.log('Error ', error);
                }
            },
            error: function (xhr, status, error) {
                console.log('Error', status, error);
            }
        });
    }

    function listarProvincias(denoDepartamento, denoProvincia, denoDistrito) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.provincia/listarprovincia",
            contentType: "application/json",
            data: denoDepartamento,
            dataType: "json",
            success: function (response) {
                try {
                    var listado = response;
                    var combobox = $("#denoProvincia");

                    combobox.empty();

                    for (var i = 0; i < listado.length; i++) {
                        var item = listado[i];
                        if (item && item.denoProvincia) {
                            combobox.append($('<option>', {
                                value: item.denoProvincia,
                                text: item.denoProvincia
                            }));
                        }
                    }

                    if (denoProvincia !== null) {
                        $("#denoProvincia").val(denoProvincia);
                    }

                    var primeraProvincia = denoProvincia !== null ? denoProvincia : listado[0].denoProvincia;
                    listarDistritos(primeraProvincia, denoDistrito);
                } catch (error) {
                    console.log('Error ', error);
                }
            },
            error: function (xhr, status, error) {
                console.log('Error', status, error);
            }
        });
    }

    function listarDistritos(denoProvincia, denoDistrito) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.distrito/listardistrito",
            contentType: "application/json",
            data: denoProvincia,
            dataType: "json",
            success: function (response) {
                try {
                    var listado = response;
                    var combobox = $("#denoDistrito");

                    combobox.empty();

                    for (var i = 0; i < listado.length; i++) {
                        var item = listado[i];
                        if (item && item.denoDistrito) {
                            combobox.append($('<option>', {
                                value: item.denoDistrito,
                                text: item.denoDistrito
                            }));
                        }
                    }
                    
                    if (denoDistrito !== null) {
                        $("#denoDistrito").val(denoDistrito);
                    }
                } catch (error) {
                    console.log('Error ', error);
                }
            },
            error: function (xhr, status, error) {
                console.log('Error', status, error);
            }
        });
    }

    $("#denoDepartamento").change(function () {
        let denoDepartamento = $(this).val();
        listarProvincias(denoDepartamento, null);
    });

    $("#denoProvincia").change(function () {
        let denoProvincia = $(this).val();
        listarDistritos(denoProvincia);
    });
});
