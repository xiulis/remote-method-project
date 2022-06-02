package client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

import common.ICalculatorCallback;
import common.IChatCallback;
import common.IRecorderCallback;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuListener;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class InterfaceClient extends Shell implements IChatCallback, IRecorderCallback, ICalculatorCallback {
	private Text text;
	private Label lbResultOutput;
	private CallbackService clientCalculator;
	private CallbackService clientRecorder;
	private CallbackService clientChat;
	private Group grpApplication;
	private String name;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			InterfaceClient shell = new InterfaceClient(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public InterfaceClient(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.BORDER);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					clientChat.logout();
					System.exit(0);
				} catch (RemoteException exception) {
				}
			}
		});

		setTouchEnabled(true);
		setLayout(null);

		Menu menuApps = new Menu(this, SWT.BAR);
		menuApps.setEnabled(true);
		setMenuBar(menuApps);

		Group grpCommands = new Group(this, SWT.BORDER);
		grpCommands.setText("Info commands");
		grpCommands.setBounds(10, 10, 551, 160);

		List listCommands = new List(grpCommands, SWT.BORDER | SWT.H_SCROLL);
		listCommands.setItems(new String[] {});
		listCommands.setBounds(20, 33, 499, 113);
		createContents();

		MenuItem mntmNewItem = new MenuItem(menuApps, SWT.NONE);
		mntmNewItem.setSelection(true);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listCommands.setItems(new String[] { "sum: calculator sum 1 1", "substract: calculator substract 3 4",
						"multiply: calculator multiply 12 3" });
			}
		});
		mntmNewItem.setText("calculator");

		MenuItem mntmNewItem_1 = new MenuItem(menuApps, SWT.NONE);
		mntmNewItem_1.setSelection(true);
		mntmNewItem_1.setID(1);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listCommands.setItems(new String[] { "login: chat login {name}", "logout: chat logout",
						"status: chat status", "users: chat users" });
			}
		});
		mntmNewItem_1.setText("chat");

		MenuItem mntmNewItem_2 = new MenuItem(menuApps, SWT.NONE);
		mntmNewItem_2.setSelection(true);
		mntmNewItem_2.setID(2);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listCommands.setItems(new String[] { "record: recorder record", "play: recorder play",
						"stop: recorder stop", "status: recorder status" });
			}
		});
		mntmNewItem_2.setText("recorder");

		Group grpCommand = new Group(this, SWT.NONE);
		grpCommand.setTouchEnabled(true);
		grpCommand.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		grpCommand.setText("Please type here");
		grpCommand.setBounds(10, 345, 551, 64);

		text = new Text(grpCommand, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		text.setBounds(10, 29, 531, 25);
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));

		grpApplication = new Group(this, SWT.NONE);
		grpApplication.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpApplication.setText(" Application: none");
		grpApplication.setBounds(10, 208, 551, 104);

		Label lblResult = new Label(grpApplication, SWT.NONE);
		lblResult.setAlignment(SWT.CENTER);
		lblResult.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblResult.setBounds(22, 41, 59, 30);
		lblResult.setText("Result:");

		lbResultOutput = new Label(grpApplication, SWT.BORDER);
		lbResultOutput.setAlignment(SWT.CENTER);
		lbResultOutput.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		lbResultOutput.setBounds(147, 41, 320, 31);
		lbResultOutput.setText("-");
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR && !text.getText().trim().isEmpty()) {
					String[] items = text.getText().strip().split("\\s");
					if (text.getText().contains("recorder")) {
						if ("play".equals(items[1])) {
							try {
								clientRecorder.play();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
						if ("stop".equals(items[1])) {
							try {
								clientRecorder.stop();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}

						}
						if ("record".equals(items[1])) {
							try {
								clientRecorder.record();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}

						}
						if ("status".equals(items[1])) {
							try {
								clientRecorder.sttatus();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						} else {
							grpApplication.setText("Application: none");
							lbResultOutput.setText("-");
						}
					}
					if (text.getText().contains("chat")) {
						if ("login".equals(items[1])) {
							if (items.length == 3) {
								if (items[2] != null) {
									try {
										name = items[2];
										clientChat.login(items[2]);
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
							}
						}
						if ("logout".equals(items[1])) {
							try {
								clientChat.logout();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
						if ("users".equals(items[1])) {
							try {
								clientChat.users();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
						if ("status".equals(items[1])) {
							try {
								clientChat.status();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}

						else {
							grpApplication.setText("Application: none");
							lbResultOutput.setText("-");
						}
					}
					if (text.getText().contains("calculator")) {
						int num1 = 0;
						int num2 = 0;
						if (items[2] != null && items[3] != null) {
							try {
								num1 = Integer.parseInt(items[2]);
								num2 = Integer.parseInt(items[3]);
							} catch (Exception er) {

							}
							if ("sum".equals(items[1])) {
								try {
									clientCalculator.sum(num1, num2);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
							}
							if ("substract".equals(items[1])) {
								try {
									clientCalculator.subtract(num1, num2);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}

							}
							if ("multiply".equals(items[1])) {
								try {
									clientCalculator.multiply(num1, num2);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
							}
						} else {
							grpApplication.setText("Application: none");
							lbResultOutput.setText("-");

						}
						
					} 
					
					if(text.getText().contains("exit")) {
						try {
							clientChat.logout();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}	
						System.exit(0);
					}
				else {
						grpApplication.setText("Application: none");
						lbResultOutput.setText("-");
					}

					text.setText("");
				}
			}
		});

		createContents();
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		String host = bundle.getString("host");
		int port = Integer.parseInt(bundle.getString("port"));
		try {
			this.clientCalculator = new CallbackService(host, port, (ICalculatorCallback) this);
			this.clientChat = new CallbackService(host, port, (IChatCallback) this);
			this.clientRecorder = new CallbackService(host, port, (IRecorderCallback) this);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		new Label(this, SWT.NONE);

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Remote Method Invocation - project networking");
		setSize(593, 524);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void onSum(String result) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: calculator");
			lbResultOutput.setText(result);

		});

	}

	@Override
	public void onSubstract(String result) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: calculator");
			lbResultOutput.setText(result);
		});

	}

	@Override
	public void onMutiply(String result) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: calculator");
			lbResultOutput.setText(result);
		});

	}

	@Override
	public void onRecord(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: recorder");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onStop(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: recorder");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onPlay(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: recorder");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onSttaus(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: recorder");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onAccept(String name) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(name);
		});

	}

	@Override
	public void onDeny(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onLogout(String notif) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(notif);
		});

	}

	@Override
	public void onStatusChange(String name) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(name);
		});

	}

	@Override
	public void onStatus(String state) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(state);
		});
	}

	@Override
	public void onUsers(String users) throws RemoteException {
		getDisplay().asyncExec(() -> {
			grpApplication.setText("Application: chat");
			lbResultOutput.setText(users);
		});

	}

}
