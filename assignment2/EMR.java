package assignment2;

import assignment2.Patient.Insurance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/* ACADEMIC INTEGRITY STATEMENT
 *
 * By submitting this file, we state that all group members associated
 * with the assignment understand the meaning and consequences of cheating,
 * plagiarism and other academic offenses under the Code of Student Conduct
 * and Disciplinary Procedures (see www.mcgill.ca/students/srr for more information).
 *
 * By submitting this assignment, we state that the members of the group
 * associated with this assignment claim exclusive credit as the authors of the
 * content of the file (except for the solution skeleton provided).
 *
 * In particular, this means that no part of the solution originates from:
 * - anyone not in the assignment group
 * - Internet resources of any kind.
 *
 * This assignment is subject to inspection by plagiarism detection software.
 *
 * Evidence of plagiarism will be forwarded to the Faculty of Science's disciplinary
 * officer.
 */

public class EMR
{
	private String aDoctorFilePath;
	private String aPatientFilePath;
	private String aVisitsFilePath;
	private ArrayList<Doctor> doctorList;
	private ArrayList<Patient> patientList;
	
	/**
     * Used to invoke the EMR command line interface. You only need to change
     * the 3 filepaths.
	 */
	public static void main(String[] args) throws IOException
	{
        String base= "/Users/David/repositories/comp250_assignments/comp250_assignment2/Data/";
		EMR system = new EMR(base+"Doctors.csv",base+"Patients.csv", base+"Visits.csv");
		system.displayMenu();
	}
	
	
	/**
	 * You don't have to modify the constructor, nor its code
	 * @param pDoctorFilePath path to file
	 * @param pPatientFilePath path to file
	 * @param pVisitsFilePath path to file
	 */
	public EMR(String pDoctorFilePath, String pPatientFilePath, String pVisitsFilePath){
		this.aDoctorFilePath = pDoctorFilePath;
		this.aPatientFilePath = pPatientFilePath;
		this.aVisitsFilePath = pVisitsFilePath;

		this.doctorList = importDoctorsInfo(this.aDoctorFilePath);
		this.patientList = importPatientInfo(this.aPatientFilePath);

		sortDoctors(this.doctorList);
        sortPatients(this.patientList);

		importVisitData(this.aVisitsFilePath);

    }

    public EMR(){}

    public void setDoctorList(ArrayList<Doctor> doctorList){
        this.doctorList=doctorList;
    }

    public void setPatientList(ArrayList<Patient> patientList) {
        this.patientList = patientList;
    }

    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    /**
	 * This method should sort the doctorList in time O(n^2). It should sort the Doctors
	 * based on their ID 
	 */
    public void sortDoctors(ArrayList<Doctor> docs){
	    ArrayList<Doctor> sortedDocs = new ArrayList<Doctor>();
        for (Doctor doc : docs){
            if (sortedDocs.size()==0){
                sortedDocs.add(doc);
            } else {
                sortedDocs.add(searchIndex(sortedDocs, doc), doc);
            }
        }
        for (int j = 0; j <sortedDocs.size(); j++){
            docs.set(j, sortedDocs.get(j));
        }
    }

    /**
     * return the index where the doctor should be stored
     * @param list the list of current doctors
     * @param key the new doctor to sort
     * @return the proper index where to insert the doc
     */
    int searchIndex(List<Doctor> list, Doctor key){
        int right = list.size()-1;

        if (right == -1) return 0;
        else if(right==0){
            if (list.get(right).compareTo(key)>0) return 0;
            else return 1;
        }

        int mid = right/2;

        if(list.get(mid).compareTo(key) > 0){
            //current is larger than key
            return searchIndex(list.subList(0,mid),key);
        } else {
            //current is smaller than key
            return mid+1+searchIndex(list.subList(mid+1,right+1),key);
        }
    }


    /**
     * return the index where the patient should be stored
     * @param list the list of current patient
     * @param key the new patient to sort
     * @return the proper index where to insert the patient
     */
    int searchIndex(List<Patient> list, Patient key){
        int right = list.size()-1;

        if (right == -1) return 0;
        else if(right==0){
            if (list.get(right).compareTo(key)>0) return 0;
            else return 1;
        }

        int mid = right/2;

        if(list.get(mid).compareTo(key) > 0){
            //current is larger than key
            return searchIndex(list.subList(0,mid),key);
        } else {
            //current is smaller than key
            return mid+1+searchIndex(list.subList(mid+1,right+1),key);
        }
    }

    /**
	 * This method should sort the patientList in time O(n log n). It should sort the 
	 * patients based on the hospitalID
	 */
    public void sortPatients(ArrayList<Patient> patients){
        ArrayList<Patient> sortedPatients = new ArrayList<Patient>();
        for (Patient patient : patients){
            //linear traversal => O(n)
            if (sortedPatients.size()==0){
                sortedPatients.add(patient);
            } else {
                //find index in list
                //binary search => O(log(n))
                sortedPatients.add(searchIndex(sortedPatients, patient), patient);
            }
        }
        for (int j = 0; j <sortedPatients.size(); j++){
            patients.set(j, sortedPatients.get(j));
        }
        //total is n*log(n)

	}
	
	/**
	 * This method adds takes in the path of the Doctor sheet csv file and imports
	 * all doctors data into the doctorList ArrayList
	 */
	private ArrayList<Doctor> importDoctorsInfo(String doctorFilePath){
        ArrayList<Doctor> existingDocs = new ArrayList<Doctor>();
        try
        {
            FileReader fr = new FileReader(doctorFilePath);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip the first line
            String stringRead = br.readLine();

            while( stringRead != null )
            {
                String [] docInfo = stringRead.split(",");
                try {
                    existingDocs.add(new Doctor(docInfo[0], docInfo[1], docInfo[2], Long.parseLong(docInfo[3])));
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println("Failed to parse:\n"+stringRead);
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse ID: "+docInfo[3]);
                }
                // read the next line
                stringRead = br.readLine();
            }
            br.close();
            fr.close();
        }

        catch(IOException ioe){
            ioe.printStackTrace();
        }

        return existingDocs;
	}
	
	/**
	 * This method adds takes in the path of the Patient sheet csv file and imports
	 * all Patient data into the patientList ArrayList
	 */
	private ArrayList<Patient> importPatientInfo(String patientFilePath){
        ArrayList<Patient> existingPatients = new ArrayList<Patient>();
        try
        {
            FileReader fr = new FileReader(patientFilePath);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip the first line
            String stringRead = br.readLine();

            while( stringRead != null )
            {
                String [] patientInfo = stringRead.split(",");
                try {
                    Insurance insurance;
                    if (patientInfo[3].equals("RAMQ")){
                        insurance = Insurance.RAMQ;
                    } else if (patientInfo[3].equals("Private")){
                        insurance = Insurance.Private;
                    } else {
                        insurance = Insurance.NONE;
                    }

                    existingPatients.add(new Patient(patientInfo[0], patientInfo[1], Double.parseDouble(patientInfo[2]),
                            patientInfo[4], insurance, Long.parseLong(patientInfo[5]), patientInfo[6]));
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println("Failed to parse:\n"+stringRead);
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse ID: "+patientInfo[3]);
                }
                // read the next line
                stringRead = br.readLine();
            }
            br.close();
            fr.close();
        }

        catch(IOException ioe){
            ioe.printStackTrace();
        }

        return existingPatients;
    }
	
	/**
	 * This method adds takes in the path of the Visit sheet csv file and imports
	 * every Visit data. It appends Visit objects to their respective Patient
	 */
	private void importVisitData(String visitsFilePath){
        try
        {
            FileReader fr = new FileReader(visitsFilePath);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip the first line
            String stringRead = br.readLine();

            while( stringRead != null )
            {
                String [] visitInfo = stringRead.split(",");
                try {
                   //HospitalID,DoctorID,Date,DoctorNote
                    Patient currentPatient = findPatientByID(Long.parseLong(visitInfo[0]));

                    if (currentPatient == null){
                        throw new NullPointerException("Patient not found" + Arrays.toString(visitInfo));
                    }
                    Doctor currentDoc = findDoctor(Long.parseLong(visitInfo[1]));
                    if (currentDoc == null){
                        throw new NullPointerException("DoctorId not found" + Arrays.toString(visitInfo));
                    }
                    Visit newVisit = new Visit(currentDoc,currentPatient, visitInfo[2], visitInfo[3]);
                    currentPatient.addVisit(newVisit);
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println("Failed to parse:\n"+stringRead);
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse ID: "+visitInfo[0]+ " or: "+visitInfo[1]);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
                // read the next line
                stringRead = br.readLine();
            }
            br.close();
            fr.close();
        }

        catch(IOException ioe){
            ioe.printStackTrace();
        }

	}
	
	/**
	 * This method uses an infinite loop to simulate the interface of the EMR system.
	 * A user should be able to select 10 options. The loop terminates when a user 
	 * chooses option 10: EXIT. You do not have to modify this code.
	 */
	public void displayMenu(){
		System.out.println();
		System.out.println("****************************************************************");
		System.out.println();
		System.out.println("Welcome to The Royal Victoria EMR Interface V1.0");
		System.out.println("");
		System.out.println("This system will allow you to access and modify the health records of the hospital");
		System.out.println();
		System.out.println("****************************************************************");
		System.out.println();
		
		Scanner scan = new Scanner(System.in);
		boolean exit = false;
		while(!exit){
			
			System.out.println("Please select one of the following options and click enter:");
			System.out.println("   (1) Add a new patient to the EMR system\n" +
								"   (2) Add a new Doctor to the EMR system\n" +
								"   (3) Record new patient visit to the department\n" +
								"   (4) Edit patient information\n" +
								"   (5) Display list of all Patient IDs\n" +
								"   (6) Display list of all Doctor IDs\n" +
								"   (7) Print a Doctor's record\n" +
								"   (8) Print a Patient's record\n" +
								"   (9) Exit and save modifications\n");
			System.out.print("   ENTER YOUR SELECTION HERE: ");
			
			int choice;
			try{
				choice = Integer.parseInt(scan.next());
			}
			catch(Exception e){
				choice=0;
			}
			
			System.out.println("\n");
			
			switch(choice){
				case 1: 
					option1();
					break;
				case 2:
					option2();
					break;
				case 3: 
					option3();
					break;
				case 4: 
					option4();
					break;
				case 5: 
					option5();
					break;
				case 6: 
					option6();
					break;
				case 7: 
					option7();
					break;
				case 8: 
					option8();
					break;
				case 9:
                    exit=true;
					option9();
					break;	
				default:
					System.out.println("   *** ERROR: You entered an invalid input, please try again ***\n");
					break;
			}
		}
	}
	
	/**
	 * This method adds a patient to the end of the patientList ArrayList. It 
	 * should ask the user to provide all the input to create a Patient object. The 
	 * user should not be able to enter empty values. The input should be supplied
	 * to the addPatient method
	 */
	private void option1(){

        Scanner scan = new Scanner(System.in);

        System.out.print("Patient's first name: ");
        String firstname = scan.next();
        System.out.print("Patient's last name: ");
        String lastname = scan.next();
        double height;
        while (true){
            try {
                System.out.print("Patient's height: ");
                height = Double.parseDouble(scan.next());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid height");
            }
        }
        System.out.print("Patient's gender: ");
        String Gender = scan.next();
        Long hospitalID;
        while (true) {
            try {
                System.out.print("Patient's hospital ID : ");
                hospitalID = Long.parseLong(scan.next());
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid ID.");
            }
        }
        System.out.print("Patient's date of birth: ");
        String DOB = scan.next();
        Insurance type = null;
        while (type == null){
            System.out.print("Patient's insurance: ");
            String input = scan.next();
            if (input.equals("RAMQ")){
                type = Insurance.RAMQ;
            } else if (input.equals("Private")){
                type = Insurance.Private;
            } else if (input.equals("NONE")){
                type = Insurance.NONE;
            } else {
                System.out.println("Please enter a valide type i.e. RAMQ, Private, NONE.");
            }
        }

		
		addPatient(firstname, lastname, height, Gender, type, hospitalID, DOB);
	}
	
	/**
	 * This method adds a patient object to the end of the patientList ArrayList. 
	 */
	private void addPatient(String firstname, String lastname, double height, String Gender, Insurance type, Long hospitalID, String DOB){
        if (findPatientByID(hospitalID) != null){
            System.out.println("Existing ID, please enter unique identifier\n");
            return;
        }
        Patient newPatient = new Patient(firstname,lastname,height,Gender,type,hospitalID,DOB);
        int i = 0;
        while (i < patientList.size() && patientList.get(i).getHospitalIDLong() < newPatient.getHospitalIDLong()){
            i++;
        }
        patientList.add(i, newPatient );
        save(newPatient);
	}
	
	
	/**
	 * This method adds a doctor to the end of the doctorList ArrayList. It 
	 * should ask the user to provide all the input to create a Doctor object. The 
	 * user should not be able to enter empty values.
	 */
	private void option2(){
		String firstname;
		String lastname;
		String specialty;
		Long doctor_id;

        Scanner scan = new Scanner(System.in);
        System.out.print("Doctor's first name: ");
        firstname = scan.next();
        System.out.print("Doctor's last name: ");
        lastname = scan.next();
        System.out.print("Speciality: ");
        specialty = scan.next();
        System.out.print("Doctor's id: ");
        doctor_id = Long.parseLong(scan.next());

        addDoctor(firstname, lastname, specialty, doctor_id);

	}
	
	/**
	 * This method adds a doctor to the end of the doctorList ArrayList.
	 */
	public void addDoctor(String firstname, String lastname, String specialty, Long docID){
        if (findDoctor(docID) != null){
            System.out.println("Existing ID, please enter unique identifier\n");
            return;
        }
        Doctor newDoc = new Doctor(firstname, lastname, specialty, docID);

        int i = 0;
        while (i < doctorList.size() && doctorList.get(i).getID() < newDoc.getID()){
            i++;
        }
        doctorList.add(i, newDoc);
        save(newDoc);
	}
	
	/**
	 * This method creates a Visit record. 
	 */
	private void option3(){

		Long doctorID;
		Long patientID;
		String date;
		String note;

        Scanner scan = new Scanner(System.in);
        System.out.print("Note:");
        note = scan.nextLine();
        System.out.print("Patient's ID: ");
        patientID = Long.parseLong(scan.next());
        System.out.print("Date: ");
        date = scan.next();
        System.out.print("Doctor's id: ");
        doctorID = Long.parseLong(scan.next());

		//Use above variables to find which Doctor the patient saw
		Doctor d = findDoctor(doctorID);
		Patient p = findPatientByID(patientID);
		
		recordPatientVisit(d, p, date, note);
	}
	
	/**
	 * This method creates a Visit record. It adds the Visit to a Patient object.
	 */
	private void recordPatientVisit(Doctor doctor, Patient patient, String date, String note){
		assert doctor!=null && patient!=null;

        Visit newVisit = new Visit(doctor,patient,date,note);
        patient.addVisit(newVisit);
        save(newVisit);
	}
	
	/**
	 * This method edits a Patient record. Only the firstname, lastname, height,
	 * Insurance type, and date of birth could be changed. You should ask the user to supply the input.
	 */
	private void option4(){
	    Scanner scan = new Scanner(System.in);

		// for each of the 5 variables
        System.out.print("Enter the changed first name: ");
        String newFirstname = scan.next();
        System.out.print("Enter the changed last name: ");
        String newLastname = scan.next();
        double newHeight;
        while (true) {
            System.out.print("Enter the Patient's height: ");
            try {
                newHeight = Double.parseDouble(scan.next());
                break;
            }
            catch (Exception e){
                System.out.println("Invalid format, please try again. ");
            }
        }
        Insurance newType = null;
        while (newType == null){
            System.out.print("Patient's insurance: ");
            String input = scan.next();
            if (input.equals("RAMQ")){
                newType = Insurance.RAMQ;
            } else if (input.equals("Private")){
                newType = Insurance.Private;
            } else if (input.equals("NONE")){
                newType = Insurance.NONE;
            } else {
                System.out.println("Please enter a valide type i.e. RAMQ, Private, NONE.");
            }
        }
        System.out.print("Date of birth: ");
        String newDOB = scan.next();
		
		editPatient(newFirstname, newLastname, newHeight, newType, newDOB);
	}
	
	/**
	 * This method edits a Patient record. Only the firstname, lastname, height, 
	 * Insurance type, address could be changed, and date of birth. 
	 */
	private void editPatient(String firstname, String lastname, double height, Insurance type, String DOB){
        Patient patient;
        Scanner scan = new Scanner(System.in);
        Long id;
        while (true) {
            System.out.print("Enter the Patient's ID: ");
            try {
                id = Long.parseLong(scan.next());
                patient = findPatientByID(id);
                if (patient == null){
                    throw new NullPointerException("Patient Id not found");
                }
                break;
            }
            catch (Exception e){
                System.out.println("Invalid ID, please try again. ");
            }
        }
        patient.setDateOfBirth(DOB);
        patient.setFirstName(firstname);
        patient.setHeight(height);
        patient.setInsurance(type);
        patient.setLastName(lastname);
	}
	
	/**
	 * This method should print to screen
	 * one Patient at a time by calling the displayPatients() method
	 */
	private void option5(){
		displayPatients(this.patientList);
	}
	
	/**
	 * This method should print to screen 
	 * one Patient at a time by calling the Patient toString() method
	 */
	private void displayPatients(ArrayList<Patient> patients){
		for (Patient patient : patients){
            System.out.println(patient.toString());
        }
        System.out.println("");
	}
	
	/**
	 * This method should print to screen
	 * one Doctor at a time by calling the displayDoctors() method
	 */
	private void option6(){
		displayDoctors(this.doctorList);
	}

	/**
	 * This method should print to screen
	 * one Doctor at a time by calling the Doctor toString() method
	 */
	private void displayDoctors(ArrayList<Doctor> docs){
        for (Doctor doctor : docs){
            System.out.println(doctor.toString());
        }
        System.out.println("");
	}

	
	/**
	 * This method should ask the user to supply an id of the patient they want info about
	 */
	private void option8(){
		Scanner scan = new Scanner(System.in);
        Long patientID;
        while (true) {
            try {
                System.out.print("Enter the patient ID: ");
                patientID = Long.parseLong(scan.next());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid ID");
            }
        }
        printPatientRecord(patientID);
	}
	
	/**
	 * This method should call the toString method of a specific Patient. It should
	 * also list all the patient's Visit objects sorted in order by date (earliest first). For
	 * every Visit, the doctor's firstname, lastname and id should be printed as well.
	 */
	private void printPatientRecord(Long patientID){
        Patient patient = findPatientByID(patientID);
        if (patient==null) {
            System.out.println("Patient not found");
        }
        else {
            System.out.println(patient.toString());
            for (Visit visit : patient.getaVisitList()){
                System.out.println(visit.toString());
            }
        }
	}

    private Patient findPatientByID(Long patientID){
        int left = 0;
        int right = patientList.size()-1;
        while (left <= right){
            int midIndex = left + (right-left)/2;
            if (patientID.equals(patientList.get(midIndex).getHospitalIDLong())){
                return patientList.get(midIndex);
            }
            if (patientID.compareTo(patientList.get(midIndex).getHospitalIDLong())<0){
                right = midIndex-1;
            } else {
                left = midIndex+1;
            }
        }
        return null;
    }

    /**
     * Searches in O(log n) time the doctorList to find the correct doctor with doctorID = id
     * @param docID the doctor id
     * @return the corresponding Doctor
     */
    private Doctor findDoctor(Long docID){
        int left = 0;
        int right = doctorList.size()-1;
        while (left <= right){
            int midIndex = left + (right-left)/2;
            if (docID.equals(doctorList.get(midIndex).getID())){
                return doctorList.get(midIndex);
            }
            if (docID.compareTo(patientList.get(midIndex).getHospitalIDLong())<0){
                right = midIndex-1;
            } else {
                left = midIndex+1;
            }
        }
    return null;
}

	/**
	 * This method should ask the user to supply an id of a doctor they want info about
	 */
	private void option7(){

        Scanner scan = new Scanner(System.in);
        Long docId;
        while (true) {
            try {
                System.out.print("Enter the doctor ID: ");
                docId = Long.parseLong(scan.next());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid ID");
            }
        }
        printDoctorRecord(findDoctor(docId));
	}

	
	/**
	 * This method should call the toString() method of a specific Doctor. It should
	 * also find and list all the patients that a Doctor has seen by calling their toString()
	 * method as well. It should also list the date that the doctor saw a particular patient
	 */
	private void printDoctorRecord(Doctor d){
        System.out.println(d.toString());
        for (Patient patient : patientList){
            for (Visit visit : patient.getaVisitList()){
                if (visit.getDoctor().equals(d)){
                    System.out.println(patient.toString());
                    break;
                }
            }
        }
	}
	
	/**
	 * This method should be invoked from the command line interface if the user
	 * would like to quit the program. This method should export all the Doctor, Patient and 
	 * Visit data by overwriting the contents of the 3 original files.
	 */
	private void option9(){
		exitAndSave();
	}
	
	
	/**
	 * Export all the Doctor, Patient and Visit data by overwriting the contents of the 3 original csv files.
	 */
	private void exitAndSave(){
        save();
	}


    private final static String LINE_TERMINATOR = "\n";
    private final static String ITEM_SEPARATOR = ",";

    /**
     * Export the Doctor, Patients and Visits to file
     */
    private void save() {
        try {
            FileWriter docFw = new FileWriter(aDoctorFilePath);
            FileWriter patientFw = new FileWriter(aPatientFilePath);
            FileWriter visitFw = new FileWriter(aVisitsFilePath);

            docFw.write("Firstname,Lastname,Specialty,DoctorID");
            for (int i = doctorList.size()-1; i > -1; i--){
                //reverse order will make sorting after import much faster than straight
                Doctor doctor = doctorList.get(i);
                docFw.write(LINE_TERMINATOR);

                docFw.write(doctor.getFirstName());
                docFw.write(ITEM_SEPARATOR);
                docFw.write(doctor.getLastName());
                docFw.write(ITEM_SEPARATOR);
                docFw.write(doctor.getSpecialty());
                docFw.write(ITEM_SEPARATOR);
                docFw.write(String.valueOf(doctor.getID()));
            }
            docFw.close();


            patientFw.write("FirstName,LastName,Height (cm),Insurance,Gender,HospitalID,Date of Birth (mm-dd-yyyy)");
            for (int i =patientList.size()-1; i>-1; i--) {
                //reverse order will make sorting after import much faster than straight
                Patient patient = patientList.get(i);

                patientFw.write(LINE_TERMINATOR);

                patientFw.write(patient.getFirstName());
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(patient.getLastName());
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(String.valueOf(patient.getHeight()));
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(String.valueOf(patient.getaInsurance()));
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(patient.getaGender());
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(String.valueOf(patient.getHospitalIDLong()));
                patientFw.write(ITEM_SEPARATOR);
                patientFw.write(patient.getDateOfBirth());
            }
            patientFw.close();

            visitFw.write("HospitalID,DoctorID,Date,DoctorNote");
            for (int i =patientList.size()-1; i>-1; i--) {
                for (Visit visit : patientList.get(i).getaVisitList()) {
                    visitFw.write(LINE_TERMINATOR);

                    visitFw.write(String.valueOf(visit.getPatient().getHospitalIDLong()));
                    visitFw.write(ITEM_SEPARATOR);
                    visitFw.write(String.valueOf(visit.getDoctor().getID()));
                    visitFw.write(ITEM_SEPARATOR);
                    visitFw.write(visit.getDate());
                    visitFw.write(ITEM_SEPARATOR);
                    visitFw.write(visit.getNote());


                }
            }
            visitFw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a new doctor to file, prevents information loss in case of crash
     * @param doctor the new doctor to save
     */
    protected void save(Doctor doctor) {

        try {
            FileWriter docFw = new FileWriter(aDoctorFilePath, true);

            docFw.write(LINE_TERMINATOR);

            docFw.write(doctor.getFirstName());
            docFw.write(ITEM_SEPARATOR);
            docFw.write(doctor.getLastName());
            docFw.write(ITEM_SEPARATOR);
            docFw.write(doctor.getSpecialty());
            docFw.write(ITEM_SEPARATOR);
            docFw.write(String.valueOf(doctor.getID()));

            docFw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(Patient patient) {
        try {
            FileWriter patientFw = new FileWriter(aPatientFilePath, true);

            patientFw.write(LINE_TERMINATOR);

            patientFw.write(patient.getFirstName());
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(patient.getLastName());
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(String.valueOf(patient.getHeight()));
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(String.valueOf(patient.getaInsurance()));
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(patient.getaGender());
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(String.valueOf(patient.getHospitalIDLong()));
            patientFw.write(ITEM_SEPARATOR);
            patientFw.write(patient.getDateOfBirth());

            patientFw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(Visit visit) {
        //TODO: Make it work
        try {
            FileWriter visitFw = new FileWriter(aVisitsFilePath, true);

            visitFw.write(LINE_TERMINATOR);

            visitFw.write(String.valueOf(visit.getPatient().getHospitalIDLong()));
            visitFw.write(ITEM_SEPARATOR);
            visitFw.write(String.valueOf(visit.getDoctor().getID()));
            visitFw.write(ITEM_SEPARATOR);
            visitFw.write(visit.getDate());
            visitFw.write(ITEM_SEPARATOR);
            visitFw.write(visit.getNote());

            visitFw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

/**
 * This simple class just keeps the information about
 * a Patient together. You will have to Modify this class
 * and fill in missing data.
 */
class Patient implements Comparable<Patient>
{
    @Override
    public int compareTo(Patient o) {
        return this.aHospitalID.compareTo(o.getHospitalIDLong());
    }

    public enum Insurance {RAMQ, Private, NONE}
	
	private String aFirstName;
	private String aLastName;
	private double aHeight;
	private String aGender;
	private Insurance aInsurance;
	private Long aHospitalID;
	private String aDateOfBirth; //ex. 12-31-1988 (Dec. 31st, 1988)
	private ArrayList<Visit> aVisitList;
	
	public Patient(String pFirstName, String pLastName, double pHeight, String pGender, Insurance pInsurance,
			Long pHostpitalID, String pDateOfBirth)
	{
        this.aFirstName = pFirstName;
        this.aLastName = pLastName;
        this.aHeight = pHeight;
        this.aGender = pGender;
        this.aInsurance = pInsurance;
        this.aHospitalID = pHostpitalID;
        this.aDateOfBirth = pDateOfBirth;
        aVisitList = new ArrayList<Visit>();
    }

    public Insurance getaInsurance() {
        return aInsurance;
    }

    public ArrayList<Visit> getaVisitList() {
        return aVisitList;
    }

    public double getHeight(){
        return aHeight;
    }
	public String getFirstName()
	{
		return aFirstName;
	}

	public String getLastName()
	{
		return aLastName;
	}

    public long getHospitalIDLong()
    {
        return aHospitalID;
    }

    public String getHospitalID()
    {
        return String.valueOf(aHospitalID);
    }

	public String getDateOfBirth()
	{
		return aDateOfBirth;
	}

    public String getaGender() {
        return aGender;
    }

    public void addVisit(Visit visit){
        aVisitList.add(visit);
	}

	public void addVisit(String vDate, Doctor vDoctor){
        addVisit(new Visit(vDoctor, this, vDate, ""));
    }
    
	public void setFirstName(String fname){
		this.aFirstName = fname;
	}
	
	public void setLastName(String lname){
		this.aLastName = lname;
	}
	
	public void setHeight(double height){
		this.aHeight = height;
	}
	
	public void setInsurance(Insurance type){
		this.aInsurance = type;
	}
	
	public void setDateOfBirth(String dob){
		this.aDateOfBirth = dob;
	}
	
	/**
	 * This method should print all the Patient's info. "ID, Lastname, Firstname, etc..."
	 */
	public String toString(){
        return ("{"+String.valueOf(aHospitalID) + ", " + aLastName + ", " + aFirstName + ", " + aHeight + ", " + aGender + ", " + aInsurance + ", " + aDateOfBirth+"}");
	}
}

/**
 * This simple class just keeps the information about
 * a Doctor together. Do modify this class as needed.
 */
class Doctor implements Comparable<Doctor>
{
	private String aFirstName;
	private String aLastName;
	private String aSpecialty; 
	private Long aID;
	
	public Doctor(String pFirstName, String pLastName, String pSpecialty, Long pID)
	{
        this.aSpecialty = pSpecialty;
        this.aFirstName = pFirstName;
        this.aLastName = pLastName;
        this.aID = pID;
	}
	
	public String getFirstName()
	{
		return aFirstName;
	}
	
	public String getLastName()
	{
		return aLastName;
	}

	public String getSpecialty(){
		return aSpecialty;
	}

	public Long getID(){
		return aID;
	}

    /**
	 * This method should print all the Doctor's info. "ID, Lastname, Firstname, Specialty"
	 */
	public String toString(){
		return ("{"+String.valueOf(aID) + ", " + aLastName + ", " + aFirstName + ", " + aSpecialty+"}");
	}

    @Override
    public int compareTo(Doctor o) {
        return this.getID().compareTo(o.getID());
    }
}

/**
 * This simple class just keeps the information about
 * a Visit together. Do modify this class as needed.
 */
class Visit
{
	private Doctor aDoctor;
	private Patient aPatient;
	private String aDate; 
	private String aNote;
	
	public Visit(Doctor aDoctor, Patient aPatient, String aDate, String aNote)
	{
        this.aDoctor = aDoctor;
        this.aPatient = aPatient;
        this.aDate = aDate;
        this.aNote = aNote;
    }


    public Doctor getDoctor() {
        return aDoctor;
    }

    public Patient getPatient() {
        return aPatient;
    }

    public String getDate() {
        return aDate;
    }

    public String getNote() {
        return aNote;
    }

    public String toString(){
        return "{"+aDate+", Note: "+aNote;
    }
}