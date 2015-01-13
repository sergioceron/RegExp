package regexp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sergio
 */
public class RegExp {
    final static char LAMBDA = '¬';
    static int NUMERATION = 0;
    private String regexp;
    
    public RegExp(String regexp){
        this.regexp = regexp;
    }
    
    private Node N(Character letter){
        Node X = new Node(0);
        Node Y = new Node(1);
        Y.setAcceptance(true);
        X.addTransition(new Transition(Y, letter));
        return X;
    }
    
    private Node Union(Node A, Node B){
        Node newRoot = new Node(0);
        newRoot.addTransition(new Transition(A, LAMBDA));
        newRoot.addTransition(new Transition(B, LAMBDA));
        return newRoot;
    }
    
    private Node Concatenacion(Node A, Node B){
        ConnectNodes(A, B.getTransitions());
        return A;
    }
    
    private Node Estrella(Node A){
        ConnectNodes(A, A.getTransitions());
        A.setAcceptance(true);
        return A;
    }
    
    private void ConnectNodes(Node root, List<Transition> Ts){
        for( Transition t : root.getTransitions() ){
            ConnectNodes(t.getTo(), Ts);
        }
        if( root.isAcceptance() ){
            for( Transition t : Ts ){
                if( !root.hasTransition(t) ){
                    root.addTransition(t);
                } else {
                    return;
                }
            }
        }
    }
    
    private Node Parse(String regexp){
        Character a = regexp.charAt(0);
        
        Node A = null, B = null;
        int next = 1;
        if( Character.isLetter(a) ){
            A = N(a);
            if( regexp.length() == 1 ) return A;
        } else if( a == '(' ){
            next = regexp.indexOf(')', next);
            A = Parse(regexp.substring(1, next));
            next++;
        }
        if( next >= regexp.length() ) return A;
        Character b = regexp.charAt(next);
        if( Character.isLetter(b) || b == '(' ){
            B = Parse(regexp.substring(next));
            return Concatenacion(A, B);
        } else if( b == '|' ){
            B = Parse(regexp.substring(next+1));
            return Union(A, B);
        } else if( b == '*' ){
            return Estrella(A);
        }
        return null;
    }

    /*private void test() {
        for(Node node : tabla.values())
        {
            for( Transition t : node.getTransitions() ){
                System.out.printf("(%d) --%c--> (%d)\n", node.getNumber(), t.getLetter(), t.getTo().getNumber());
            }
        }
    }*/
    
    public Map<Integer, Node> getTransitionsTable(){
        Map<Integer, Node> tabla = new HashMap<Integer, Node>();
        Node afnd = Parse(regexp);
        scanNode(afnd, tabla);
        return tabla;
    }
    
    public Map<Character, Integer> getAlfabeto(){
        Map<Character, Integer> alf = new HashMap<Character, Integer>();
        int j = 0;
        for( int i = 0; i < regexp.length(); i++ )
            if( Character.isLetter(regexp.charAt(i)) )
                if( !alf.containsKey(regexp.charAt(i)) )
                    alf.put(regexp.charAt(i), j++);
        alf.put(LAMBDA, j);
        return alf;
    }
    
    private void scanNode(Node afnd, Map<Integer, Node> tabla){
        if(!tabla.containsKey(afnd.getNumber())){
            tabla.put(afnd.getNumber(), afnd);
            for( Transition t : afnd.getTransitions() )
                scanNode(t.getTo(), tabla);
        }
    }
}
