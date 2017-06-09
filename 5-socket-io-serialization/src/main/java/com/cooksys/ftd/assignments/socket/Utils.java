package com.cooksys.ftd.assignments.socket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

/**
 * Shared static methods to be used by both the {@link Client} and
 * {@link Server} classes.
 */
public class Utils {

	// Created for testing, used by both client and server for the location of
	// the config.xml
	public static final String CONFIG_FILE_PATH = "C:/Users/ftd-6/workspace/combined-assignments/5-socket-io-serialization/config/config.xml";

	/**
	 * @return a {@link JAXBContext} initialized with the classes in the
	 *         com.cooksys.socket.assignment.model package
	 * @throws JAXBException
	 */
	public static JAXBContext createJAXBContext() throws JAXBException {
		return JAXBContext.newInstance(Config.class, LocalConfig.class, RemoteConfig.class, Student.class);
	}

	/**
	 * Reads a {@link Config} object from the given file path.
	 *
	 * @param configFilePath
	 *            the file path to the config.xml file
	 * @param jaxb
	 *            the JAXBContext to use
	 * @return a {@link Config} object that was read from the config.xml file
	 * @throws JAXBException
	 */
	public static Config loadConfig(String configFilePath, JAXBContext jaxb)
			throws JAXBException, FileNotFoundException {
		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		Config config = null;

		try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
			config = (Config) unmarshaller.unmarshal(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return config;
	}
}
