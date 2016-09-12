package edu.tridenttech.cpt237.irving.neptuneregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FILE		- Major.java
 * PURP		- The blueprint for a major at Neptune Technical College. 
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/8/16
 * @author	- Stephen M. Irving
 */

public class Major implements Comparable<Major> {
	//Field declarations
	private final String name;		//The name of the major
	private final boolean isDegree; //If false, then the major is a certificate program
	private ArrayList<Course> requiredCourses = new ArrayList<Course>(); //Contains required courses for a major
	private int requiredHours;		//Total number of required credit hours for a major

	//Constructor
	public Major(String mName, String degreeBool) { 
		name = mName;
		isDegree = Boolean.parseBoolean(degreeBool);
	}

	//Accessor block
	public String getName() {
		return name;
	}

	public boolean getIsDegree() {
		return isDegree;
	}

	public List<Course> getRequiredCourses() {
		return Collections.unmodifiableList(requiredCourses);
	}
	
	public int getRequiredHours() {	
		return requiredHours;
	}//End of accessor block

	/****************************************************************************************************
	 **********							Start of addRequiredCourses method						*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a course ID and populates the list requiredCourses	*
	 * @param	- String courseId (The course ID being added to requiredCourses)						*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void addRequiredCourses(Course newCourse) {
		requiredCourses.add(newCourse);
		addRequiredHours(newCourse.getHours());
	}

	/****************************************************************************************************
	 **********							Start of addRequiredHours method						*********
	 ****************************************************************************************************
	 * PURP 	- Accepts an int value that represents the number of credit hours that a given course	*
	 * 			  offers and adds that value to the total number of required hours for the major.		*
	 * @param	- int courseCredHours (The course's credit hours being added to the total				*
	 * @return	- void																					*
	 ****************************************************************************************************/
	private void addRequiredHours(int courseCredHours) {
		requiredHours += courseCredHours;
	}

	@Override
	public int compareTo(Major majorComparable) {
		return this.name.compareTo(majorComparable.name);
	}
	
	@Override
	public String toString() {
		return String.format("%s%n", name);
	}
} //End of Major class
