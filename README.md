# Fogwing MQTT client in Java.

**Notes:-** These SDKs are currently in preview and are subject to change.

### Pre-requisites:
- [Eclipse IDE](https://www.eclipse.org/downloads)

### Step: 1
- [Setup Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/installer).

### Step: 2
- Download [fw_IIoT_mqtt_pub]() to publish once, Download [fw_IIoT_mqtt_pubsub]() to publish data in interval and also to subscribe.

### Step: 3
- Unzip the file and open Eclipse IDE -> Open projects from files system -> Choose the right folder and Enter the [Fogwing](https://portal.fogwing.net/) IoTHub credentials and set `public static int interval` to publish data with time interval.

### Step: 4
-  Run the client.

### Step: 5 (To run in linux)
-  Install java in Linux system using command `sudo apt install default-jre`, Export the file as **Runnable Jar file** and copy it to your Linux system, run the file using command `sudo java -jar <filename>.jar`

**Note:-** Provided everything goes in line with the above mentioned
           instructions, you will be able to see a message that reads
           **Succesfully published!** in console window.

### Step: 6
- Now you are ready to analyze your data at [Fogwing Platform](https://portal.fogwing.net/) portal,
you can check all the data within the data storage in the portal.


Please visit https://www.fogwing.io/industrial-iot-platform/ for more information about Fogwing Industrial IoT Platform.
