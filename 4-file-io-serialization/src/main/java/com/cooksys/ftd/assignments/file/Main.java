package com.cooksys.ftd.assignments.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

public class Main {

    /**
     * Creates a {@link Student} object using the given studentContactFile.
     * The studentContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param studentContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return a {@link Student} object built using the {@link Contact} data in the given file
     * @throws JAXBException 
     * @throws FileNotFoundException 
     */
    public static Student readStudent(File studentContactFile, JAXBContext jaxb) throws JAXBException, FileNotFoundException {
    	if (jaxb == null || studentContactFile == null) {
			return null;
		}
    	
    	Unmarshaller unmarshaller = jaxb.createUnmarshaller();
    	Contact contact = (Contact) unmarshaller.unmarshal(new FileInputStream(studentContactFile));
    	Student student = new Student();
    	student.setContact(contact);
    	
        return student;
    }

    /**
     * Creates a list of {@link Student} objects using the given directory of student contact files.
     *
     * @param studentDirectory the directory of student contact files to use
     * @param jaxb the JAXB context to use
     * @return a list of {@link Student} objects built using the contact files in the given directory
     * @throws JAXBException 
     * @throws FileNotFoundException 
     */
    public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) throws FileNotFoundException, JAXBException {
    	if (jaxb == null || studentDirectory == null || !studentDirectory.isDirectory()) {
			return null;
		}
    	
    	List<Student> studentsList = new ArrayList<>();
    	File[] fileList = studentDirectory.listFiles();
    	
    	for (File file : fileList) {
			studentsList.add(readStudent(file, jaxb));
		}
    	
        return studentsList; // TODO
    }

    /**
     * Creates an {@link Instructor} object using the given instructorContactFile.
     * The instructorContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param instructorContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return an {@link Instructor} object built using the {@link Contact} data in the given file
     * @throws JAXBException 
     * @throws FileNotFoundException 
     */
    public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) throws JAXBException, FileNotFoundException {
    	if (jaxb == null || instructorContactFile == null) {
			return null;
		}
    	
    	Unmarshaller unmarshaller = jaxb.createUnmarshaller();
    	Contact contact = (Contact) unmarshaller.unmarshal(new FileInputStream(instructorContactFile));
    	Instructor instructor = new Instructor();
    	instructor.setContact(contact);
    	
        return instructor;
    }

    /**
     * Creates a {@link Session} object using the given rootDirectory. A {@link Session}
     * root directory is named after the location of the {@link Session}, and contains a directory named
     * after the start date of the {@link Session}. The start date directory in turn contains a directory named
     * `students`, which contains contact files for the students in the session. The start date directory
     * also contains an instructor contact file named `instructor.xml`.
     *
     * @param rootDirectory the root directory of the session data, named after the session location
     * @param jaxb the JAXB context to use
     * @return a {@link Session} object built from the data in the given directory
     * @throws JAXBException 
     * @throws FileNotFoundException 
     */
    public static Session readSession(File rootDirectory, JAXBContext jaxb) throws FileNotFoundException, JAXBException {
        if (rootDirectory == null || jaxb == null || !rootDirectory.isDirectory()) {
			return null;
		}
        Session session = new Session();
        
        File[] files = rootDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
			session.setLocation(files[i].getName());
			
			File[] fileStepIn = files[i].listFiles();
			session.setLocation(fileStepIn[i].getName());
			
			File[] filesDates = fileStepIn[i].listFiles();
			for (int j = 0; j < filesDates.length; j++) {
				if (!filesDates[j].isDirectory()) {
					session.setInstructor(readInstructor(new File(filesDates[j].getPath()), jaxb));
				} else {
					session.setStudents(readStudents(files[j], jaxb));
				}
			}
		}
    	
        
        
    	
    	
    	return session;
    }

    /**
     * Writes a given session to a given XML file
     *
     * @param session the session to write to the given file
     * @param sessionFile the file to which the session is to be written
     * @param jaxb the JAXB context to use
     */
    public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) {
        // TODO
    }

    /**
     * Main Method Execution Steps:
     * 1. Configure JAXB for the classes in the com.cooksys.serialization.assignment.model package
     * 2. Read a session object from the <project-root>/input/memphis/ directory using the methods defined above
     * 3. Write the session object to the <project-root>/output/session.xml file.
     *
     * JAXB Annotations and Configuration:
     * You will have to add JAXB annotations to the classes in the com.cooksys.serialization.assignment.model package
     *
     * Check the XML files in the <project-root>/input/ directory to determine how to configure the {@link Contact}
     *  JAXB annotations
     *
     * The {@link Session} object should marshal to look like the following:
     *      <session location="..." start-date="...">
     *           <instructor>
     *               <contact>...</contact>
     *           </instructor>
     *           <students>
     *               ...
     *               <student>
     *                   <contact>...</contact>
     *               </student>
     *               ...
     *           </students>
     *      </session>
     */
    public static void main(String[] args) {
    	
        try {
        	JAXBContext jaxbContext = JAXBContext.newInstance(Session.class);
			Session instructor = readSession(new File("C:/Users/ftd-6/workspace/combined-assignments/4-file-io-serialization/input/"), jaxbContext);
			
			System.out.println(instructor);
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
