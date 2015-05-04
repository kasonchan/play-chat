/**
 * Created by kasonchan on 5/3/15.
 */

$(document).ready(function () {
  alert("Hi");
  $("#msg").hide();

  ws = new WebSocket("@routes.Chat.chatSocket(title).webSocketURL()")

  ws.onmessage = function (msg) {
    $("<li />").text(msg.data).appendTo("#msgs")
  }

  $("#chat").submit(function () {
    ws.send($("#msg").val())
    $("#msg").val("").focus()
    return false;
  })
})
