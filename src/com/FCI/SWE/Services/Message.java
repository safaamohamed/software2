package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

//import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyContainer;
import com.google.appengine.api.datastore.Query;

public class Message implements MassageInterface
{
		public ArrayList<Users> observers = new ArrayList<Users>();
		public String SendFrom  ;
		public void notifyObservers()
		{
			for(int i = 0 ; i < observers.size(); i++){
				observers.get(i).update(this);
			}
		}
		@POST
		@Path("/SendMessageService")
		public Boolean sendMessageService(@FormParam("SendFrom") String from_name, @FormParam("Receivers") String to_name,
				@FormParam("MessageText") String messageText) { 
			System.out.println("1");
			to_name += ',' ; to_name += from_name ;
			String delimeter = "[,]" , recev;
			String[] all_to_ids = to_name.split(delimeter) ; 
			
			Boolean ok = false;
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query userQuery = new Query("users");
			PreparedQuery pq = datastore.prepare(userQuery);	
			
			for(int i = 0; i < all_to_ids.length; i ++)
			{
				ok = false ;
				for (Entity entity : pq.asIterable()) 
				{
					if (entity.getProperty("name").toString().equals(all_to_ids[i])) 
					{
						ok = true ;
						break ;
					}
				}
				if(!ok) return false ; 
			}
			Query conQuery = new Query("Conversation");
			PreparedQuery pconv = datastore.prepare(conQuery);			
			List<Entity> list = pconv.asList(FetchOptions.Builder.withDefaults());		
			
			for(Entity entity: pconv.asIterable())
			{			
				recev = entity.getProperty("Receivers").toString() ;
				String[] receivers = recev.split(delimeter) ;
				if(all_to_ids.length == receivers.length)
				{
					ok = false ;
					for(int i = 0;i < all_to_ids.length; i ++)
					{
						ok = false ;
						for(int j = 0; j < receivers.length; j ++)
						{
							if(all_to_ids[i] == receivers[j])
							{
								ok = true ;
								break ;
							}
						}
						if(!ok) break ;
						
					}	
					if(ok)
					{						
						String messg = entity.getProperty("MessageText").toString() ;
						messg += "&" ;
						messg += "(" + from_name + ")-" ;
						messg += messageText ;
						entity.setProperty("MessageText", messg);										 
						break ;
					}										
				}
			}
			if(!ok)
			{
				Entity NewConv = new Entity("Conversation", list.size()+1);			    
			    NewConv.setProperty("ConversationID", list.size()+1);
			    NewConv.setProperty("Receivers ", to_name);	
			    messageText = "(" + from_name + ")-" + messageText ;			
				NewConv.setProperty("MessageText", messageText);
				datastore.put(NewConv);
			}
			this.SendFrom = from_name;
			return true;
		}
		public String getUpdate(Users observ)
		{
			System.out.print(SendFrom);
			return this.SendFrom ;
		}
		public void replyMessageService() 
		{
			
			System.out.print("");
		}

}
 