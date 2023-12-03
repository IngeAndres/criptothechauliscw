$(document).ready(function () {
    const usuario = getCookie("usuario");
    const token = getCookie("token");

    $.getJSON("/CriptoTheChaulisCW/validarsessionprin", function (data) {
        if (data.resultado === "ok") {
            console.log("ok");
        } else if (data.resultado === "error") {
            window.location.href = "index.html";
        }
    });

    document.getElementById('txtUsuario').textContent = usuario;

    $('#dataTableUsuarios').on('click', '.btnEliminar', function () {
        var idUsuario = $(this).data('idusuario');
        $('#modalEliminarRegistro').modal('show');
        $('#confirmarEliminar').on('click', function () {
            let parametro = {idUsuario: idUsuario};
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.usuario/eliminarusuario",
                contentType: "application/json",
                data: JSON.stringify(parametro),
                dataType: "json",
                headers: {
                    Authorization: `Bearer ${token}`
                },
                success: function (data) {
                    if (data.resultado === "ok") {
                        if (data.success === true) {
                            location.reload();
                        } else {
                            alert("Error");
                        }
                    } else if (data.resultado === "error") {
                        $("#modalSesionExpirada").modal('show');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error al eliminar el usuario: ' + error);
                }
            });
        });
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
