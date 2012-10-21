package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import sync.CometPropertyChangeListener;
import sync.FileContent;
import views.html.CometSync.index;

public class CometSync extends Controller {
    public static Result index() {
        return ok(index.render(FileContent.getInstance().getContent()));
    }

    public static Result feed() {
        play.libs.Comet comet = new play.libs.Comet("parent.cometMessage") {
            public void onConnected() {
                FileContent.getInstance().addPropertyChangeListener(new CometPropertyChangeListener(this));
            }
        };
        return ok(comet);
    }
}
