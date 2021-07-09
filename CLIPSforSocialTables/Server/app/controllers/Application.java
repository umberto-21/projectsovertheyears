package controllers;

import play.mvc.*;

public class Application extends Controller {
	
	public Result helloWorld(String id){
		return ok(id);
	}
}
