package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.UserStories.index;

public class UserStories extends Controller {

    public static Result index() {
        return ok(index.render());
    }
}
