package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRecorderCallback extends Remote {
	/**
	 * Raspuns pentru record()
	 * @param notif - state
	 * @throws RemoteException
	 */
	void onRecord(String notif) throws RemoteException;
	
	/**
	 * Raspuns pentru stop() 
	 * @param notif - state
	 * @throws RemoteException
	 */
	void onStop(String notif) throws RemoteException;
	
	/**
	 * Raspuns pentru pause()
	 * @param notif - state
	 * @throws RemoteException
	 */
	void onPlay(String notif) throws RemoteException;
	/**
	 * Raspuns pentru status()
	 * @param notif - state
	 * @throws RemoteException
	 */
	void onSttaus(String notif) throws RemoteException;

}
