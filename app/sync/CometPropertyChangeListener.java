package sync;

import play.libs.Comet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CometPropertyChangeListener implements PropertyChangeListener {
    private final Comet comet;

    public CometPropertyChangeListener(Comet comet) {
        this.comet = comet;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        comet.sendMessage(propertyChangeEvent.getNewValue().toString());
    }
}
