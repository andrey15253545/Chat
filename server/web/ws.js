

var ws = new WebSocket("ws://localhost:8080/chat-1.0-SNAPSHOT/ws");

ws.onmessage = function (event) {
    console.log(event.data);
    document.getElementById("log").value += "[" + timestamp() + "] " + event.data + "\n";
};

document.getElementById("input").addEventListener("keyup", function (event) {
    if (event.keyCode === 13 && event.target.value!=="") {
        ws.send(event.target.value);
        event.target.value = "";
    }
});

function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}


