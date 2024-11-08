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


        while (!exit) {
            System.out.println("Menu Principal:");
            System.out.println("\t1. Mostrar llistat d'estudiants");
            System.out.println("\t2. Mostrar família d'un estudiant");
            System.out.println("\t3. Afegir un estudiant");
            System.out.println("\t4. Modificar un estudiant");
            System.out.println("\t5. Mostrar el informe");
            System.out.println("\t6. Guardar i Sortir");
            System.out.println("Tria una opció:");


            option = sc.nextInt();


            while (option < 1 || option > 6) {
                System.out.println("Introdueix una opció vàlida:");
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


        }
        System.out.println("Sortint...");
    }

    private static Students readAllStudents(String folderPath) {
        Students student = new Students();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    BinaryTree bt = new BinaryTree(folderPath + "/" + file.getName());
                    System.out.println("Alumne carregat des del fitxer: " + file.getName());
                    System.out.println("Arbre binari estructurat:");
                    bt.displayTree();
                    student.addStudent(bt);
                }
            }
        }
        return student;
    }

    private static void saveAllStudents(Students StudentsList) {
        ArrayList<String> students = StudentsList.getAllStudentsName();
        for (String student : students) {
            BinaryTree bt = StudentsList.getStudent(student);
            bt.preOrderSave();
            System.out.println("Alumne guardat al fitxer: " + student);
        }
    }

    private static void displayAllStudents(Students StudentsList) {
        ArrayList<String> students = StudentsList.getAllStudentsName();
        if (students.isEmpty()) {
            throw new NoSuchElementException("La llista d'estudiants està buida");
        }
        System.out.println("Mostrem els noms dels estudiants:");
        for (String student : students) {
            System.out.println("\t" + student);
        }
    }

    private static void showStudentFamily(Students studentList, Scanner sc) {
        System.out.println("Introdueix el nom de l'estudiant del qual vols veure la família:");
        sc.nextLine();
        String name = sc.nextLine();
        BinaryTree bt = studentList.getStudent(name);
        if (bt != null) {
            System.out.println("La família de " + name + " està representada per:");
            bt.displayTree();
        } else {
            System.out.println("Estudiant no trobat.");
        }
    }

    private static void addNewStudent(Students StudentList, Scanner sc) {
        System.out.println("Introdueix el nom del nou estudiant:");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Introdueix l'estat civil de l'estudiant:");
        String maritalStatus = sc.nextLine();
        System.out.println("Introdueix el lloc de naixement de l'estudiant:");
        String placeOfBirth = sc.nextLine();
        BinaryTree bt = new BinaryTree();
        String s = "Nom: " + name + ", lloc d'origen: " + placeOfBirth + ", estat civil: " + maritalStatus;
        try {
            bt.addNode(new Person(s), "");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en afegir l'estudiant.");
            return;
        }
        StudentList.addStudent(bt);
    }

    private static void modifyStudent(Students StudentList, Scanner sc) {
        System.out.println("Introdueix el nom de l'estudiant que vols modificar:");
        sc.nextLine();
        String name = sc.nextLine();
        BinaryTree bt = StudentList.getStudent(name);

        if (bt == null) {
            System.out.println("L'estudiant no existeix");
            return;
        }

        System.out.println("- OPCIONS:");
        System.out.println("1. Afegir un membre de la família");
        System.out.println("2. Eliminar un membre de la família");
        System.out.println();

        System.out.println("Introdueix una opció:");
        int option = sc.nextInt();
        sc.nextLine();

        while (option < 1 || option > 2) {
            System.out.println("Introdueix una opció vàlida:");
            option = sc.nextInt();
            sc.nextLine();
        }

        if (option == 1) {
            System.out.println("Afegir membre de la família");
            System.out.println("Introdueix el nom del membre de la família que vols afegir:");
            name = sc.nextLine();
            System.out.println("Introdueix l'estat civil del membre de la família:");
            String maritalStatus = sc.nextLine();
            System.out.println("Introdueix el lloc de naixement del membre de la família:");
            String placeOfBirth = sc.nextLine();
            String s = "Nom: " + name + ", lloc d'origen: " + placeOfBirth + ", estat civil: " + maritalStatus;
            System.out.println("Introdueix la ruta, L o R, ex: LRL , RRL , LRR , etc.");
            String ruta = sc.nextLine();
            if (bt.addNode(new Person(s), ruta)) {
                System.out.println("Membre de la família afegit correctament.");
            } else {
                System.out.println("Error en afegir el membre de la família.");
            }
        } else if (option == 2) {
            System.out.println("Eliminar membre de la família, quin?");
            String familyName = sc.nextLine();
            bt.removePerson(familyName);
        }
    }

    private static void mostrarInforme(Students studentsList, Scanner sc) {
        sc.nextLine();

        System.out.println("Indica la ciutat de naixement a buscar:");
        String birthCity = sc.nextLine();
        System.out.println("Indica la ciutat de procedència a buscar:");
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
            if (bt.howManyGrandParents() >= 2) {
                studentsWithMoreThanTwoGrandparents++;
            }
        }

        System.out.println("Nombre d'alumnes totals: " + totalStudents);
        System.out.println("Hi ha " + studentsFromBirthCity + " alumnes de " + birthCity);
        System.out.println("Hi ha " + studentsFromFamilyOriginCity + " alumnes descendents de " + familyOriginCity);
        System.out.println("Hi ha " + (studentsWithOneParent + 1) + " alumnes amb un únic progenitor.");
        System.out.println("Hi ha " + studentsWithUnmarriedParents + " alumnes amb progenitors no casats.");
        System.out.println("Hi ha " + (studentsWithMoreThanTwoGrandparents + 1) + " alumnes amb dos o més avis o àvies.");
    }
}