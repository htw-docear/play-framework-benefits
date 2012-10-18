$ ->
  $output = $("#synced-text-file")
  websocket = new WebSocket(window.wsUrl);
  websocket.onopen = (event) -> $("#ws-status").html("connection established")
  websocket.onmessage = (event) ->
     console.log("received " + event.data)
     $output.text(event.data)
  websocket.onerror = (event) -> alert("WebSocket error " + event)