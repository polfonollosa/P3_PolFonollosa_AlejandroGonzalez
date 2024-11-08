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

        private void preOrderSaveRecursive(BufferedWriter buf) {
            try {
                String person = inf.toString();
                buf.write(person);

                // Caso de nodo hoja (sin hijos)
                if (this.esq == null && this.dre == null) {
                    buf.write("; ;");
                    buf.newLine();
                    return;
                }
                // Caso de nodo con solo hijo izquierdo
                else if (this.esq != null && this.dre == null) {
                    buf.write(";");
                    buf.newLine();
                    this.esq.preOrderSaveRecursive(buf);
                    return;
                }
                // Caso de nodo con solo hijo derecho
                else if (this.esq == null && this.dre != null) {
                    buf.write(";");
                    buf.newLine();
                    this.dre.preOrderSaveRecursive(buf);
                    return;
                }
                // Caso de nodo con ambos hijos
                else {
                    buf.write("");
                    buf.newLine();
                    this.esq.preOrderSaveRecursive(buf);
                    this.dre.preOrderSaveRecursive(buf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private boolean addNodeRecursive(Person unaPersona, String level) {
            if (level.isEmpty()) {
                return false;
            }
            char direction = level.charAt(0);
            String remainingSerie = level.substring(1);
            if (direction == 'L') {
                if (this.esq == null) {
                    this.esq = new NodeA();
                }
                if (remainingSerie.isEmpty()) {
                    this.esq.inf = unaPersona;
                    return true;
                }
                return this.esq.addNodeRecursive(unaPersona, remainingSerie);
            } else if (direction == 'R') {
                if (this.dre == null) {
                    this.dre = new NodeA();
                }
                if (remainingSerie.isEmpty()) {
                    this.dre.inf = unaPersona;
                    return true;
                }
                return this.dre.addNodeRecursive(unaPersona, remainingSerie);
            }
            return false;
        }

        private void displayTreeRecursive(int level) {
            for (int i = 0; i < level; i++) {
                System.out.print("      ");
            }
             System.out.println(inf.getName());
            if (this.esq != null) {
                esq.displayTreeRecursive(level + 1);
            }
            if (this.dre != null) {
                dre.displayTreeRecursive(level + 1);
            }
        }


        private void removePersonRecursive(String name) {
            if (this.esq != null && (this.esq.inf).getName().equals(name)) {
                System.out.println("Removing " + name);
                this.esq.inf = new Person(Person.SINGLE, esq.inf.getPlaceOfOrigin(), "*death");
            } else if (this.dre != null && (this.dre.inf).getName().equals(name)) {
                System.out.println("Removing " + name);
                this.dre.inf = new Person(Person.SINGLE, esq.inf.getPlaceOfOrigin(), "*death");
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
                    if (this.esq.esq != null && !this.esq.esq.inf.getName().equals("*death") || this.esq.dre != null) {
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
    private NodeA arrel;

    public BinaryTree(){

        this.arrel = null;
    }

    public BinaryTree(String filename){
       try{
           BufferedReader buf = new BufferedReader(new FileReader(filename));
           this.arrel = preOrderLoad(buf);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    public String getName(){
        return this.arrel.inf.getName();
    }

    private NodeA preOrderLoad(BufferedReader buf) {
        try {
            int cont = 0;
            String line = buf.readLine();
            if(line == null){
                return null;
            }
            String[] dc = line.split(";");
            Person p = new Person(dc[0]);
            NodeA n = new NodeA(p);
            for(int i = 0 ; i < line.length() ; i++){
                if(line.charAt(i) == ';'){
                    cont ++;
                }
            }
            if(cont == 2){
                n.esq = null;
                n.dre = null;
            }
            else if(cont == 1){
                n.esq = preOrderLoad(buf);
                n.dre = null;
            }
            else{
                n.esq = preOrderLoad(buf);
                n.dre = preOrderLoad(buf);
            }
            return n;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean addNode(Person unaPersona, String level) {
        if (arrel == null) {
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

    public void preOrderSave() {
        if (arrel == null || arrel.inf == null) {
            throw new IllegalStateException("The tree is empty.");
        }
        try {
            String name = arrel.inf.getName();
            BufferedWriter buf = new BufferedWriter(new FileWriter("Files/" + name + ".txt"));
            arrel.preOrderSaveRecursive(buf);
            buf.close();
        } catch (IOException e) {
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
                        esquerre.getMaritalStatus() == Person.MARRIED &&
                        dreat.getMaritalStatus() == Person.MARRIED) {
                    return true;
                }
            }
        }
        return false;
    }

    }


