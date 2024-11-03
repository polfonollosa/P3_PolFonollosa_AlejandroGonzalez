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
                if (this.inf != null) {
                    buf.write(this.inf.toString());
                    if (this.esq == null && this.dre == null) {
                        buf.write("; ;");
                    } else if (this.esq == null) {
                        buf.write(";");
                    } else if (this.dre == null) {
                        buf.write(";");
                    }
                    buf.newLine();
                } else {
                    buf.write(";");
                    buf.newLine();
                }
                if (this.esq != null) {
                    this.esq.preOrderSaveRecursive(buf);
                }
                if (this.dre != null) {
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

        public void displayTreeRecursive(int level) {
            // Imprime el nodo actual con la cantidad correcta de tabuladores para su nivel
            System.out.println("    ".repeat(level) + (this.inf != null ? this.inf.toString() : "null"));

            // Llamada recursiva para el subárbol izquierdo
            if (this.esq != null) {
                this.esq.displayTreeRecursive(level + 1);
            }

            // Llamada recursiva para el subárbol derecho
            if (this.dre != null) {
                this.dre.displayTreeRecursive(level + 1);
            }
        }


        private void removePersonRecursive(String name) {
            if (this.esq != null && (this.esq.inf).getName().equals(name)) {
                System.out.println("Removing " + name);
                this.esq = (this.esq.esq != null) ? this.esq.esq : this.esq.dre;
            } else if (this.dre != null && (this.dre.inf).getName().equals(name)) {
                System.out.println("Removing " + name);
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
            // Leer la siguiente línea del archivo
            String line = buf.readLine();
            if (line == null || line.equals("-")) {
                return null; // Nodo nulo si la línea es null o un guión
            }

            // Dividir la línea para extraer los datos de la persona
            String[] parts = line.split(";");
            String personInfo = parts[0];
            Person person = new Person(personInfo); // Crear el objeto Persona con los datos
            NodeA node = new NodeA(person); // Crear el nodo con la persona

            // Determinar el número de hijos basándonos en el final de la línea
            if (parts.length == 1) {
                // No hay símbolos al final -> Nodo tiene dos hijos
                node.esq = preOrderLoad(buf); // Llamada recursiva al hijo izquierdo
                node.dre = preOrderLoad(buf); // Llamada recursiva al hijo derecho
            } else if (parts.length == 2) {
                // Un solo punto y coma al final -> Nodo tiene un hijo izquierdo
                node.esq = preOrderLoad(buf); // Llamada recursiva solo al hijo izquierdo
                node.dre = null; // No hay hijo derecho
            } else if (parts.length == 3) {
                // Dos punto y coma al final -> Nodo no tiene hijos
                node.esq = null;
                node.dre = null;
            }

            return node; // Retornar el nodo creado
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
                        esquerre.getMaritalStatusString().equals("MARRIED") &&
                        dreat.getMaritalStatusString().equals("MARRIED")) {
                    return true;
                }
            }
        }
        return false;
    }

    }
