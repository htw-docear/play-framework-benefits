package controllers;

import org.apache.commons.io.FileUtils;
import play.*;
import play.mvc.*;

import sync.FileContent;
import views.html.*;

import java.io.File;
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
                FileContent.set(fileContent);
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
        return ok(syncText.render(FileContent.get()));
    }
}