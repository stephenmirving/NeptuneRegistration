package edu.tridenttech.cpt237.irving.neptuneregistration;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FILE		- MainClass.java
 * PURP		- This program serves as a school registration system for Neptune Technical College.
 * 			  This is the class where main is located and the program is run. 
 * 			  Programmed for the Fall Semester of CPT 237 at Trident Technical College.
 * EDIT		- 9/8/16
 * @author	- Stephen M. Irving
 * @version - 2.0
 */

public class MainClass {

	private static Scanner in = new Scanner(System.in);		//Input Scanner

	//File names for the Master Files located in the default, top level program directory
	private final static String COURSE_MASTER_FILE = "NeptuneCourses.txt";		//Course master file
	private final static String MAJOR_MASTER_FILE = "NeptuneMajors.txt";		//Major master file
	private final static String STU_MASTER_FILE = "NeptuneStudents.txt";		//Student master file
	private final static String INS_A_MASTER_FILE = "NeptuneInstructors-A.txt";	//Instructor master file
	
	public static void main(String[] args) {
		String userInput = "0";	//Variable holding top level user input to navigate the menu or quit
		String fName;			//A variable to hold user input of a person's first name
		String lName;			//A variable to hold user input of a person's last name
		String sMajor;			//A variable to hold user input of a student's major
		Student myStudent;		//A local Student object
		Instructor myInstructor;//A local Instructor object

		//Begin user welcome message output to console
		programStartUp();

		while (!userInput.equalsIgnoreCase("Q")) {
			displayMenu();
			userInput = in.nextLine().substring(0, 1).toUpperCase(); //Get first character of user input
			switch (userInput) {
				case "1":
					System.out.printf("%n%71s%n%n", "SELECTION 1 - ADDING A STUDENT");
					System.out.print("Plese enter the student's first name: ");
					fName = in.nextLine();
					System.out.print("Please enter the student's last name: ");
					lName = in.nextLine();
					myStudent = College.INSTANCE.addStudent(fName, lName);
					System.out.print("Please enter the student's declared major: ");
					sMajor = in.nextLine();
					while (Catalog.INSTANCE.getMajorByName(sMajor) == null) {
						System.err.println("THE MAJOR YOU ENTERED [" + sMajor + "] IS INVALID AND DOES NOT EXITST!");
						System.out.println();
						System.out.print("Please enter the student's declared major: ");
						sMajor = in.nextLine();
					}
					myStudent.setMajor(sMajor);
					College.INSTANCE.recordStudent(myStudent, STU_MASTER_FILE);
					System.out.printf("%n%69s%n", "STUDENT SUCESSFULLY ADDED!");
					break;
				case "2":
					createInstructor();
					break;
				case "3":
					System.out.printf("%n%71s%n%n", "SELECTION 3 - SHOW STUDENT INFO");
					System.out.print("Plese enter the student's first name: ");
					fName = in.nextLine();
					System.out.print("Please enter the student's last name: ");
					lName = in.nextLine();
					myStudent = College.INSTANCE.getStudentByName(fName, lName);
					if (myStudent == null) {
						System.err.println("No student by that name was found!");
					} else {
						String formattedStudent = myStudent.toString()
							    .replace("[", "")  //remove the right bracket
							    .replace("]", "")  //remove the left bracket\
							    .trim();
						System.out.printf("%n %s%n", formattedStudent);
					}
					break;
				case "4":
					System.out.printf("%n%71s%n%n", "SELECTION 4 - SHOW INSTRUCTOR INFO");
					System.out.print("Plese enter the instructor's first name: ");
					fName = in.nextLine();
					System.out.print("Please enter the instructor's last name: ");
					lName = in.nextLine();
					myInstructor = College.INSTANCE.getInstructorByName(fName, lName);
					if (myInstructor == null) {
						System.err.println("No instructor by that name was found!");
					} else {
						String formattedInstructor = myInstructor.toString()
								.replace(",", "")  //remove the commas
							    .replace("[", "")  //remove the right bracket
							    .replace("]", "")  //remove the left bracket
							    .trim();           //remove trailing spaces from partially initialized arrays
						System.out.printf("%n %s%n", formattedInstructor);
					}	
					break;
				case "5":
					System.out.printf("%n%71s%n", "SELECTION 5 - PRINT COURSE LIST");
					printCourseList();				
					break;
				case "6":
					System.out.printf("%n%70s%n", "SELECTION 6 - PRINT MAJOR LIST");
					printMajorList();				
					break;
				case "Q":
					System.out.printf("%n%69s%n%n", "SELECTION Q - QUIT THE PROGRAM");
					System.out.printf("%91s%n", "Thank you for using the Neptune Technical College " +
							"Registration Program!");
					System.out.printf("%59s%n", "GOODBYE!");
					break;
				default:
					System.err.printf("%82s%n", "YOU HAVE MADE AN INVALID SELECTION. PLEASE TRY AGAIN!");
					break;
			}	// End of switch statement
		}	//End of user input while loop
		in.close();
	}	//End of main method

	/****************************************************************************************************
	 **********							Start of programStartUp method							*********
	 ****************************************************************************************************
	 * PURP 	- Prints the program welcome screen to the user and then loads all data files into		*
	 * 			  memory, using their appropriate load methods in Catalog and College. Finally, it		*
	 * 			  prints a header for the first display of the user menu.								*
	 * @param	- none																					*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public static void programStartUp() {
		//Start up screen
		System.out.println("*********************************************************************************"
				+ "*****************************");
		System.out.printf("%-109s%s%n", "*", "*");
		System.out.println("*****************        Welcome to the Neptune Technical College Registration Program"
				+ "       *****************");
		System.out.printf("%-109s%s%n", "*", "*");
		System.out.println("*********************************************************************************"
				+ "*****************************\n");

		//Load in data files
		System.out.println("Program is now loading courses into the Neptune Technical College Catalog...");
		Catalog.INSTANCE.loadCourses(COURSE_MASTER_FILE);
		System.out.println("Courses have been loaded into the Catalog!\n");
		
		System.out.println("Program is now loading majors into the Neptune Technical College Catalog...");
		Catalog.INSTANCE.loadMajors(MAJOR_MASTER_FILE);
		System.out.println("Majors have been loaded into the Catalog! Catalog building complete!\n");
		
		System.out.println("Program is now loading students into the Neptune Technical College Catalog...");
		College.INSTANCE.loadStudents(STU_MASTER_FILE);
		System.out.println("Students have been loaded into the Catalog!\n");
		
		System.out.println("Program is now loading instructors into the Neptune Technical College Catalog...");
		College.INSTANCE.loadInstructors(INS_A_MASTER_FILE);
		System.out.println("Instrutors have been loaded into the College System! College System building complete!\n");	
		
		System.out.println("*********************************************************************************"
				+ "*****************************\n"); 	
		System.out.printf("%75s%n%n", "Neptune Technical College - Program Menu");
		System.out.println("*********************************************************************************"
				+ "*****************************");
	}

	/****************************************************************************************************
	 **********								Start of displayMenu method							*********
	 ****************************************************************************************************
	 * PURP 	- Prints the program menu to the user and then prompts them to make a selection.		*
	 * 			  It does not, however, take any input from the user.									*
	 * @param	- none																					*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public static void displayMenu() {
		System.out.printf("%n%81s%n",    "***************************************************");
		System.out.printf("%81s%n", "* Please select from one of the following options *");
		System.out.printf("%81s%n",    "* ----------------------------------------------- *");
		System.out.printf("%31s%14s%24s%12s%n", "*", "KEY", "ACTION", "*");
		System.out.printf("%81s%n",    "* ----------------------------------------------- *");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(1)", "ADD A STUDENT", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(2)", "ADD AN INSTRUCTOR", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(3)", "STUDENT INFO", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(4)", "INSTRUCTOR INFO", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(5)", "PRINT COURSE LIST", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(6)", "PRINT MAJOR LIST", "*");
		System.out.printf("%31s%14s%24s%12s%n", "*", "(Q)", "QUIT THE PROGRAM", "*");
		System.out.printf("%31s%50s%n",    "*", "*");
		System.out.printf("%81s%n",    "***************************************************");
		System.out.printf("%69s%n", "What would you like to do?");
		System.out.printf("%74s", "Press the desired KEY and hit ENTER: ");
	}

	/****************************************************************************************************
	 **********							Start of createInstructor method						*********
	 ****************************************************************************************************
	 * PURP 	- Generates all the dialogue with the user necessary for adding an instructor			*
	 * 			  to the system.																		*
	 * @param	- none																					*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public static void createInstructor() {
		final String regex = "^[a-zA-Z]{3}-[0-9]{3}$";	//The regex pattern for a course ID
		Pattern pattern = Pattern.compile(regex);		//The applied pattern for a course ID
		Matcher matcher;		//The matcher for course ID entry
		Instructor myInstructor;//A local Instructor object
		String contInput = "0";	//Variable holding user input determining if they want to continue entering course ID's
		String fName;			//A variable to hold user input of a person's first name
		String lName;			//A variable to hold user input of a person's last name
		String iCourse;			//A variable to hold user input of an instructor's preferred course

		System.out.printf("%n%72s%n%n", "SELECTION 2 - ADDING AN INSTRUCTOR");
		System.out.print("Plese enter the instructor's first name: ");
		fName = in.nextLine();
		System.out.print("Please enter the instructor's last name: ");
		lName = in.nextLine();
		myInstructor = College.INSTANCE.addInstructor(fName, lName);
		System.out.print("Please enter the instructor's preferred course ID [Ex) CPT-102]: ");
		iCourse = in.nextLine().toUpperCase();
		matcher = pattern.matcher(iCourse);
		while (!matcher.matches()) {
			System.err.println("YOU HAVE MADE AN INVALID ENTRY. INCORRECT FORMAT. PLEASE TRY AGAIN!");
			System.out.printf("%n%s", "Please enter the instructor's preferred course ID [Ex) CPT-102]: ");
			iCourse = in.nextLine().toUpperCase();
			matcher = pattern.matcher(iCourse);
		}
		myInstructor.addPreferredCourse(iCourse);
		System.out.println("Are you finished entering preferred courses?");
		System.out.print("If so, type Y then ENTER to complete entry. type N to to input another "
				+ "preferred course: ");
		contInput = in.nextLine().toUpperCase();
		while (!contInput.equalsIgnoreCase("Y")) {	
			if (!contInput.equalsIgnoreCase("N")) {
				System.err.println("YOU HAVE MADE AN INVALID SELECTION. PLEASE TRY AGAIN!");
				System.out.println("Are you finished entering preferred courses?");
				System.out.print("If so, type Y then ENTER to complete entry. type N to to input another "
						+ "preferred course: ");
				contInput = in.nextLine().toUpperCase();
			} else {
				System.out.printf("%n%s", "Please enter the instructor's preferred course ID [Ex) CPT-102]: ");
				iCourse = in.nextLine().toUpperCase();
				matcher = pattern.matcher(iCourse);
				while (!matcher.matches()) {
					System.err.println("YOU HAVE MADE AN INVALID ENTRY. INCORRECT FORMAT. PLEASE TRY AGAIN!");
					System.out.print("Please enter the instructor's preferred course ID [Ex) CPT-102]: ");
					iCourse = in.nextLine().toUpperCase();
					matcher = pattern.matcher(iCourse);
				}
				myInstructor.addPreferredCourse(iCourse);
				System.out.println("Are you finished entering preferred courses?");
				System.out.print("If so, type Y then ENTER to complete entry. type N to to input another "
						+ "preferred course: ");
				contInput = in.nextLine().toUpperCase();
			}
		}
		College.INSTANCE.recordInstructor(myInstructor, INS_A_MASTER_FILE);
		System.out.printf("%n%70s%n", "INSTRUCTOR SUCESSFULLY ADDED!");				
	} //End of createInstructor method

	/****************************************************************************************************
	 **********								Start of printCourseList method						*********
	 ****************************************************************************************************
	 * PURP 	- Prints Neptune Technical College's entire course list, sorted by course ID.			*
	 * @param	- none																					*
	 * @return	- void																					*
	 ****************************************************************************************************/
	public static void printCourseList() {
		System.out.println("\n*******************************************************************************"
				+ "*******************************\n");
		System.out.printf("%80s%n", "Neptune Technical College - Available Course List");
		System.out.println("\n*******************************************************************************"
				+ "*******************************");

		System.out.printf("%n%-17s%s%n", "COURSE ID", "COURSE NAME");
		System.out.println("-------------------------------------------------");

		for (Course c : Catalog.INSTANCE.getCourseList()) {
			System.out.printf("%-17s%s%n", c.getCourseId(), c.getName());
		}

		System.out.println("\n*******************************************************************************"
				+ "*******************************");
	} //End of printCourseList method

	public static void printMajorList() {	
		String degreeOrCert;	//Saves either Degree or Certificate as a String
		
		System.out.println("\n*********************************************************************************"
				+ "*****************************\n");
		System.out.printf("%75s%n", "Neptune Technical College - Available Majors List");
		System.out.println("\n*********************************************************************************"
				+ "*****************************\n");
		
		for (Major m : Catalog.INSTANCE.getMajorList()) {
			System.out.printf("%n%-40s%-40s%n", "MAJOR NAME", "PROGRAM TYPE");
			System.out.println("------------------------------------------------------------");
			System.out.printf("%-40s", m.getName());
			
			if (m.getIsDegree()) {
				degreeOrCert = "Degree";
			} else {
				degreeOrCert = "Certificate";
			}

			System.out.printf("%-40s%n", degreeOrCert);
			System.out.printf("%n%34s%n", "REQUIRED COURSES");
			System.out.println("------------------------------------------------------------");
			String formattedCourses = m.getRequiredCourses().toString()
				    .replace(",", "")  //remove the commas
				    .replace("[", "")  //remove the right bracket
				    .replace("]", "")  //remove the left bracket
				    .trim();           //remove trailing spaces from partially initialized arrays
			System.out.printf(" %s%n", formattedCourses);
			System.out.printf("%nTOTAL REQUIRED CREDIT HOURS FOR %s: %d Credit Hours%n", degreeOrCert.toUpperCase(),
					m.getRequiredHours());
			System.out.println("*******************************************************************************"
					+ "*******************************");
		} //End of major printing enhanced for loop	
	} //End of printMajorList method
} //End of MainClass
