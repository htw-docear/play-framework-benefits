package sync;

import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.mvc.Results;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LongPollingPropertyChangeListener implements PropertyChangeListener {
    private final Results.Chunks.Out<String> out;
    private final FileContent fileContent;

    public LongPollingPropertyChangeListener(Results.Chunks.Out<String> out, FileContent fileContent) {
        this.out = out;
        this.fileContent = fileContent;
        out.write("{");
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        final String content = propertyChangeEvent.getNewValue().toString();
        ObjectNode result = Json.newObject();
        result.put("syncedText", content);
        result.put("refresh", true);
        out.write(result.toString().substring(1));//{ written before to get 55 seconds timeout instead of 30, see https://devcenter.heroku.com/articles/request-timeout
        out.close();
        fileContent.removePropertyChangeListener(this);
    }
}
