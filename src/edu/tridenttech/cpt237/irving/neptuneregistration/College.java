package edu.tridenttech.cpt237.irving.neptuneregistration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * FILE		- College.java
 * PURP		- The blueprint for the college comprised of students and instructors.
 * 			  College is an enum that implements a singleton pattern.
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/11/16
 * @author	- Stephen M. Irving
 */

public enum College {
	INSTANCE;
	
	//Field declarations
	private ArrayList<Student> students;		//An ArrayList of students
	private ArrayList<Instructor> instructors;	//An ArrayList of instructors
	
	//Constructor
	private College() {
		students = new ArrayList<Student>();
		instructors = new ArrayList<Instructor>();
	}

	//Accessor block
	public ArrayList<Student> getStudentList() {
		return students;
	}

	public ArrayList<Instructor> getInstructorList() {
		return instructors;
	} //End of accessors
	
	/****************************************************************************************************
	 **********							Start of addStudent method								*********
	 * PURP 	- To use a first name and last name to create a student object, add that object to the	*
	 * 			  ArrayList of students and then return the student object at the end of the method.	*
	 * @param	- String fName, lName (Strings representing the first and last name of the student)		*
	 * @return	- Student myStudent																		*
	 ****************************************************************************************************/
	public Student addStudent(String fName, String lName) {
		Student myStudent = new Student(fName, lName);
		students.add(myStudent);
		return myStudent;
	}

	/****************************************************************************************************
	 **********							Start of addInstructor method							*********
	 * PURP 	- To use a first name and last name to create an instructor object, add that object to	*
	 * 			  the ArrayList of instructors and then return the instructor object at the end of		*
	 * 			  the method.																			*
	 * @param	- String fName, lName (Strings representing the first and last name of the instructor)	*
	 * @return	- Instructor myInstructor																		*
	 ****************************************************************************************************/
	public Instructor addInstructor(String fName, String lName) {
		Instructor myInstructor = new Instructor(fName, lName);
		instructors.add(myInstructor);
		return myInstructor;	
	}

	/****************************************************************************************************
	 **********							Start of loadStudents method							*********
	 * PURP 	- To load the students from the student master file line by line, creating new student	*
	 * 			  objects and adding those objects to the ArrayList students.							*
	 * @param	- String studentDataFile (String representing the file name of the student data file)	*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void loadStudents(String studentDataFile) {
		String line = "";		//A single line in the courseDataFile		
		String[] studentData;	//Each value in a single line from the studentDataFile
		Student myStudent; 		//Local student object used in the loop and rewritten each pass

		try (BufferedReader br = new BufferedReader(new FileReader(studentDataFile))) {
			while ((line = br.readLine()) != null) 	{
				studentData = line.split(",");
				myStudent = new Student(studentData[0], studentData[1]);
				myStudent.setMajor(studentData[2]);
				students.add(myStudent);
			}
			Collections.sort(students);
			students.trimToSize();
		} catch (IOException e) {
			System.err.println("The Student Data File could not be found!");
			System.err.println("Please check the top level program directory for " + studentDataFile);
			e.printStackTrace();
		}
	} //End of loadStudents method

	/****************************************************************************************************
	 **********							Start of loadInstructors method							*********
	 * PURP 	- To load the instructors from the instructor master file line by line, creating new	*
	 * 			  instructor objects and adding those objects to the ArrayList instructors.				*
	 * @param	- String instructorDataFile (String representing the file name of the student data file)*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void loadInstructors(String instructorDataFile) {
		String line = "";		//A single line in the instructorDataFile		
		String[] instData;		//Each value in a single line from the studentDataFile
		Instructor myInstructor;//Local student object used in the loop and rewritten each pass
		Course myCourse; 		//Local course object used in the loop and rewritten each pass

		try (BufferedReader br = new BufferedReader(new FileReader(instructorDataFile))) {
			while ((line = br.readLine()) != null) 	{
				instData = line.split(",");
				myInstructor = new Instructor(instData[0], instData[1]);
				instructors.add(myInstructor);
				for (int i = 2; i < instData.length; i++) {
					myCourse = Catalog.INSTANCE.getCourseById(instData[i]);
					if (myCourse == null) {	//Catch if course is not in the course master file
						System.err.println("Error loading " + instData[i] +" into " + myInstructor.getFName() + " " +
								myInstructor.getLName() +"'s preferred course list. No such course was found in the " +
								"course directory!");
					} else if (myInstructor.getPreferredCourses().contains(myCourse)) { //Catch duplicate values
						System.err.println("Error loading " + instData[i] +" into " + myInstructor.getFName() + " " +
								myInstructor.getLName() +"'s preferred course list. Course duplicate found, this " +
								"course has already been added to the directory!");
					} else { //If the course is valid and is not a duplicate in the list, then add the course
						myInstructor.addPreferredCourse(myCourse);
					} //End of if/else logic
				} //End of preferred course reading inner for loop
			} //End of line reading while loop
			Collections.sort(instructors);
			instructors.trimToSize();
		} catch (IOException e) {
			System.err.println("The Instructor Data File could not be found!");
			System.err.println("Please check the top level program directory for " + instructorDataFile);
			e.printStackTrace();
		} //End of file reading Try/Catch block
	} //End of loadInstructors method

	/****************************************************************************************************
	 **********							Start of recordStudent method							*********
	 * PURP 	- To take a student object and append it to the end of the student data file in	the		*
	 * 			  same format as the existing data in the file.											*
	 * @param	- Studen s (A student passed to the method that will be written to the file.			*
	 * 			  String studentDataFile (A string representing the file name of the student data file)	*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void recordStudent(Student s, String studentDataFile) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(studentDataFile, true))) {
			bw.write(s.getFName() + "," + s.getLName() + "," + s.getStudentMajor().getName());
			bw.newLine();
			bw.flush();  
			bw.close();  
			System.out.printf("%n%s, %s has now been added to the student data file %s.%n", s.getLName(), s.getFName(),
					studentDataFile);
			} catch (IOException e) {
				System.err.printf("I'm sorry, there has been a problem writing or saving to the file %s", studentDataFile);
				System.out.println(e.getMessage());
			}
	} //End of recordStudent method

	/****************************************************************************************************
	 **********							Start of recordInstructor method						*********
	 * PURP 	- To take an instructor object and append it to the end of the instructor data file in	*
	 * 			  the same format as the existing data in the file.										*
	 * @param	- Instructor inst (An instructor passed to the method that will be written to the file.	*
	 * 			  String instDataFile (A string representing the file name of the instructor data file)	*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void recordInstructor(Instructor inst, String instDataFile) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(instDataFile, true))) {
			bw.write(inst.getFName() + "," + inst.getLName());
			for (int k = 0; k < inst.getPreferredCourses().size(); k++) {
				bw.write("," + inst.getPreferredCourses().get(k).getCourseId());
			}			
			bw.newLine();
			bw.flush();  
			bw.close();  
			System.out.printf("%n%s, %s has now been added to the instructor data file %s.%n", inst.getLName(),
					inst.getFName(), instDataFile);
			} catch (IOException e) {
				System.err.printf("I'm sorry, there has been a problem writing or saving to the file %s", instDataFile);
				System.out.println(e.getMessage());
			}
	} //End of recordInstructor method

	/****************************************************************************************************
	 **********							Start of getStudentById method							*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a student ID and returns the corresponding student.	*
	 * 			  If no student is found then a null value is returned									*
	 * @param	- String sID (The student ID being searched for)										*
	 * @return	- Student chosenStudent (The student object found by it's ID)							*
	 ****************************************************************************************************/
	public Student getStudentById(int sID) {
		Student idChosenStudent = null;
		for (Student studentById : students) {
			if (studentById.getStudentId() == sID) {
				idChosenStudent = studentById;
				break;
			} 
		}
		return idChosenStudent;
	}//End of getStudentById method

	/****************************************************************************************************
	 **********							Start of getStudentbyName method						*********
	 ****************************************************************************************************
	 * PURP 	- Accepts 2 Strings that represents a student's first name and last name and returns	*
	 * 			  the corresponding student. If no student is found then a null value is returned.		*
	 * @param	- String sFName, String SLName (The student first name and last name being searched for)*
	 * @return	- Student nChosenStudent (The student object found by it's name)						*
	 ****************************************************************************************************/
	public Student getStudentByName(String sFName, String sLName) {
		Student nChosenStudent = null;
		for (Student studentByName : students) {
			if ((studentByName.getLName().equalsIgnoreCase(sLName)) && 
					(studentByName.getFName().equalsIgnoreCase(sFName))) {
				nChosenStudent = studentByName;
				break;
			} 
		}
		return nChosenStudent;
	} //End of getStudentbyName method

	/****************************************************************************************************
	 **********						Start of getInstructorByName method							*********
	 ****************************************************************************************************
	 * PURP 	- Accepts 2 Strings that represents an instructor's first name and last name and returns*
	 * 			  the corresponding instructor. If a match is not found then a null value is returned.	*
	 * @param	- String iFName, String iLName (The instrutor's first name and last name that is being	*
	 * 			  searched for)																			*
	 * @return	- Instructor nChosenInstructor (The student object found by it's name OR a null value)	*
	 ****************************************************************************************************/
	public Instructor getInstructorByName(String iFName, String iLName) {
		Instructor nChosenInstructor = null;
		for (Instructor instructorByName : instructors) {
			if ((instructorByName.getLName().equalsIgnoreCase(iLName)) && 
					(instructorByName.getFName().equalsIgnoreCase(iFName))) {
				nChosenInstructor = instructorByName;
				break;
			} 
		}
		return nChosenInstructor;
	} //End of getInstructorByName method
} //End of College enum
