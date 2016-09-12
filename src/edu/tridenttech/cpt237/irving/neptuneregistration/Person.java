package edu.tridenttech.cpt237.irving.neptuneregistration;

/**
 * FILE		- Person.java
 * PURP		- The blueprint for a person. Contains only first and last name as attributes.
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Tech.
 * EDIT		- 9/11/16
 * @author	- Stephen M. Irving
 */

public class Person implements Comparable<Person> {

	//Field Declarations
	private String fName;
	private String lName;

	//Constructor
	public Person(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
	}

	//Accessor block
	public String getFName() {
		return fName;
	}

	public String getLName() {
		return lName;
	} //End of accessors

	//Mutator block
	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	} //End of mutators

	@Override	//Compares by last name then by first name
	public int compareTo(Person personComparable) { 
        int relationship = this.lName.compareTo(personComparable.lName);
        if (relationship == 0) {
            relationship = this.fName.compareTo(personComparable.fName);
        }
        return relationship;    
	}
} //End of Person class
