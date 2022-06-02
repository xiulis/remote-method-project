package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {
	 /** Start
	 * @param callback - notificare seriviu
	 * @param name - username
	 * @throws RemoteException
	 */
	void login(String name, IChatCallback callback) throws RemoteException;
	
	/**
	 * Stop
	 * @param callback - mesaj stop service
	 * @throws RemoteException
	 */
	void logout(IChatCallback callback) throws RemoteException;
	
	/**
	 * Restart
	 * @param callback - notificare application restarted
	 * @throws RemoteException
	 */
	void users(IChatCallback callback) throws RemoteException;
	
	/**
	 * Status
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void status(IChatCallback callback) throws RemoteException;
}
