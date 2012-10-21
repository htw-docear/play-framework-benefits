package controllers;

import play.Logger;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import sync.FileContent;
import sync.LongPollingPropertyChangeListener;
import views.html.LongPolling.index;

import java.util.UUID;

public class LongPolling extends Controller {

    public static Result index() {
        return ok(index.render(FileContent.getInstance().getContent()));
    }

    public static Result feed() {
        Results.Chunks<String> chunks = new Results.StringChunks() {
            public void onReady(Results.Chunks.Out<String> out) {
                final String id = UUID.randomUUID().toString();
                Logger.debug("long polling: added client " + id);
                final FileContent fileContent = FileContent.getInstance();
                fileContent.addPropertyChangeListener(new LongPollingPropertyChangeListener(out, fileContent));
                out.onDisconnected(new DisconnectCallback(id));
            }
        };
        return ok(chunks);
    }

    private static class DisconnectCallback implements F.Callback0 {
        private final String id;

        public DisconnectCallback(String id) {
            this.id = id;
        }

        @Override
        public void invoke() throws Throwable {
            Logger.debug("long polling: disconnected to client " + id);
        }
    }
}
