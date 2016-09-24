var KEY     = { 27:'p', 32:'aD', 37:'lf', 38:'UP', 39:'rt', 40:'d', 90:'rCCw', 88:'rCw'};

var ws = new WebSocket("ws://localhost:8080");
var gameBoard = document.getElementById('gameBoard');


window.onkeydown = function(event){
    sendData(KEY[event.keyCode]);
};

ws.onopen = function() {
};

ws.onmessage = function (evt) {
    document.getElementById("demo").innerHTML += evt.data + "\n";
};

ws.onclose = function() {
    alert("Closed!");
};

ws.onerror = function(err) {
    alert("Error with Server: " + err);
};

function sendData(message)
{
  ws.send(message);
}
