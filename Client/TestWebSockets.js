var KEY     = { 27:'p', 32:'aD', 37:'lf', 38:'UP', 39:'rt', 40:'d', 90:'rCCw', 88:'rCw'},
    DIR     = { UP: 0, RIGHT: 1, DOWN: 2, LEFT: 3, MIN: 0, MAX: 3 };

var ws = new WebSocket("ws://localhost:8080");

window.onkeydown = function(event){
    sendData(KEY[event.keyCode]);
};

ws.onopen = function() {
    alert("Opened!");
    ws.send("Hello Server");
};

ws.onmessage = function (evt) {
    document.getElementById("demo").innerHTML += evt.data + "\n";
};

ws.onclose = function() {
    alert("Closed!");
};

ws.onerror = function(err) {
    alert("Error: " + err);
};

function sendData(message)
{
  ws.send(message);
}
