$(document).ready(function () {
    var mensajesGuardados = localStorage.getItem('chatMessages');

    if (mensajesGuardados) {
        var mensajes = JSON.parse(mensajesGuardados);
        mensajes.forEach(function (msj) {
            agregarMensajeAlDiv(msj);
        });
    } else {
        console.log("No hay mensajes almacenados en localStorage.");
    }
});

function agregarMensajeAlDiv(msj) {
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
}
