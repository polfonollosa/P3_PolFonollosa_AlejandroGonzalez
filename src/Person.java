public class Person {

    public static final int WIDOWED = 1;
    public static final int DIVORCED = 2;
    public static final int MARRIED = 3;
    public static final int SINGLE = 4;

    private int maritalStatus;
    private String PlaceOfOrigin;
    private String name;

    public Person(int maritalStatus, String PlaceOfOrigin, String name){
        if(maritalStatus < Person.WIDOWED || maritalStatus > Person.SINGLE){
            throw new IllegalArgumentException("Marital status not good");
        }
        this.PlaceOfOrigin = PlaceOfOrigin;
        this.name = name;
    }

    public Person(String desc){
        String[]part = desc.split(",");
        this.name = part[0].split(":")[1];
        this.PlaceOfOrigin = part[1].split(":")[1];
        String marry = part[2].split(":")[1];
        switch (marry){
            case "WIDOWED":
                this.maritalStatus = Person.WIDOWED;
                break;
            case "MARRIED":
                this.maritalStatus = Person.MARRIED;
                break;
            case "SINGLE":
                this.maritalStatus = Person.SINGLE;
                break;
            case "DIVORCED":
                this.maritalStatus = Person.DIVORCED;
                break;
            default:
                throw new IllegalArgumentException("marital status not avaiable");
        }
    }
    public String getName(){
        return this.name;
    }
    public String getPlaceOfOrigin(){
        return this.PlaceOfOrigin;
    }
    public int getMaritalStatus(){
        return this.maritalStatus;
    }
    public String getMaritalStatusString(){
        switch (this.maritalStatus){
            case Person.WIDOWED:
                return "WIDOWED";
            case Person.MARRIED:
                return "MARRIED";
            case Person.SINGLE:
                return "SINGLE";
            case Person.DIVORCED:
                return "DIVORCED";
            default:
                return null;
        }
    }
    public String toString(){
        return "Name: " + this.name + ", Origin: " + this.PlaceOfOrigin + ", Matrial Status: " + getMaritalStatusString();
    }
}
