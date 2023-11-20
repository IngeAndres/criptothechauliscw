$(document).ready(function () {
    let idDetalle = sessionStorage.getItem("idDetalle");
    let parametro = {
        idDetallePres: idDetalle
    };

    let Meses;
    let Monto;
    let Taza;
    let Fechad;

    $.getJSON("listaraportemensual", parametro, function (data) {
        if (data.resultado === "ok") {

            Meses = data.Meses;
            Monto = data.Monto;
            Taza = data.Tasa;
            Fechad = data.Fecha;
            llenarTabla(Meses, Monto, Taza,Fechad);
        } else {
            alert("error");
        }
    });
});

function calcularCuota(monto, tasa, tiempo) {
    var tem = (tasa / 12); // Convertir la tasa a decimal (porcentaje a fracción)
    var n = tiempo;

    var cuota = (monto * (tem * Math.pow((1 + tem), n))) / (Math.pow((1 + tem), n) - 1);

    return cuota;
}
function llenarTabla(Meses, Monto, Taza,fechita) {
    var tabla = $('#dataTable tbody');
    var fechaActual = moment(fechita);
    console.log(fechaActual)
    var saldo = Monto;
    var tasaMensual = Taza / 12;
    var sumaT = 0;
    var sumaI = 0;
    tabla.empty();

    for (var i = 1; i <= Meses + 1; i++) {
        var fechaVencimiento = fechaActual.format("DD/MM/YYYY");

        var interes = saldo * tasaMensual; // Interés
        var cuota = calcularCuota(Monto, Taza, Meses); // Cuota mensual
        console.log(cuota);
        var amortizacion = cuota - interes; // Amortización
         
        if (i !== Meses + 1) {
            var nuevaFila = '<tr>' +
                    '<td>' + i + '</td>' +
                    '<td>' + fechaVencimiento + '</td>' +
                    '<td>' + amortizacion.toFixed(3) + '</td>' +
                    '<td>' + interes.toFixed(3) + '</td>' +
                    '<td>' + cuota.toFixed(3) + '</td>' +
                    '<td>' + saldo.toFixed(3) + '</td>' +
                    '</tr>';
            tabla.append(nuevaFila);

            saldo -= amortizacion; // Actualizar el saldo para el próximo mes
            fechaActual.add(1, 'months');
            sumaT = sumaT + amortizacion;
            sumaI = sumaI + interes;
        } else {

            var ultimaFila = '<tr>' +
                    '<td>' + "" + '</td>' +
                    '<td>' + "Total" + '</td>' +
                    '<td>' + sumaT.toFixed(3) + '</td>' +
                    '<td>' + sumaI.toFixed(3) + '</td>' +
                    '<td>' + "" + '</td>' +
                    '<td>' + saldo.toFixed(3) + '</td>' +
                    '</tr>';
            tabla.append(ultimaFila);

        }

    }
}
