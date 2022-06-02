package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculator extends Remote{
	/**
	 * Sum
	 * @param a - first operand
	 * @param b - second operand
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void sum(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException;
	
	/**
	 * Substract
	 * @param a - first operand
	 * @param b - second operand
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void substract(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException;
	
	/**
	 * Multiply
	 * @param a - first operand
	 * @param b - second operand
	 * @param callback - notificare client
	 * @throws RemoteException
	 */
	void multiply(Integer a, Integer b, ICalculatorCallback callback) throws RemoteException;
}
