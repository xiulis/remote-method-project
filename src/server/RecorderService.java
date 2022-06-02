package server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import common.IRecorder;
import common.IRecorderCallback;

public class RecorderService implements IRecorder {
	
private boolean isActive=false;
private String state = "inactive";
	
	public RecorderService(Registry registry, String name) throws RemoteException {
		registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
	}

	@Override
	public void record(IRecorderCallback callback) throws RemoteException {
		if(!isActive) {
			isActive=true;
			state="recording";
			callback.onRecord("Recording started..");
		} else {
			callback.onRecord("Can't record. Another recording in progress.");
		}
		
	}

	@Override
	public void stop(IRecorderCallback callback) throws RemoteException {
		if(isActive) {
			isActive=false;
			state="stopped";
			callback.onStop("Recording stopped.");
		} else {
			callback.onStop("No recording in progress.");
		}
		
	}

	@Override
	public void play(IRecorderCallback callback) throws RemoteException {
		if(isActive) {
			callback.onPlay("Can't play another record while recording. Please stop before.");
		} else {
			state="playing audio";
			callback.onPlay("Recording is playing.");
		}
		
	}

	@Override
	public void sttatus(IRecorderCallback callback) throws RemoteException {
		callback.onSttaus(state);
		
	}

}
