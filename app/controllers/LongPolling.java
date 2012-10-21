package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import sync.FileContent;
import sync.LongPollingPropertyChangeListener;
import views.html.LongPolling.index;

public class LongPolling extends Controller {

    public static Result index() {
        return ok(index.render(FileContent.getInstance().getContent()));
    }

    public static Result feed() {
        Results.Chunks<String> chunks = new Results.StringChunks() {
            public void onReady(Results.Chunks.Out<String> out) {
                FileContent.getInstance().addPropertyChangeListener(new LongPollingPropertyChangeListener(out));
            }
        };
        return ok(chunks);
    }
}
