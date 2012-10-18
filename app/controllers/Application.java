package controllers;

import org.apache.commons.io.FileUtils;
import play.*;
import play.libs.Comet;
import play.libs.F;
import play.mvc.*;

import sync.CometPropertyChangeListener;
import sync.FileContent;
import sync.WebSocketPropertyChangeListener;
import views.html.*;

import java.io.IOException;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("Your new application is ready."));
  }

    public static Result uploadSync() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("file");
        if (filePart != null) {
            try {
                final String fileContent = FileUtils.readFileToString(filePart.getFile());
                FileContent.getInstance().setContent(fileContent);
                return ok("File uploaded");
            } catch (IOException e) {
                final String message = "can't open uploaded file";
                Logger.error(message, e);
                return badRequest(message);
            }
        } else {
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }
    }

    public static Result displaySyncedTextFile() {
        return ok(syncText.render(FileContent.getInstance().getContent()));
    }

    public static WebSocket<String> updateFeed() {
        return new WebSocket<String>() {
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
                FileContent.getInstance().addPropertyChangeListener(new WebSocketPropertyChangeListener(out));
            }
        };
    }

    public static Result cometUpdateFeed() {
        Comet comet = new Comet("parent.cometMessage") {
            public void onConnected() {
                FileContent.getInstance().addPropertyChangeListener(new CometPropertyChangeListener(this));
            }
        };

        return ok(comet);
    }
}