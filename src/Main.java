import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int option;
        boolean exit = false;
        Students list = new Students();
        list = readAllStudents("Files");
        Scanner sc = new Scanner(System.in);

        while(exit == false){

            System.out.println("============ MENU ============");
            System.out.println("1. Show Students List ");
            System.out.println("2. Show Student Family ");
            System.out.println("3. Add New Student ");
            System.out.println("4. Modify Student ");
            System.out.println("5. Show Informe ");
            System.out.println("6. Save and Exit ");
            System.out.println("================================");
            System.out.println("Introduce the number of the option you want to execute:");

            option = sc.nextInt();

            while (option < 1 || option > 6) {
                System.out.println("Enter a valid option:");
                option = sc.nextInt();
            }

            switch (option) {
                case 1:
                    displayAllStudents(list);
                    break;
                case 2:
                    showStudentFamily(list, sc);
                    break;
                case 3:
                    addNewStudent(list, sc);
                    break;
                case 4:
                    modifyStudent(list, sc);
                    break;
                case 5:
                    mostrarInforme(list, sc);
                    break;
                case 6:
                    saveAllStudents(list);
                    exit = true;
                    break;
            }
            System.out.println();
        }
    }
    private static Students readAllStudents(String folderPath){
        Students student  = new Students();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    BinaryTree bt = new BinaryTree(folderPath + "/" + file.getName());
                    bt.displayTree();
                    student.addStudent(bt);
                }
            }
        }

        return student;
    }

    private static void saveAllStudents(Students StudentsList){
        ArrayList<String> students = StudentsList.getAllStudentsName();
        for (String student : students) {
            BinaryTree bt = StudentsList.getStudent(student);
            bt.preOrderSave();
        }

    }

    private static void displayAllStudents(Students StudentsList){
        ArrayList<String> students = StudentsList.getAllStudentsName();
        if(students.isEmpty()){
            throw new NoSuchElementException("The list of students is empty");
        }
        System.out.println("The list of students is:");
        for (String student : students) {
            System.out.println("- " + student);
        }
    }

    private static void showStudentFamily(Students studentList, Scanner sc) {
        System.out.println("Enter the name of the student you want to see the family of:");
        sc.nextLine();
        String name = sc.nextLine();
        BinaryTree bt = studentList.getStudent(name);
        if (bt != null) {
            System.out.println("The family of " + name + " is represented by:");
            bt.displayTree();
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void addNewStudent(Students StudentList, Scanner sc){
        System.out.println("Enter the name of new Student:");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter the marital status of the student:");
        String maritalStatus = sc.nextLine();
        System.out.println("Enter the place of birth of the student:");
        String placeOfBirth = sc.nextLine();
        BinaryTree bt = new BinaryTree();
        String s = "Name: " + name + ", place of Origin: " + placeOfBirth + ", marital Status: " + maritalStatus;
        bt.addNode(new Person(s), "");
        StudentList.addStudent(bt);
    }

    private static void modifyStudent(Students StudentList, Scanner sc) {
        System.out.println("Enter the name of the student you want to modify:");
        sc.nextLine();
        String name = sc.nextLine();
        BinaryTree bt = StudentList.getStudent(name);

        if (bt == null) {
            throw new NoSuchElementException("The student does not exist");
        }

        System.out.println("- OPTIONS:");
        System.out.println("1. Add a family member");
        System.out.println("2. Remove a family member");
        System.out.println();

        System.out.println("Enter an option:");
        int option = sc.nextInt();
        sc.nextLine();

        while (option < 1 || option > 2) {
            System.out.println("Enter a valid option:");
            option = sc.nextInt();
            sc.nextLine();
        }

        if (option == 1) {
            System.out.println("Add family member");
            System.out.println("Enter the name of the family member you want to add:");
            name = sc.nextLine();
            System.out.println("Enter the marital status of the family member:");
            String maritalStatus = sc.nextLine();
            System.out.println("Enter the place of birth of the family member:");
            String placeOfBirth = sc.nextLine();
            String s = "Name: " + name + ", place of Origin: " + placeOfBirth + ", marital Status: " + maritalStatus;
            System.out.println("Introdueix la ruta, L o R, ex: LRL , RRL , LRR , etc.");
            String ruta = sc.nextLine();
            if(bt.addNode(new Person(s), ruta)){
                System.out.println("Family member added successfully.");
            } else {
                System.out.println("Error adding family member.");
            }
        }
        else if (option == 2) {
            System.out.println("Remove family member, which one?");
            String familyName = sc.nextLine();
            bt.removePerson(familyName);
        }
    }

    private static void mostrarInforme(Students studentsList, Scanner sc) {
        sc.nextLine();

        System.out.println("Enter the city where the student was born:");
        String birthCity = sc.nextLine();
        System.out.println("Enter the city of origin of the family:");
        String familyOriginCity = sc.nextLine();

        int totalStudents = 0;
        int studentsFromBirthCity = 0;
        int studentsFromFamilyOriginCity = 0;
        int studentsWithOneParent = 0;
        int studentsWithUnmarriedParents = 0;
        int studentsWithMoreThanTwoGrandparents = 0;

        ArrayList<String> students = studentsList.getAllStudentsName();
        for (String student : students) {
            BinaryTree bt = studentsList.getStudent(student);
            totalStudents++;
            if (bt.isFrom(birthCity)) {
                studentsFromBirthCity++;
            }
            if (bt.isDescentFrom(familyOriginCity)) {
                studentsFromFamilyOriginCity++;
            }
            if (bt.howManyParents() == 1) {
                studentsWithOneParent++;
            }
            if (!bt.marriedParents()) {
                studentsWithUnmarriedParents++;
            }
            if (bt.howManyGrandParents() > 2) {
                studentsWithMoreThanTwoGrandparents++;
            }
        }

        System.out.println("Total number of students: " + totalStudents);
        System.out.println("Number of students born in " + birthCity + ": " + studentsFromBirthCity);
        System.out.println("Number of students with family origin in " + familyOriginCity + ": " + studentsFromFamilyOriginCity);
        System.out.println("Number of students with one parent: " + (studentsWithOneParent + 1));
        System.out.println("Number of students with unmarried parents: " + studentsWithUnmarriedParents);
        System.out.println("Number of students with more than two grandparents: " + studentsWithMoreThanTwoGrandparents);
    }

}
