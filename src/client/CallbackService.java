package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import common.ICalculator;
import common.ICalculatorCallback;
import common.IChat;
import common.IChatCallback;
import common.IRecorder;
import common.IRecorderCallback;

public class CallbackService extends UnicastRemoteObject implements
IChatCallback, ICalculatorCallback, IRecorderCallback {
	private static final long serialVersionUID = 1L;
	private IChat stubChat;
	private ICalculator stubCalculator;
	private IRecorder stubRecorder;
	
	private IChatCallback chatCallback;
	private IRecorderCallback recorderCallback;
	private ICalculatorCallback calculatorCallback;

	public CallbackService(String host, int port, IChatCallback chat) throws RemoteException, MalformedURLException, NotBoundException {
		int localPort = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(localPort);
		} catch(RemoteException e) {
			registry = LocateRegistry.getRegistry(localPort);
		}
		if(registry!=null) {
			registry.rebind("chatCallback", this);
		}
		this.stubChat = (IChat) Naming.lookup(String.format("rmi://%s:%d/chat", host,port));
		this.chatCallback=chat;
	}
	
	public CallbackService(String host, int port, ICalculatorCallback calculator) throws RemoteException, MalformedURLException, NotBoundException {
		int localPort = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(localPort);
		} catch(RemoteException e) {
			registry = LocateRegistry.getRegistry(localPort);
		}
		if(registry!=null) {
			registry.rebind("calculatorCallback", this);
		}
		this.stubCalculator = (ICalculator) Naming.lookup(String.format("rmi://%s:%d/calculator", host,port));
		this.calculatorCallback=calculator;
	}

	public CallbackService(String host, int port, IRecorderCallback recorder) throws RemoteException, MalformedURLException, NotBoundException {
		int localPort = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(localPort);
		} catch(RemoteException e) {
			registry = LocateRegistry.getRegistry(localPort);
		}
		if(registry!=null) {
			registry.rebind("recorderCallback", this);
		}
		this.stubRecorder = (IRecorder) Naming.lookup(String.format("rmi://%s:%d/recorder", host,port));
		this.recorderCallback=recorder;
	}

	@Override
	public void onRecord(String notif) throws RemoteException {
		recorderCallback.onRecord(notif);
		
	}

	@Override
	public void onStop(String notif) throws RemoteException {
		recorderCallback.onRecord(notif);
		
	}

	@Override
	public void onPlay(String notif) throws RemoteException {
		recorderCallback.onPlay(notif);
		
	}

	@Override
	public void onSum(String result) throws RemoteException {
		calculatorCallback.onSum(result);
		
	}

	@Override
	public void onSubstract(String result) throws RemoteException {
		calculatorCallback.onSubstract(result);
		
	}

	@Override
	public void onMutiply(String result) throws RemoteException {
		calculatorCallback.onMutiply(result);
		
	}

	@Override
	public void onAccept(String name) throws RemoteException {
		chatCallback.onAccept(name);
		
	}

	@Override
	public void onDeny(String notif) throws RemoteException {
		chatCallback.onDeny(notif);
		
	}

	@Override
	public void onLogout(String notif) throws RemoteException {
		chatCallback.onLogout(notif);
		
	}

	@Override
	public void onStatusChange(String name) throws RemoteException {
		chatCallback.onStatusChange(name);
		
	}

	@Override
	public void onStatus(String state) throws RemoteException {
		chatCallback.onStatus(state);
		
	}

	@Override
	public void onUsers(String users) throws RemoteException {
		chatCallback.onUsers(users);
		
	}
	
	public void login(String name) throws RemoteException {
		stubChat.login(name, this);
	}
	
	public void logout() throws RemoteException {
		stubChat.logout(this);
	}
	
	public void users() throws RemoteException {
		stubChat.users(this);
	}
	
	public void status() throws RemoteException {
		stubChat.status(this);
	}
	
	public void sttatus() throws RemoteException {
		stubRecorder.sttatus(this);
	}
	
	public void record() throws RemoteException {
		stubRecorder.record(this);
	}
	
	public void stop() throws RemoteException {
		stubRecorder.stop(this);
	}
	
	public void play() throws RemoteException {
		stubRecorder.play(this);
	}
	
	public void sum(Integer a, Integer b) throws RemoteException {
		stubCalculator.sum(a,b,this);
	}
	
	public void subtract(Integer a, Integer b) throws RemoteException {
		stubCalculator.substract(a,b,this);
	}
	
	public void multiply(Integer a, Integer b) throws RemoteException {
		stubCalculator.multiply(a,b,this);
	}

	@Override
	public void onSttaus(String notif) throws RemoteException {
		recorderCallback.onSttaus(notif);
		
	}
	
	
}
