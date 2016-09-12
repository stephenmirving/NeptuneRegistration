package edu.tridenttech.cpt237.irving.neptuneregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * FILE		- Catalog.java
 * PURP		- The blueprint for a course and major catalog at Neptune Technical College.
 * 			  Catalog is an enum that implements a singleton pattern.
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/11/16
 * @author	- Stephen M. Irving
 */

public enum Catalog {
	INSTANCE;
	
	//Field declarations
	private ArrayList<Course> courses;	//An ArrayList of courses
	private ArrayList<Major> majors;	//An ArrayList of majors

	//Constructor
	private Catalog() {
		courses = new ArrayList<Course>(100);
		majors = new ArrayList<Major>(10);
	}
	
	//Accessor block
	public ArrayList<Course> getCourseList() {
		return courses;
	}

	public ArrayList<Major> getMajorList() {
		return majors;
	} //End of accessor block

	/****************************************************************************************************
	 **********							Start of loadCourses method								*********
	 * PURP 	- To load the courses from the course master file line by line, creating new course		*
	 * 			  objects and adding those objects to the ArrayList courses.							*
	 * @param	- String courseDataFile (A string representing the file name of the course data file	*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void loadCourses(String courseDataFile) {
		String line = "";		//A single line in the courseDataFile		
		String[] courseData;	//Each value in a single line from the courseDataFile

		try (BufferedReader br = new BufferedReader(new FileReader(courseDataFile))) {
			while ((line = br.readLine()) != null) 	{
				courseData = line.split(",");
				courses.add(new Course (courseData[0], courseData[1], courseData[2]));
			}
			Collections.sort(courses);
			courses.trimToSize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} //End of loadCourses method

	/****************************************************************************************************
	 **********							Start of loadMajors method								*********
	 * PURP 	- To load the majors from the major master file line by line, creating new major		*
	 * 			  objects and adding those objects to the major ArrayList.								*
	 * @param	- String majorDataFile (A string representing the file name of the major data file		*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void loadMajors(String majorDataFile) {
		String line = "";	//A single line in the majorDataFile
		String[] majorData; //Each value in a single line from the majorDataFile
		Major myMajor;		//Local Major object that changes value each time through the loop as majors are loaded
		Course myCourse;	//Local Course object that changes value each time through the loop as majors are loaded

		try (BufferedReader br = new BufferedReader(new FileReader(majorDataFile))) {
				while ((line = br.readLine()) != null) 	{ //Add each value in the line to the majorData array
					majorData = line.split(","); // Split the comma separated values on each line into the array
					myMajor = new Major (majorData[0], majorData[1]);
					majors.add(myMajor);
					for (int i = 2; i < majorData.length; i++) {
						myCourse = getCourseById(majorData[i]);
						if (myCourse == null) { //Catch if course is not in the course master file
							System.err.println("Error loading " + majorData[i] +" into " + myMajor.getName() +"'s "
									+ "required courses. No such course found in course directory!");
						} else if (myMajor.getRequiredCourses().contains(myCourse)) { //Catch duplicate values
							System.err.println("Error loading " + majorData[i] +" into " + myMajor.getName() +"'s "
									+ "required courses. Course duplicate found, this course has already been added!");
						} else { //If the course is valid and is not a duplicate in the list, then add the course
							myMajor.addRequiredCourses(myCourse);
						} //End of IF/ELSE block
					} //End of for loop
				} //End of line-reading while loop
				Collections.sort(majors);
				majors.trimToSize();
		} catch (IOException e) {
			e.printStackTrace();
		} //End of Try/Catch block
	} //End of loadMajors method

	/****************************************************************************************************
	 **********							Start of getCourseById method							*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a course ID and returns the corresponding course		*
	 * @param	- String cID (The course ID being searched for											*
	 * @return	- Course chosenCourse (The chosen course object found by it's ID)						*
	 ****************************************************************************************************/
	public Course getCourseById(String cID) {
		Course chosenCourse = null;
		for (Course courseByID : courses) {
			if (courseByID.getCourseId().equalsIgnoreCase(cID)) {
				chosenCourse = courseByID;
				break;
			} 
		}
		return chosenCourse;
	} //End of getCourseById method

	/****************************************************************************************************
	 **********							Start of getMajorByName method							*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a major name and returns the corresponding major		*
	 * @param	- String mName (The major name being searched for)										*
	 * @return	- Major chosenMajor (The chosen major object found by it's name)						*
	 ****************************************************************************************************/
	public Major getMajorByName(String mName) {
		Major chosenMajor = null;
		for (Major majorByName : majors) {
			if (majorByName.getName().equalsIgnoreCase(mName)) {
				chosenMajor = majorByName;
				break;
			} 
		}
		return chosenMajor;
	} //End of getCourseById method
} //End of Catalog enum
