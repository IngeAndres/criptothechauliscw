$(document).ready(function () {
    let tp = sessionStorage.getItem("Ptipo");
    limpiarCampos();
    mostrarDetalles();
    $.ajax({
        "url": "http://localhost:8080/CriptoTheChaulis/webresources/dto.prestamo/llenarcombobox",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var selectTipoPrestamo = $('#TipoPrestamos');
            var selectTipoComprobante = $('#TipoComprobante');
            var selectTipoInformacion = $('#TipoInformacion');
            $.each(data.Tipoprestamos, function (index, item) {
                if (item.CATEGORIA === tp) {
                    selectTipoPrestamo.append('<option value="' + item.ID + '">' + item.NOMBRE + '</option>');
                }
            });
            $.each(data.Tipocomprobante, function (index, item) {
                selectTipoComprobante.append('<option value="' + item.IDT + '">' + item.NOMBRET + '</option>');
            });
            $.each(data.Tipoinformacion, function (index, item) {
                selectTipoInformacion.append('<option value="' + item.IDI + '">' + item.NOMBREI + '</option>');
            });
        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText);
        }
    });

    function mostrarDetalles() {

        let tp = sessionStorage.getItem("Ptipo");
        $(".card-header").find("h1").text("Préstamo " + tp);


    }


    $('#btnConfirmarPres').click(function (event) {

        event.preventDefault();

        var tipoPrestamo = $('#TipoPrestamos').val();
        var cuentaNum = $('#CuentaNum').val();
        var tipoComprobante = $('#TipoComprobante').val();
        var tipoinformacion = $('#TipoInformacion').val();
        var cuotasAdicionales = $('#siNo').is(':checked');
        var monedaSeleccionada = $('input[name="moneda"]:checked').val();
        var monto = $('#Monto').val();
        var tasa = $('#Tasa').val();
        var tiempo = $('#Tiempo').val();
        var tiempopago = $('#TiempoFecha').val();
        var charCuotasAdicionales;
        if (cuotasAdicionales) {
            charCuotasAdicionales = 's';
        } else {
            charCuotasAdicionales = 'n';
        }


        let parametro = {
            IdTipoInformacionBien: tipoinformacion,
            CuotasAdicionales: charCuotasAdicionales,
            Monto: monto,
            Moneda: monedaSeleccionada,
            Tasa: tasa / 100,
            Tiempo: tiempo,
            IdTipoPrestamo: tipoPrestamo,
            NumeroCuenta: cuentaNum,
            IdTipoComprobante: tipoComprobante,
            FechaPago: tiempopago
        };

        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/CriptoTheChaulis/webresources/dto.prestamo/insertarPrestamo",
            contentType: "application/json",
            data: JSON.stringify(parametro),
            dataType: "json",
            success: function (data) {
                if (data.resultado === "ok") {
                    sessionStorage.setItem("idDetalle", data.idDetalle);
                    sessionStorage.setItem("tipP", data.TipoPrest);
                    window.location.href = 'tablaAporteMensual.html';
                } else {
                    limpiarCampos();
                    alert("Error");
                }
            }

        });
      
    });
});

function limpiarCampos() {
    $('#TipoPrestamos').val('');
    $('#CuentaNum').val('');
    $('#TipoComprobante').val('');
    $('#TipoInformacion').val('');
    $('#siNo').prop('checked', false);
    $('input[name="moneda"]').prop('checked', false);
    $('#Monto').val('');
    $('#Tasa').val('');
    $('#Tiempo').val('');

}