package edu.stevens.cs549.dhts.remote;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.websocket.Session;

import edu.stevens.cs549.dhts.main.IShell;
import edu.stevens.cs549.dhts.main.LocalShell;

/**
 * Maintain a stack of shells.
 * @author dduggan
 *
 */
public class SessionManager {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SessionManager.class.getCanonicalName());
	
	public static final String ACK = "ACK";
	
	private static final SessionManager SESSION_MANAGER = new SessionManager();
	
	public static SessionManager getSessionManager() {
		return SESSION_MANAGER;
	}
	
	private Lock lock = new ReentrantLock();
	
	private ControllerServer currentServer;
	private ControllerServer newServer;
	
	
	public boolean isSession() {
		return currentServer != null;
	}

	public Session getCurrentSession() {
		return currentServer != null ? currentServer.getSession() : null;
	}

	public boolean setCurrentSession(ControllerServer server) {
		lock.lock();
		try {
			if (currentServer == null) {
				currentServer = server;
				return true;
			} else {
				newServer = server;
				return false;
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void acceptSession() throws IOException {
		lock.lock();
		try {
			/*
			 *  TODO We are accepting a remote control request.  Push a local shell with a proxy context
			 *  on the shell stack and flag that initialization has completed.  Confirm acceptance of the 
			 *  remote control request by sending an ACK to the client.  The CLI of the newly installed shell
			 *  will be executed by the underlying CLI as part of the "accept" command.
			 */
			ShellManager smanager = ShellManager.getShellManager();
			
			//Create proxy context
			ProxyContext remoteContext = ProxyContext.createProxyContext(getCurrentSession().getBasicRemote());
			//Get the local shell
			LocalShell localShell = smanager.getCurrentShell().getLocal();
			
			IShell proxyShell = LocalShell.createRemotelyControlled(localShell, remoteContext);
			
			smanager.addShell(proxyShell);
			//Accept and send the ack back to the client
			getCurrentSession().getBasicRemote().sendText(ACK);
			currentServer.endInitialization();
		} finally {
			lock.unlock();
		}
	}
	
	public void rejectSession() throws IOException {
		lock.lock();
		try {
			// TODO reject remote control request by closing the session (provide a reason!)
			//Session rejected may be two reasons: another pending session or manually rejected by user
			if(newServer != null){
				newServer.getSession().getBasicRemote().sendText("Already has another remote request being processing");
				newServer.getSession().close();
				newServer = null;
			}else{
				currentServer.getSession().getBasicRemote().sendText("Manually rejected by user!");
				currentServer.getSession().close();
				this.currentServer = null;
			}
			
		} finally {
			lock.unlock();
		}
	}
	
	public void closeCurrentSession() throws IOException {
		lock.lock();
		try {
			// TODO normal shutdown of remote control session (provide a reason!)
			getCurrentSession().getBasicRemote().sendText("Normally close the session: received quit!");
			getCurrentSession().close();
			currentServer = null;
		} finally {
			lock.unlock();
		}
	}

}
