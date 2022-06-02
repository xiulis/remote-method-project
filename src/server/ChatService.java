package server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.IChat;
import common.IChatCallback;

public class ChatService implements IChat{
	
	private Map<String, IChatCallback> users = new HashMap<String, IChatCallback>();
	private Map<String, String> status = new HashMap<String, String>();
	private String list="";
	
	public ChatService(Registry registry, String name) throws RemoteException {
		registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
	}
	
	@Override
	public void login(String name, IChatCallback callback) throws RemoteException {
		if(users.containsKey(name)) {
			status.put(name, "active");
			callback.onAccept("Welcome back "+name.toString());
			users.entrySet().forEach(entry -> {
				try {
					if(!entry.getKey().equals(name))
					entry.getValue().onAccept(name.toString()+" is active now.");
				} catch (RemoteException e) {
				}
			});
		} else {
			callback.onAccept("Connected to chat");
			users.entrySet().forEach(entry -> {
				try {
					if(!entry.getKey().equals(name))
					entry.getValue().onAccept(name.toString()+" is active now.");
				} catch (RemoteException e) {
				}
			});
			status.put(name, "active");
			users.put(name, callback);
		}
		
	}


	@Override
	public void logout(IChatCallback callback) throws RemoteException {
		final Optional<String> name = getName(callback);
		if (name.isPresent()) {
			status.put(name.get(), "inactive");
			users.entrySet().forEach(entry -> {
				try {
					if(!entry.getKey().equals(name.get())) {
						if(status.get(entry.getKey()).equals("active"))
						entry.getValue().onStatusChange(name.get().toString()+" left the chat");
					}
				} catch (RemoteException e) {
				}
			});
			callback.onLogout("You left the chat.");
		}
		
	}


	@Override
	public void users(IChatCallback callback) throws RemoteException {
		list="";
		status.keySet().forEach((e) -> {
			if(status.get(e).equals("active"))
			list+=e.toString()+", ";
		});
		if(list.equals("")) {
			callback.onUsers("No active users.");
		} else {
			callback.onUsers(list); 
		}
		
	}


	@Override
	public void status(IChatCallback callback) throws RemoteException {
		final Optional<String> name = getName(callback);
		if(name.isPresent()) {
			String state =status.get(name.get());
			callback.onStatus(state);
		}
		
	}
	
	// returneaza entry-ul care are valoarea egala cu callbackul cautat; retunreaza
		// numele clientului
		private Optional<String> getName(IChatCallback callback) {
			return users.entrySet().stream().filter(entry -> entry.getValue().equals(callback)).map(entry -> entry.getKey())
					.findAny();
		}

	

}
