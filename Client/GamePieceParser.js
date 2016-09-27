
/**
Parses string from server and stores information in array of Block objects
*/
function parse(message)
{
  var blockObj = [];
  var block = message.split(";");
  for(index = 0; index < block.length; index++)
  {
    var posCol = block[index].split(":");
    var pos = posCol[0].split(",");

    blockObj.push({width: pos[0], height: pos[1], color: posCol[1]});
  }
  return blockObj;
}
