package com.FCI.SWE.Services;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Users implements Oserver{
	public String mesg ;
	
	public boolean update(Message obj)
	{
		String sender = obj.getUpdate(this) ;
		if(sender != null) 
		{
			String type = "Message";
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query notiQuery = new Query("Notification");
			PreparedQuery preq = datastore.prepare(notiQuery);
			List<Entity> list = preq.asList(FetchOptions.Builder.withDefaults());
			Entity noti = new Entity("Notification", list.size() + 1);
			noti.setProperty("Type", type);
			noti.setProperty("Sender", obj.SendFrom);
			datastore.put(noti);
			return true;
		}
		return false;
	}
	
	public void setMassage(Message obj) 
	{
        this.mesg= obj.toString();
    }
	
	
}
