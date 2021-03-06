package edu.tridenttech.cpt237.irving.neptuneregistration;

/**
 * FILE		- Course.java
 * PURP		- The blueprint for a course at Neptune Technical College. 
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/8/16
 * @author	- Stephen M. Irving
 */

public class Course implements Comparable<Course> {
	//Field declarations
	private final String courseId;	//The course ID
	private final String name;		//The name of the course
	private final int hours;		//The number of credit hours generated by the course

	//Constructor
	public Course(String cID, String cTitle, String cHours) {
		courseId = cID;
		name = cTitle;
		hours = Integer.parseInt(cHours);
	}

	//Accessor block
	public String getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public int getHours() {
		return hours;
	} //End of accessor block

	@Override
	public int compareTo(Course courseComparable) {
		return this.courseId.compareToIgnoreCase(courseComparable.courseId);
	}

	@Override
	public String toString() {
		return String.format("%s - %s - %d Credit Hours%n", courseId, name, hours);
	}
} //End of Course class
