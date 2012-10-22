package controllers;

import akka.util.Duration;
import play.Logger;
import play.Play;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import sync.FileContent;
import sync.LongPollingPropertyChangeListener;
import views.html.LongPolling.index;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LongPolling extends Controller {

    public static Result index() {
        return ok(index.render(FileContent.getInstance().getContent()));
    }

    public static Result feed() {
        Results.Chunks<String> chunks = new Results.StringChunks() {
            public void onReady(final Results.Chunks.Out<String> out) {
                final String id = UUID.randomUUID().toString();
                Logger.debug("long polling: added client " + id);
                final FileContent fileContent = FileContent.getInstance();
                fileContent.addPropertyChangeListener(new LongPollingPropertyChangeListener(out, fileContent));
                //on disconnect does not work
                //https://play.lighthouseapp.com/projects/82401/tickets/654
                final Integer timeout = Play.application().configuration().getInt("longpolling.serverside.timeout");
                Akka.system().scheduler().scheduleOnce(
                        Duration.create(timeout, TimeUnit.MILLISECONDS),
                        new Runnable() {
                            public void run() {
                                out.close();
                            }
                        }
                );
            }
        };
        return ok(chunks);
    }
}
