package sync;

import play.mvc.Results;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LongPollingPropertyChangeListener implements PropertyChangeListener {
    private final Results.Chunks.Out<String> out;

    public LongPollingPropertyChangeListener(Results.Chunks.Out<String> out) {
        this.out = out;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        out.write(propertyChangeEvent.getNewValue().toString());
        out.close();
    }
}
