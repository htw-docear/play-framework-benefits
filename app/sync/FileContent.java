package sync;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FileContent {
    private static FileContent instance = new FileContent();
    private String content = "default file content";
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        final String oldContent = this.content;
        this.content = content;
        changes.firePropertyChange("content", oldContent, content);
    }

    public static FileContent getInstance() {
        return instance;
    }

    public void addPropertyChangeListener( PropertyChangeListener l )
    {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener( PropertyChangeListener l )
    {
        changes.removePropertyChangeListener(l);
    }
}
