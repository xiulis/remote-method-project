package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRecorder extends Remote{
	/**
	 * Record sound
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void record(IRecorderCallback callback) throws RemoteException;
	
	/**
	 * Stop recording
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void stop(IRecorderCallback callback) throws RemoteException;
	
	/**
	 * Play audio
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void play(IRecorderCallback callback) throws RemoteException;
	/**
	 * Record sound
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void sttatus(IRecorderCallback callback) throws RemoteException;
}
