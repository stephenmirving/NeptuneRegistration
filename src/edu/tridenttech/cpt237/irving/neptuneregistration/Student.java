package edu.tridenttech.cpt237.irving.neptuneregistration;

/**
 * FILE		- Student.java
 * PURP		- The blueprint for a student at Neptune Technical College. Extends the person class.
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/11/16
 * @author	- Stephen M. Irving
 */

public class Student extends Person {

	//Field declarations
	private final int studentId;		//An int representing the student's ID number
	private static int nextId = 10000;	//The next available ID number, incremented each time a student is created
	private Major studentMajor = new Major("UNDECLARED", "false"); //Default major set as UNDECLARED
	
	//Constructor
	public Student(String fName, String lName) {
		super(fName, lName);
		studentId = nextId;
		nextId++;
	}

	//Accessors block
	public int getStudentId() {
		return studentId;
	}

	public Major getStudentMajor() {
		return studentMajor;
	}

	//Mutator block
	public void setMajor(String sMajor) {		//Overloaded method
		for (Major findingMajor : Catalog.INSTANCE.getMajorList()) {
			if (findingMajor.getName().equalsIgnoreCase(sMajor)) {
				studentMajor = findingMajor;
				break;
			} 
		}
	}

	public void setMajor(Major studentMajor) {	//Overloaded method
		this.studentMajor = studentMajor;
	} //End of mutators
	
	@Override
	public String toString() {
		return String.format("%s, %s%n ID#: %d%n Major: %s%n", super.getLName(), super.getFName(),
				studentId, studentMajor);
	}

} //End of Student class
