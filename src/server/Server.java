package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("settings");
			int port = Integer.parseInt(bundle.getString("port"));
			
			new ChatService(getRegistry(port), "chat");
			new CalculatorService(getRegistry(port), "calculator");
			new RecorderService(getRegistry(port), "recorder");
			
			System.out.println("Server is runnig, type 'exit' to close it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine(); 
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					}
				}
			}
		} catch (NumberFormatException | RemoteException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	private static Registry getRegistry(int port) throws RemoteException {
		try {
			return LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			return LocateRegistry.getRegistry(port);
		}
	}

}
