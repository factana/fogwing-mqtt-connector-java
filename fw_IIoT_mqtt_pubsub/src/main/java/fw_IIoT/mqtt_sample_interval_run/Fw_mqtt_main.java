package fw_IIoT.mqtt_sample_interval_run;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONValue;  
import java.util.HashMap;  
import java.util.Map;


public class Fw_mqtt_main
{
	// Enter Fogwing IoT Hub access details here
	String host_name = "iothub.fogwing.net";
	int port = 1883; // 1883 MQTT connection and 8883 for MQTTS connection
	String client_id = "<Client Id>";
	String publish_topic = "<Publish Topic>";
	String subscribe_topic = "<Subscribe Topic>";
	String mqtt_username = "<Username>";
	String mqtt_password = "<Password>";
	
	// Set data frequency in minutes
	public static int interval = 1;
	
    public static void main( String[] args )throws MqttException, InterruptedException 
    {
    	try {
		Fw_mqtt_class fw_mqtt= new Fw_mqtt_class();
		Random random = new Random();  
		Map<String, Float> obj=new HashMap<String, Float>();
		while(true){
			float temp =random.nextFloat(20, 30);
			float hum =random.nextFloat(40, 100);
			float pressure =random.nextFloat(1000, 2000);
			obj.put("Temperature", temp); 
		        obj.put("Humidity", hum);
		        obj.put("Pressure", pressure);
		        String jsonText = JSONValue.toJSONString(obj);
		        
		        // Publish data to Fogwing IIoT
			fw_mqtt.publish(jsonText);
			fw_mqtt.subscribe();
			TimeUnit.MINUTES.sleep(interval);
			}}
    	catch(MqttException me) {
        	me.printStackTrace();
        }
    }
}
    	
