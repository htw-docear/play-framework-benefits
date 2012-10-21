package controllers;

import org.apache.commons.io.FileUtils;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sync.FileContent;
import views.html.Application.index;

import java.io.IOException;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Home"));
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
}