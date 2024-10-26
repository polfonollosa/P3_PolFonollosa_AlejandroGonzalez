import java.util.ArrayList;

public class Students {
    class Node{
        protected Node next;
        protected BinaryTree info;

        public Node(BinaryTree info){
            this.info = info;
            this.next = null;
        }
    }

    private Node first;

    public Students(){
        first = null;
    }

    public void addStudent(BinaryTree nouEstudiant){
        if (first == null) {
            first = new Node(nouEstudiant);
        } else {
            Node aux = first;
            while(aux.next != null){
                aux = aux.next;
            }
            aux.next = new Node(nouEstudiant);
        }
    }

    public void removeStudent(String name){
        if(first != null){
            if((first.info).getName().equals(name)){
                first = first.next;
            } else {
                Node aux = first;
                while(aux.next != null){
                    if(aux.next.info.getName().equals(name)){
                        aux.next = aux.next.next;
                        return;
                    }
                    aux = aux.next;
                }
            }
        }
    }

    public BinaryTree getStudent(String name){
        if (first == null){
            return null;
        }
        else {
            Node aux = first;
            while(aux != null){
                if (aux.info.getName().equals(name)){
                    return aux.info;
                }
                aux = aux.next;
            }
            return null;
        }
    }

    public ArrayList<String> getAllStudentsName(){
        ArrayList<String> llista = new ArrayList<>();
        Node aux = first;
        while(aux != null){
            llista.add(aux.info.getName());
            aux = aux.next;
        }
        return llista;
    }
}
