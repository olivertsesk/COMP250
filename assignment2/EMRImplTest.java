package assignment2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class EMRImplTest {

    EMR emr;
    @Before
    public void setUp() throws Exception {
        emr = new EMRImpl();
        ArrayList<Doctor> docList = new ArrayList<Doctor>();
        ArrayList<Patient> patientList = new ArrayList<Patient>();

        Doctor d1 = new Doctor("A", "B", "C", (long) 1);
        Doctor d2 = new Doctor("A", "B", "C", (long) 2);
        Doctor d3 = new Doctor("A", "B", "C", (long) 3);
        Doctor d4 = new Doctor("A", "B", "C", (long) 4);
        Doctor d5 = new Doctor("A", "B", "C", (long) 5);
        Doctor d6 = new Doctor("A", "B", "C", (long) 6);
        Doctor d7 = new Doctor("A", "B", "C", (long) 7);
        Doctor d8 = new Doctor("A", "B", "C", (long) 8);
        Doctor d9 = new Doctor("A", "B", "C", (long) 9);
        Doctor d10 = new Doctor("A", "B", "C", (long) 10);
        Doctor d11 = new Doctor("A", "B", "C", (long) 11);
        Doctor d12 = new Doctor("A", "B", "C", (long) 12);
        docList.add(d2);
        docList.add(d4);
        docList.add(d6);
        docList.add(d8);
        docList.add(d10);
        docList.add(d12);
        docList.add(d1);
        docList.add(d3);
        docList.add(d5);
        docList.add(d7);
        docList.add(d9);
        docList.add(d11);

        emr.setDoctorList(docList);


        Patient p1 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 1, "");
        Patient p2 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 2, "");
        Patient p3 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 3, "");
        Patient p4 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 4, "");
        Patient p5 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 5, "");
        Patient p6 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 6, "");
        Patient p7 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 7, "");
        Patient p8 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 8, "");
        Patient p9 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 9, "");
        Patient p10 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 10, "");
        Patient p11 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 11, "");
        Patient p12 = new Patient("","",1,"", Patient.Insurance.NONE,(long) 12, "");
        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);
        patientList.add(p5);
        patientList.add(p6);
        patientList.add(p7);
        patientList.add(p8);
        patientList.add(p9);
        patientList.add(p10);
        patientList.add(p11);
        patientList.add(p12);

        emr.setPatientList(patientList);
    }

    @Test
    public void testSetDoctorList() throws Exception {
        ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
        emr.setDoctorList(doctorArrayList);
        assertEquals(emr.getDoctorList(), doctorArrayList);

        doctorArrayList = new ArrayList<Doctor>();
        Doctor d1 = new Doctor("A", "B", "C", (long) 12);
        doctorArrayList.add(d1);
        assertNotEquals(emr.getDoctorList(), doctorArrayList);

        doctorArrayList = new ArrayList<Doctor>();
        Doctor d11 = new Doctor("A", "B", "C", (long) 11);
        Doctor d12 = new Doctor("A", "B", "C", (long) 12);
        doctorArrayList.add(d11);
        doctorArrayList.add(d12);
        emr.setDoctorList(doctorArrayList);
        assertEquals(emr.getDoctorList(), doctorArrayList);

        doctorArrayList = new ArrayList<Doctor>();
        assertNotEquals(emr.getDoctorList(), doctorArrayList);
    }

    @Test
    public void testSetPatientList() throws Exception {
        ArrayList<Patient> PatientArrayList = new ArrayList<Patient>();
        emr.setPatientList(PatientArrayList);
        assertEquals(emr.getPatientList(), PatientArrayList);

        PatientArrayList = new ArrayList<Patient>();
        Patient d1 = new Patient("A", "B",1, "C", Patient.Insurance.NONE,(long) 12,"1-1-1000");
        PatientArrayList.add(d1);
        assertNotEquals(emr.getPatientList(), PatientArrayList);

        PatientArrayList = new ArrayList<Patient>();
        Patient d11 = new Patient("A", "B",1, "C", Patient.Insurance.NONE,(long) 11,"1-1-1000");
        Patient d12 = new Patient("A", "B",1, "C", Patient.Insurance.NONE,(long) 12,"1-1-1000");
        PatientArrayList.add(d11);
        PatientArrayList.add(d12);
        emr.setPatientList(PatientArrayList);
        assertEquals(emr.getPatientList(), PatientArrayList);

        PatientArrayList = new ArrayList<Patient>();
        assertNotEquals(emr.getPatientList(), PatientArrayList);
    }

    @Test
    public void testSortDoctors() throws Exception {
        Random random = new Random();
        ArrayList<Doctor> newDocs = new ArrayList<Doctor>();
        for (int i = 0; i<100000; i++){
            newDocs.add(new Doctor("A","B","A", Math.abs(random.nextLong())));
        }
        emr.setDoctorList(newDocs);

        emr.sortDoctors(emr.getDoctorList());
        ArrayList<Doctor> doctorArrayList = emr.getDoctorList();
        for (int i = 1; i<doctorArrayList.size(); i++){
            assertTrue(doctorArrayList.get(i).compareTo(doctorArrayList.get(i-1))>=0);
        }
    }

    @Test
    public void testSortPatients() throws Exception {
        Random random = new Random();
        ArrayList<Patient> patientArrayList = new ArrayList<Patient>();
        for (int i = 0; i<100000; i++){
            patientArrayList.add(new Patient("A", "B",123, "A", Patient.Insurance.NONE, Math.abs(random.nextLong()),"12-12-12"));
        }
        emr.setPatientList(patientArrayList);

        emr.sortPatients(emr.getPatientList());
        ArrayList<Patient> patientList = emr.getPatientList();
        for (int i = 1; i<patientList.size(); i++){
            try{
                assertTrue(patientList.get(i).compareTo(patientList.get(i-1))>=0);
            }
            catch (AssertionError error){
                System.out.println("i: "+String.valueOf(patientList.get(i).getHospitalIDLong()));
                System.out.println("i-1: "+String.valueOf(patientList.get(i-1).getHospitalIDLong()));
                System.out.println(i);
                break;
            }
        }
    }
}