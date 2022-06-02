package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatCallback extends Remote {
	/**
	 * Raspuns pentru login(), daca clientul e valid
	 * @param name - lista cu utilizatori conectati
	 * @throws RemoteException
	 */
	void onAccept(String notif) throws RemoteException;
	
	/**
	 * Raspuns pentru login() daca clientul e invalid
	 * @throws RemoteException
	 */
	void onDeny(String notif) throws RemoteException;
	
	/**
	 * Raspuns pentru logout(). s
	 * @throws RemoteException
	 */
	void onLogout(String notif) throws RemoteException;
	
	/**
	 * Raspuns pentru logout(). s
	 * @throws RemoteException
	 */
	void onStatusChange(String name) throws RemoteException;
	
	/**
	 * Raspuns pentru status(). arata statusul meu
	 * @throws RemoteException
	 */
	void onStatus(String state) throws RemoteException;
	
	/**
	 * Raspuns pentru users(). lista cu userii
	 * @param users - lista cu utilizatori conectati
	 * @throws RemoteException
	 */
	void onUsers(String users) throws RemoteException;
}
