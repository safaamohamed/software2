package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserServices {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		System.out.println("Controller : " + uname + " " + pass + " " + email);
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	@POST
	@Path("/addfriendService")
	public String addfriendService(@FormParam("uname") String uname,@FormParam("rname") String rname) {
		JSONObject object = new JSONObject();
		UserEntity user =new UserEntity("", "", "");
		user.saveaddfriend(uname,rname);
		//user.saveaddfriend();
		object.put("Status", "OK");
			

		return object.toString();

	}
	@POST
	@Path("/sendmessageService")
	public String sendmessageService(@FormParam("uname") String uname,@FormParam("rname") String rname,@FormParam("message") String message) {
		JSONObject object = new JSONObject();
		UserEntity user =new UserEntity("", "", "");
		user.savemessage(uname,rname,message);
		object.put("Status", "OK");
		
		return object.toString();

	}

	@POST
	@Path("/acceptfriendService")
	public String acceptfriendService(@FormParam("uname") String uname,@FormParam("rname") String rname) {
		JSONObject object = new JSONObject();
		UserEntity user =new UserEntity("", "", "");
		user.acceptfriend(uname,rname);
		//user.saveaddfriend();
		
		
	
		object.put("Status", "OK");
			

		return object.toString();

	}

	
	@POST
	@Path("/showmessageService")
	public String showmessageService(@FormParam("uname") String uname,@FormParam("rname") String rname) {
		JSONObject object = new JSONObject();
		UserEntity user =new UserEntity("", "", "");
		user.showmessage(uname,rname);
		object.put("Status", "OK");
			

		return object.toString();

	}


@POST
@Path("/searchService")
public String searchService(@FormParam("uname") String uname) {
	UserEntity user =new UserEntity("", "", "");
	ArrayList<UserEntity> matches = user.search(uname);
	JSONArray returnedjson = new JSONArray ();
	
	for(int i=0 ; i< matches.size(); i++){
		JSONObject object = new JSONObject();
		object.put("uname", matches.get(i).getName());
		object.put("email", matches.get(i).getEmail());
		
		returnedjson.add(object);
		System.out.print("helloooooooooOOOOOOOOoooooo");
	}
	return returnedjson.toString();
}
}

