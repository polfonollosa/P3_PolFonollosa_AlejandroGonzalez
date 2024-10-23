public class BinaryTree {
    class NodeA{
        Object inf;
        NodeA esq, dre;

        NodeA(){
            this(null);
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
            return addNode(unaPersona, level);
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
        NodeA actual;
        NodeA nou = new NodeA(unaPersona);
        char[] path = level.toCharArray();
        if(path.length == 1){
            if(path[0] == 'L'){
                actual =
            }
            else if(path[0] == 'R'){

            }
        }
        if(path[0] == 'L'){
            actual =
        }
        else if(path[0] == 'R'){

        }
    }

}
