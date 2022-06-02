package server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import common.ICalculator;
import common.ICalculatorCallback;


public class CalculatorService implements ICalculator {
	
//private Map<String, ICalculatorCallback> users = new HashMap<String, ICalculatorCallback>();
	
	public CalculatorService(Registry registry, String name) throws RemoteException {
		registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
	}

	@Override
	public void sum(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException {
		Integer result = a+b;
		callback.onSum(result.toString());
		
	}


	@Override
	public void substract(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException {
		Integer result = a-b;
		callback.onSubstract(result.toString());
		
	}

	@Override
	public void multiply(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException {
		Integer result = a*b;
		callback.onMutiply(result.toString());
		
	}

}
