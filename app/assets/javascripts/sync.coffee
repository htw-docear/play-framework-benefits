$ ->
  $output = $("#synced-text-file")
  if window.wsUrl != ""
    websocket = new WebSocket();
    websocket.onopen = (event) -> $("#ws-status").html("connection established")
    websocket.onmessage = (event) ->
      console.log("received " + event.data)
      $output.text(event.data)
    websocket.onerror = (event) -> $("#ws-status").html("NO WEBSOCKET CONNECTION ESTABLISHED")

