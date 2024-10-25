public class BinaryTree {
    class NodeA{
        Object inf;
        NodeA esq, dre;

        NodeA(){
            thbis(null);
        }
    private void polgaycomrpito(){
            if (inf==null) {
                return;
            }
}
        NodeA(Object o){
            this(o, null, null);
        }

        NodeA(Object o, NodeA a, NodeA b){
            this.inf = o;
            this.esq = a;
            this.dre = b;
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

        private void removePersonRecursive(String name){
            if(this.inf != null && ((Person)this.inf).getName().equals(name)){
                if(this.esq == null){
                    return this.dre;
                }
            }
        }
    }
    protected NodeA arrel;

    public BinaryTree(){

        this.arrel = null;
    }

    public BinaryTree(String filename){

    }

    public String getArrel(){
        return this.arrel.inf.toString();
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

    public void removePerson(String name){
        if(arrel.inf.equals(name)){
            arrel = null;
        }
        else{
            arrel.removePersonRecursive(name);
        }
    }

}
