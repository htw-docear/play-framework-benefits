package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import sync.FileContent;
import sync.WebSocketPropertyChangeListener;
import views.html.WebSocketSync.index;

public class WebSocketSync extends Controller {
    public static Result index() {
        return ok(index.render(FileContent.getInstance().getContent()));
    }

    public static play.mvc.WebSocket<String> feed() {
        return new play.mvc.WebSocket<String>() {
            public void onReady(In<String> in, Out<String> out) {
                FileContent.getInstance().addPropertyChangeListener(new WebSocketPropertyChangeListener(out));
            }
        };
    }
}
