package fw_IIoT.mqtt_sample_interval_run;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import javax.net.ssl.SSLSocketFactory;


public class Fw_mqtt_class {
	private MqttClient client;
	private MqttConnectOptions conOpt;
	Fw_mqtt_main fw_mqtt= new Fw_mqtt_main();
	
	String brokerUrl = fw_mqtt.host_name;
	int port = (int) fw_mqtt.port;
	String clientId= fw_mqtt.client_id;
	String pub_topic = fw_mqtt.publish_topic;
	String sub_topic = fw_mqtt.subscribe_topic;
	String username = fw_mqtt.mqtt_username;
	String password = fw_mqtt.mqtt_password;
	
	int qos = 0;
	
	public Fw_mqtt_class() throws MqttException {

	    	try {
	    		// Construct the connection options object that contains connection parameters
		    	conOpt = new MqttConnectOptions();
		    	conOpt.setCleanSession(true);
		    	conOpt.setUserName(username);
		    	conOpt.setPassword(password.toCharArray());
		    	
	    		// Construct an MQTT blocking mode client
		    	if(port == 1883){
		    		client = new MqttClient("tcp://"+brokerUrl+":"+Integer.toString(port), clientId, new MemoryPersistence());
		    	}
		    	else if(port == 8883) {
		    		conOpt.setSocketFactory(SSLSocketFactory.getDefault());
		    		client = new MqttClient("ssl://"+brokerUrl+":"+Integer.toString(port), clientId, new MemoryPersistence());
		    	}
				

				
				// Set this wrapper as the callback handler
		    	client.setCallback(new MqttCallback() {
					
		    		// Check if any message is received  
					@Override
					public void messageArrived(String sub_topic, MqttMessage message) throws Exception {
						System.out.println("---Message received from " + sub_topic + "---\n" +
								"Message: " + new String(message.getPayload()) +
								" QoS: " + message.getQos() +"\n" +
								"----------------------------------------------------------------\n");
					}
					
					// Check if data is sent successfully 
					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {

			        	try {
							System.out.println("Succesfully published!\n"  +"Payload: "+token.getMessage() + "\n");
						} catch (MqttException e) {
							e.printStackTrace();
						}
					}
					
					// Reconnect if connection is lost
					@Override 
					public void connectionLost(Throwable cause) {

						System.out.println("Connection to " + brokerUrl + " lost!" + cause.getMessage());
						System.out.println("Reconnecting..");
			        	try {
							client.connect(conOpt);
				        	System.out.println("Connected to Fogwing IIoT cloud");
						} catch (MqttException e) {
							e.printStackTrace();
						}
					}
				});
		    	if(client.isConnected()){
		        	System.out.println("was already Connected");
		        	client.disconnect();
		    	}
	        	client.connect(conOpt);
	        	System.out.println("Connected to Fogwing IIoT cloud");


			} catch (MqttException e) {
				e.printStackTrace();
				System.out.println("Unable to set up client: "+e.toString());
				System.exit(1);
			}
	    }
		
		public void subscribe() throws MqttPersistenceException, MqttException {
			if(client.isConnected()) {
				client.subscribe(sub_topic, qos);
				System.out.println("Subscribed to "+ sub_topic+"\n");
			}
			else {
				client.connect(conOpt);
				System.out.println("Connected to Fogwing IIoT cloud");
				System.out.println("Subscribed to "+ sub_topic+"\n");
			}
		}
			
		
	    public void publish(String data) throws MqttPersistenceException, MqttException, InterruptedException{
	    	System.out.println("Publishing data to " +pub_topic);
	    	// Create and configure a message
	   		MqttMessage message = new MqttMessage(data.getBytes());
	    	message.setQos(qos);
	    	message.setRetained(false);
	    	client.publish(pub_topic, message);
	    	

	    }
	    
	    // This Function is to close MQTT connection
	    public void close() throws MqttException{
	    	// Disconnect the client
	    	client.disconnect();
	    	System.out.println("Disconnected");
	    }
}