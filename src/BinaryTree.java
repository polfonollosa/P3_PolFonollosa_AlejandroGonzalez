import java.io.*;

public class BinaryTree {
    protected class NodeA{
        protected Person inf;
        protected NodeA esq, dre;

        protected NodeA(){
            this(null, null, null);
        }

        protected NodeA(Person o){
            this(o, null, null);
        }

        protected NodeA(Person o, NodeA a, NodeA b){
            this.inf = o;
            this.esq = a;
            this.dre = b;
        }

        private void preOrderSaveRecursive(BufferedWriter buf){
            try{
                if(this.inf != null){
                    buf.write(this.inf.toString());
                    buf.newLine();
                }
                else{
                    buf.write("-");
                    buf.newLine();
                }
                if(this.esq != null){
                    this.esq.preOrderSaveRecursive(buf);
                }
                if(this.dre != null){
                    this.dre.preOrderSaveRecursive(buf);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private boolean addNodeRecursive(Person unaPersona, String level){
            if(level.isEmpty()){
                return false;
            }
            char direction = level.charAt(0);
            String remainingSerie = level.substring(1);
            if (direction == 'L'){
                if(remainingSerie.isEmpty()){
                    if(this.esq == null){
                        this.esq = new NodeA(unaPersona);
                    }
                }
                else{
                    if(this.esq == null){
                        this.esq = new NodeA();
                    }
                }
                return this.esq.addNodeRecursive(unaPersona, remainingSerie);
            }
            else if(direction == 'R') {
                if(remainingSerie.isEmpty()){
                    if(this.dre == null){
                        this.dre = new NodeA(unaPersona);
                    }
                }
                else{
                    if(this.dre == null){
                        this.dre = new NodeA();
                    }
                }
                return this.dre.addNodeRecursive(unaPersona, remainingSerie);
            }
            return false;
        }

        private void displayTreeRecursive(int level) {
            if (this.esq != null) {
                this.esq.displayTreeRecursive(level + 1);
            }
            System.out.println("\t".repeat(level) + (this.inf != null ? ((Person) this.inf).toString() : "null")); //condicional ternari
            if (this.dre != null) {
                this.dre.displayTreeRecursive(level + 1);
            }
        }

        private void removePersonRecursive(String name) {
            if (this.esq != null && (this.esq.inf).getName().equals(name)) {
                this.esq = (this.esq.esq != null) ? this.esq.esq : this.esq.dre;
            } else if (this.dre != null && (this.dre.inf).getName().equals(name)) {
                this.dre = (this.dre.dre != null) ? this.dre.dre : this.dre.esq;
            } else {
                if (this.esq != null) {
                    this.esq.removePersonRecursive(name);
                }
                if (this.dre != null) {
                    this.dre.removePersonRecursive(name);
                }
            }
        }

        private boolean isDescentFromRecursive(String place) {
            if (inf != null && this.inf.getPlaceOfOrigin().equals(place)) {
                return true;
            }
            boolean leftResult = (this.esq != null) && this.esq.isDescentFromRecursive(place);
            boolean rightResult = (this.dre != null) && this.dre.isDescentFromRecursive(place);
            return leftResult || rightResult;
        }

        private int countNodesRecursive() {
            int count = 0;
            if (this.inf != null) {
                if (this.esq != null) {
                    if (this.esq.esq != null || this.esq.dre != null) {
                        count++;
                    }
                    count += this.esq.countNodesRecursive();
                }
                if (this.dre != null) {
                    if (this.dre.esq != null || this.dre.dre != null) {
                        count++;
                    }
                    count += this.dre.countNodesRecursive();
                }
            }
            return count;
        }
    }
    protected NodeA arrel;

    public BinaryTree(){

        this.arrel = null;
    }

    public BinaryTree(String filename){
       try{
           BufferedReader buf = new BufferedReader(new FileReader("Files/"+ filename +".txt"));
           this.arrel = preOrderLoad(buf);
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       }
    }

    public String getArrel(){
        return this.arrel.inf.toString();
    }

  private NodeA preOrderLoad(BufferedReader buf) {
    try {
        String line = buf.readLine();
        if (line == null || line.equals("-")) {
            return null;
        }
        Person person = new Person(line);
        NodeA tornar = new NodeA(person);
        tornar.esq = preOrderLoad(buf);
        tornar.dre = preOrderLoad(buf);
        return tornar;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
  }

    public boolean addNode(Person unaPersona, String level){
        if(arrel == null){
            arrel = new NodeA(unaPersona);
            return true;
        }
        return arrel.addNodeRecursive(unaPersona, level);
    }

    public void displayTree(){
        if (arrel != null) {
            arrel.displayTreeRecursive(0);
        } else {
            System.out.println("The tree is empty.");
        }
    }

    public void preOrderSave(){
        try{
            String name = arrel.inf.getName();
            BufferedWriter buf = new BufferedWriter(new FileWriter("Files/"+ name +".txt"));
            arrel.preOrderSaveRecursive(buf);
            buf.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePerson(String name) {
        if (arrel != null) {
            if (arrel.inf != null && ( arrel.inf).getName().equals(name)) {
                arrel = (arrel.esq != null) ? arrel.esq : arrel.dre;
            } else {
                arrel.removePersonRecursive(name);
            }
        }
    }

    public boolean isFrom(String place){
        if(arrel.inf.getPlaceOfOrigin().equals(place)){
            return true;
        }
        return false;
    }

    public boolean isDescentFrom(String place) {
        if (arrel != null) {
            return arrel.isDescentFromRecursive(place);
        }
        return false;
    }

    public int howManyParents(){
        int cont = 0;
        if(arrel == null){
            return 0;
        }
        if(this.arrel.esq != null){
            cont++;
        }
        if(this.arrel.dre != null){
            cont++;
        }
        return cont;
    }

    public int howManyGrandParents(){
        if(this.arrel != null){
            return arrel.countNodesRecursive();
        }
        return 0;
    }

    public boolean marriedParents() {
        if (this.arrel != null) {
            if (this.arrel.esq != null && this.arrel.dre != null) {
                Person esquerre = this.arrel.esq.inf;
                Person dreat = this.arrel.dre.inf;
                if (esquerre != null && dreat != null &&
                        esquerre.getMaritalStatusString().equals("MARRIED") &&
                        dreat.getMaritalStatusString().equals("MARRIED")) {
                    return true;
                }
            }
        }
        return false;
    }

    }
