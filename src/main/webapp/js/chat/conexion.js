/* global CryptoJS */

$(document).ready(function () {
    openSocket();

    $("#mensaje").keypress(function (e) {
        if (e.which === 13) {
            $("#Enviar").click();
        }
    });

    $("#Enviar").click(function () {
        $.getJSON("solicitarprivado", function (data) {
            var mensajesGuardados = localStorage.getItem('chatMessages');
            console.log(mensajesGuardados);


            let privado = parseInt(data.resultado, 10);
            console.log("clave privada " + privado);

            $.getJSON("solicitarpublico", function (data) {

                let p = data.p;
                let g = data.g;
                let generadoServidor = data.generado;
                console.log("publico" + p + " public " + g);

                let generadoCliente = generarClaves(p, g, privado);
                console.log("generadoCliente " + generadoCliente);

                let claveCompartida = calcularClaveCompartida(privado, generadoServidor, p);

                let parametro = {generadoCliente: generadoCliente, claveCompartida: claveCompartida};

                $.getJSON("verificarclaves", parametro, function (data) {
                    if (data.resultado === "ok") {
                        send();
                    } else {
                        alert("error");
                    }
                });

            });
        });
    });
});

var webSocket;
var messages = document.getElementById("messages");

function openSocket() {
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        writeResponse("WebSocket ya está abierto");
        return;
    }


    webSocket = new WebSocket("ws://localhost:8080/CriptoTheChaulisCW/proceso");
    webSocket.onopen = function (event) {
        if (event.data === undefined)
            return;
        writeResponse(event.data);
    };
    webSocket.onmessage = function (event) {
        writeResponseJSON(event.data);
    };
    webSocket.onclose = function (event) {
        alert("conexion cerrada");
    };
}

function send() {
    try {
        var usuario = getCookie("logi");
        var text = $("#mensaje").val();
        var cifrado = CryptoJS.AES.encrypt(text, "notieneclave").toString();
        var fecha = new Date();

        var msj = {
            user: usuario,
            mensaje: cifrado,
            fecha: fecha
        };
        console.log(msj);
        var smsj = JSON.stringify(msj);

        webSocket.send(smsj);
        $("#mensaje").val("");
        guardarMensajeEnLocalStorage(msj);



    } catch (err) {
        console.error(err);
    }
}



function closeSocket() {
    try {
        webSocket.close();
    } catch (err) {
        console.error(err);
    }
}


function writeResponseJSON(text) {
    var msj = JSON.parse(text);
    var decrypted = CryptoJS.AES.decrypt(msj.mensaje, "notieneclave").toString(CryptoJS.enc.Utf8);

    var messageContainer = document.getElementById("message");

    var messageElement = document.createElement("div");
    messageElement.style.margin = "5px 0";
    messageElement.style.padding = "10px";
    messageElement.style.border = "1px solid #ccc";
    messageElement.style.borderRadius = "5px";
    messageElement.style.maxWidth = "70%";
    messageElement.style.clear = "both";
    messageElement.style.overflowWrap = "break-word";

    messageElement.style.float = (msj.user === getCookie("logi")) ? "right" : "left";
    messageElement.style.alignSelf = (msj.user === getCookie("logi")) ? "flex-end" : "flex-start";
    messageElement.style.background = (msj.user === getCookie("logi")) ? "#e0f7fa" : "#007bff";
    messageElement.style.color = (msj.user === getCookie("logi")) ? "black" : "white";
    messageElement.innerHTML = (msj.user === getCookie("logi")) ? "Yo : " + decrypted : msj.user + " : " + decrypted;

    messageContainer.appendChild(messageElement);
    messageContainer.appendChild(document.createElement("br"));
    guardarMensajeEnLocalStorage(msj);
    desplazar();
}




function generarClaves(p, g, a) {
    var A = (Math.pow(g, a)) % p;

    return A;


}

function guardarMensajeEnLocalStorage(mensaje) {

    var mensajesGuardados = localStorage.getItem('chatMessages');
    var mensajes = mensajesGuardados ? JSON.parse(mensajesGuardados) : [];
    mensajes.push(mensaje);
    localStorage.setItem('chatMessages', JSON.stringify(mensajes));
}


function calcularClaveCompartida(clavePropia, clavePublicaOtro, p) {
    var claveCompartida = Math.pow(clavePublicaOtro, clavePropia) % p;

    return claveCompartida;
}

function toggleDiv() {
    var div = document.getElementById('myDiv');
    div.classList.toggle('show');
    desplazar();
}


function desplazar() {
    var messageContainer = document.getElementById("message");
    messageContainer.scrollTop = messageContainer.scrollHeight;
}

document.addEventListener("click", function (event) {
    var myDiv = document.getElementById("myDiv");
    var circleContainer = document.querySelector(".circle-container");

    if (!myDiv.contains(event.target) && !circleContainer.contains(event.target)) {
        myDiv.classList.remove('show');
    }
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
