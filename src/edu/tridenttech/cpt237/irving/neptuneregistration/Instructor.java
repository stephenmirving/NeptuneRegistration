package edu.tridenttech.cpt237.irving.neptuneregistration;

import java.util.ArrayList;

/**
 * FILE		- Instructor.java
 * PURP		- The blueprint for an instructor at Neptune Technical College. Extends the person class.
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/11/16
 * @author	- Stephen M. Irving
 */

public class Instructor extends Person {

	//Field declarations
	private ArrayList<Course> preferredCourses = new ArrayList<Course>();	//The courses an instructor can teach

	//Constructor
	public Instructor(String fName, String lName) {
		super(fName, lName);
	}

	//Accessor
	public ArrayList<Course> getPreferredCourses() {
		return preferredCourses;
	}

	/****************************************************************************************************
	 **********					Start of addPreferredCourse method (Overload)					*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a course ID and adds it to the the list				*
	 * 			  preferredCourses.																		*
	 * @param	- String iCourseId (The course ID being added to the preferredCourses list)				*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void addPreferredCourse(String iCourseID) {//Overloaded method
		if (!Catalog.INSTANCE.getCourseList().contains(Catalog.INSTANCE.getCourseById(iCourseID))) {
			System.out.println();
			System.err.println("THE COURSE YOU ENTERED [" + iCourseID + "] IS INVALID AND DOES NOT EXITST!");
			System.out.println();
		} else {
			for (Course findingCourse : Catalog.INSTANCE.getCourseList()) {
				if (findingCourse.getCourseId().equalsIgnoreCase(iCourseID)) {
					preferredCourses.add(findingCourse);
					break;
				} 
			}
		}
	} //End of addPreferredCourse method (Overload)

	/****************************************************************************************************
	 **********					Start of addPreferredCourse method (Overload)					*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a Course object and adds it to the list preferredCourses.						*
	 * @param	- Course iCourseObj (The course object being added to preferredCourses list)			*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public void addPreferredCourse(Course iCourseObj) {//Overloaded method
		preferredCourses.add(iCourseObj);
	} //End of addPreferredCourse method (Overload)

	/****************************************************************************************************
	 **********						Start of isPreferredCourse method							*********
	 ****************************************************************************************************
	 * PURP 	- Accepts a String that represents a course ID, checks that ID against the list of		*
	 * 			  preferred courses for an instructor an returns a boolean that is true if the course	*
	 * 			  is found in the list.																	*
	 * @param	- String iCourseID (The course ID being searched for in the preferredCourses list)		*
	 * @return	- boolean isItPreferred (True if it is in the last, false if it is not)					*
	 ****************************************************************************************************/
	public boolean isPreferredCourse(String iCourseID) {
		boolean isItPreferred = false;
		
		for (Course findingCourse : Catalog.INSTANCE.getCourseList()) {
			if (findingCourse.getCourseId().equalsIgnoreCase(iCourseID)) {
				isItPreferred = true;
				break;
			} 
		}
		return isItPreferred;
	} //End of isPreferredCourse method

	@Override
	public String toString() {
		return String.format("%s %s%n%n Preferred Course(s):%n %s%n", super.getFName(), super.getLName(),
				 preferredCourses);
	}
} //End of Instructor class
