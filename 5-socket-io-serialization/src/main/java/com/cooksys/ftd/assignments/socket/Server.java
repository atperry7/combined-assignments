package com.cooksys.ftd.assignments.socket;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server extends Utils {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the file path from which to read the student config file
	 * @param jaxb
	 *            the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 * @throws JAXBException
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		Student student = null;

		try (FileInputStream fileInputStream = new FileInputStream(studentFilePath)) {
			student = (Student) unmarshaller.unmarshal(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return student;
	}

	/**
	 * The server should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the
	 * embedded {@link com.cooksys.ftd.assignments.socket.model.LocalConfig}
	 * object to create a server socket that listens for connections on the
	 * configured port.
	 *
	 * Upon receiving a connection, the server should unmarshal a
	 * {@link Student} object from a file location specified by the config's
	 * "studentFilePath" property. It should then re-marshal the object to xml
	 * over the socket's output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more
	 * connections.
	 */

	public static void main(String[] args) {
		Config config = null;

		try {
			config = Utils.loadConfig(Utils.CONFIG_FILE_PATH, Utils.createJAXBContext());
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}

		try (ServerSocket serverSocket = new ServerSocket(config.getLocal().getPort());
				Socket clientSocket = serverSocket.accept();
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

			// This UnMarshalls the student file
			Student student = loadStudent(config.getStudentFilePath(), createJAXBContext());

			// This Marshalls the student class and pushes the marshelled xml
			// over the server to the client
			Marshaller marshaller = createJAXBContext().createMarshaller();
			marshaller.marshal(student, out);

		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Server Closing");
		}

	}

}
