public class Person {

    public static final int WIDOWED = 1;
    public static final int DIVORCED = 2;
    public static final int MARRIED = 3;
    public static final int SINGLE = 4;

    private int maritalStatus;
    private String placeOfOrigin;
    private String name;

    public Person(int maritalStatus, String placeOfOrigin, String name) {
        if (maritalStatus < Person.WIDOWED || maritalStatus > Person.SINGLE) {
            throw new IllegalArgumentException("Marital status not good");
        }
        this.maritalStatus = maritalStatus;
        this.placeOfOrigin = placeOfOrigin;
        this.name = name;
    }

    public Person(String desc) {
        String[] part = desc.split(" ");
        if (part.length != 9 && part.length != 10 && part.length != 0) {
            throw new IllegalArgumentException("Description format is incorrect: " + desc);
        }
        if(part.length != 0) {
            this.name = part[1].substring(0, part[1].length() - 1); // Remove trailing comma
            this.placeOfOrigin = part[5].substring(0, part[5].length() - 1); // Remove trailing comma
            String marry = part[8]; // Remove trailing semicolon
            switch (marry) {
                case "Single":
                    this.maritalStatus = Person.SINGLE;
                    break;
                case "Married":
                    this.maritalStatus = Person.MARRIED;
                    break;
                case "Widowed":
                    this.maritalStatus = Person.WIDOWED;
                    break;
                case "Divorced":
                    this.maritalStatus = Person.DIVORCED;
                    break;
                default:
                    throw new IllegalArgumentException("Marital status not available");
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getPlaceOfOrigin() {
        return this.placeOfOrigin;
    }

    public int getMaritalStatus() {
        return this.maritalStatus;
    }

    private String getMaritalStatusString() {
        switch (this.maritalStatus) {
            case Person.WIDOWED:
                return "Widowed";
            case Person.MARRIED:
                return "Married";
            case Person.SINGLE:
                return "Single";
            case Person.DIVORCED:
                return "Divorced";
            default:
                return null;
        }
    }

    //Preferim   ficarho aqui que directament a write aixi es veu menys spaghuetti code,
    //també creiem que encaixa prou bé sense modificar l'estructura general del còdig

    @Override
    public String toString() {
        return "Name: " + this.name + ", place of origin: " + this.placeOfOrigin + ", marital status: " + getMaritalStatusString();
    }
}