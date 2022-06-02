package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculatorCallback extends Remote {
	/**
	 * Raspuns pentru sum()
	 * @param names - lista cu utilizatori conectati
	 * @throws RemoteException
	 */
	void onSum(String result) throws RemoteException;
	
	/**
	 * Raspuns pentru multiply() 
	 * @throws RemoteException
	 */
	void onSubstract(String result) throws RemoteException;
	
	/**
	 * Raspuns pentru login(). Notifica toti utilizatorii conectati ca un nou client s-a conectat.
	 * @param name
	 * @throws RemoteException
	 */
	void onMutiply(String result) throws RemoteException;

}
