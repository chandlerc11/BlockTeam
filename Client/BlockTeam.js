var KEY = { 27:'p', 32:'aD', 37:'lf', 38:'r', 39:'rt', 40:'d', 90:'rCCw', 88:'rCw', 13:'st'};
var ws = new WebSocket("ws://localhost:8080");
var gameBoard = document.getElementById('gameBoard');

window.onkeydown = function(event){
    sendData(KEY[event.keyCode]);
};

ws.onopen = function() {
    alert("Welcome to BlockTeam!");
};

ws.onmessage = function (evt) {
    if(evt.data == "Game Over!")
    {
        alert("Game Over!");
    }
    else {
        var blocks = parse(evt.data);
        var canvas = document.getElementById('gameBoard');
        var context = canvas.getContext('2d');

        context.clearRect(0, 0, canvas.width, canvas.height);

        for(index = 0; index < blocks.length; index++)
        {
            var block = blocks[index];
            context.beginPath();
            context.rect(block.width * 36, (19 - block.height) * 36, 36, 36);
            context.fillStyle = block.color;
            context.fill();
            context.lineWidth = 0;
            context.strokeStyle = 'black';
            context.stroke();
        }
    }
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
