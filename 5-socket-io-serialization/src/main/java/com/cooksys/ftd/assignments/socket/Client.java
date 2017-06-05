package com.cooksys.ftd.assignments.socket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    private static final String  CONFIG_FILE_PATH = "C:/Users/ftd-6/workspace/combined-assignments/5-socket-io-serialization/config/config.xml";

	
    public static void main(String[] args) {
        Config config = null;
    	
    	try {
			config = Utils.loadConfig(CONFIG_FILE_PATH, Utils.createJAXBContext());
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try (Socket socket = new Socket(config.getRemote().getHost(), config.getRemote().getPort())) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Unmarshaller unmarshaller = Utils.createJAXBContext().createUnmarshaller();
			
    		Student student = (Student) unmarshaller.unmarshal(in);
    		System.out.println(student);
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
