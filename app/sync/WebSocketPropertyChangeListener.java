package sync;

import play.mvc.WebSocket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WebSocketPropertyChangeListener implements PropertyChangeListener {
    private final WebSocket.Out<String> out;

    public WebSocketPropertyChangeListener(WebSocket.Out<String> out) {
        this.out = out;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        out.write(propertyChangeEvent.getNewValue().toString());
    }
}
