package edu.stevens.cs549.dhts.main;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBElement;

import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import edu.stevens.cs549.dhts.activity.DHTBase;
import edu.stevens.cs549.dhts.activity.NodeInfo;
import edu.stevens.cs549.dhts.activity.DHTBase.Failed;
import edu.stevens.cs549.dhts.activity.IDHTNode;
import edu.stevens.cs549.dhts.resource.TableRep;
import edu.stevens.cs549.dhts.resource.TableRow;
import edu.stevens.cs549.dhts.state.IRouting;

public class WebClient {

	private Logger log = Logger.getLogger(WebClient.class.getCanonicalName());

	private void error(String msg) {
		log.severe(msg);
	}

	/*
	 * Encapsulate Web client operations here.
	 * 
	 * TODO: Fill in missing operations.
	 */

	/*
	 * Creation of client instances is expensive, so just create one.
	 */
	protected Client client;
	
	protected Client listenClient;

	public WebClient() {
		client = ClientBuilder.newClient();
		listenClient = ClientBuilder.newBuilder().register(SseFeature.class).build();	
	}

	private void info(String mesg) {
		Log.info(mesg);
	}

	private Response getRequest(URI uri) {
		try {
			Response cr = client.target(uri)
					.request(MediaType.APPLICATION_XML_TYPE)
					.header(Time.TIME_STAMP, Time.advanceTime())
					.get();
			processResponseTimestamp(cr);
			return cr;
		} catch (Exception e) {
			error("Exception during GET request: " + e);
			return null;
		}
	}

	private Response putRequest(URI uri, Entity<?> entity) {
		// TODO Complete.
		try{
			Response cr = client.target(uri)
								.request(MediaType.APPLICATION_XML_TYPE)
								.header(Time.TIME_STAMP, Time.advanceTime())
								.put(entity, Response.class);
			return cr;
		}catch(Exception e){
			error("Exception during PUT request");
			return null;
		}
	}
	
	private Response putRequest(URI uri) {
		return putRequest(uri, Entity.text(""));
	}

	private void processResponseTimestamp(Response cr) {
		Time.advanceTime(Long.parseLong(cr.getHeaders().getFirst(Time.TIME_STAMP).toString()));
	}

	/*
	 * Jersey way of dealing with JAXB client-side: wrap with run-time type
	 * information.
	 */
	private GenericType<JAXBElement<NodeInfo>> nodeInfoType = new GenericType<JAXBElement<NodeInfo>>() {
	};

	/*
	 * Ping a remote site to see if it is still available.
	 */
	public boolean isFailed(URI base) {
		URI uri = UriBuilder.fromUri(base).path("info").build();
		Response c = getRequest(uri);
		return c.getStatus() >= 300;
	}

	/*
	 * Get the predecessor pointer at a node.
	 */
	public NodeInfo getPred(NodeInfo node) throws DHTBase.Failed {
		URI predPath = UriBuilder.fromUri(node.addr).path("pred").build();
		info("client getPred(" + predPath + ")");
		Response response = getRequest(predPath);
		if (response == null || response.getStatus() >= 300) {
			throw new DHTBase.Failed("GET /pred");
		} else {
			NodeInfo pred = response.readEntity(nodeInfoType).getValue();
			return pred;
		}
	}
	
	/*TODO
	 * Get the successor pointer at a node
	 */
	public NodeInfo getSuccessor(NodeInfo node) throws DHTBase.Failed{
		URI succPath = UriBuilder.fromUri(node.addr).path("succ").build();
		info("client getSuccessor(" + succPath + ")");
		Response response = getRequest(succPath);
		if (response == null || response.getStatus() >= 300) {
			throw new DHTBase.Failed("GET /succ");
		} else {
			NodeInfo pred = response.readEntity(nodeInfoType).getValue();
			return pred;
		}
	}

	/*
	 * Notify node that we (think we) are its predecessor.
	 */
	public TableRep notify(NodeInfo node, TableRep predDb) throws DHTBase.Failed {
		/*
		 * The protocol here is more complex than for other operations. We
		 * notify a new successor that we are its predecessor, and expect its
		 * bindings as a result. But if it fails to accept us as its predecessor
		 * (someone else has become intermediate predecessor since we found out
		 * this node is our successor i.e. race condition that we don't try to
		 * avoid because to do so is infeasible), it notifies us by returning
		 * null. This is represented in HTTP by RC=304 (Not Modified).
		 */
		NodeInfo thisNode = predDb.getInfo();
		UriBuilder ub = UriBuilder.fromUri(node.addr).path("notify");
		URI notifyPath = ub.queryParam("id", thisNode.id).build();
		info("client notify(" + notifyPath + ")");
		Response response = putRequest(notifyPath, Entity.xml(predDb));
		if (response != null && response.getStatusInfo() == Response.Status.NOT_MODIFIED) {
			/*
			 * Do nothing, the successor did not accept us as its predecessor.
			 */
			return null;
		} else if (response == null || response.getStatus() >= 300) {
			throw new DHTBase.Failed("PUT /notify?id=ID");
		} else {
			TableRep bindings = response.readEntity(TableRep.class);
			return bindings;
		}
	}
	
	/*TODO
	 * Get the closest preceding finger from the finger table of specified node
	 */
	public NodeInfo closestPrecedingFinger(NodeInfo node, int id) throws Failed{
		for(int i = IRouting.NFINGERS - 1; i > -1; i --){
			URI succPath = UriBuilder.fromUri(node.addr).path("finger").queryParam("ID", i).build();
			info("client closestPrecedingFinger(" + succPath + ")");
			Response response = getRequest(succPath);
			if (response == null || response.getStatus() >= 300) {
				throw new DHTBase.Failed("get /finger?id=" + i);
			} else {				
				NodeInfo pred = response.readEntity(nodeInfoType).getValue();
				return pred;
			}
		}
		
		return node;
	}
	
	/*TODO
	 * find the successor of a specified id
	 */
	public NodeInfo findSuccessor(URI addr, int id) throws Failed{
		URI succPath = UriBuilder.fromUri(addr).path("find").queryParam("id", id).build();
		info("client findSuccessor(" + succPath + ")");
		Response response = getRequest(succPath);
		if (response == null || response.getStatus() >= 300) {
			throw new DHTBase.Failed("get /find?ID=" + id);
		} else {				
			NodeInfo pred = response.readEntity(nodeInfoType).getValue();
			return pred;
		}
	}
	
	public String[] getKeyValues(NodeInfo node, String key) throws Failed{		
		URI succPath = UriBuilder.fromUri(node.addr).queryParam("key", key).build();
		info("client findSuccessor(" + succPath + ")");
		Response response = getRequest(succPath);
		if (response == null || response.getStatus() >= 300) {
			throw new DHTBase.Failed("get ?KEY=" + key);
		} else {				
			String result = response.readEntity(String.class);
			
			return result.split(System.getProperty("line.separator"));
		}
	}
	
	public void addKeyValues(NodeInfo n, String k, String v){
		URI succPath = UriBuilder.fromUri(n.addr).queryParam("key", k).queryParam("val", v).build();
		info("client findSuccessor(" + succPath + ")");
		putRequest(succPath);
	}
	
	public void deleteKeyValues(NodeInfo n, String k, String v){
		URI succPath = UriBuilder.fromUri(n.addr).queryParam("key", k).queryParam("val", v).build();
		info("client findSuccessor(" + succPath + ")");
		client.target(succPath)
				.request()
				.header(Time.TIME_STAMP, Time.advanceTime())
				.delete();
	}

	
	public EventSource listenForBindings(NodeInfo node, int id, String skey) throws DHTBase.Failed {
		// TODO listen for SSE subscription requests on http://.../dht/listen?key=<key>
		// On the service side, don't expect LT request or response headers for this request.
		// Note: "id" is client's id, to enable us to stop event generation at the server.
		
		
		URI path = UriBuilder.fromUri(node.addr).path("listen").queryParam("id", id).queryParam("key", skey).build();
		
		WebTarget webtarget = listenClient.target(path);
		EventSource eventsource = new EventSource(webtarget);
		
		return eventsource;
	}

	public void listenOff(NodeInfo node, int id, String skey) throws DHTBase.Failed {
		// TODO listen for SSE subscription requests on http://.../dht/listen?key=<key>
		// On the service side, don't expect LT request or response headers for this request.
		
		URI path = UriBuilder.fromUri(node.addr).path("listen").queryParam("id", id).queryParam("key", skey).build();
		
		WebTarget webtarget = listenClient.target(path);
		webtarget.request().buildDelete().invoke();
	}

}
