/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package regexp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sergio
 */
public class Node {

    private Integer number;
    private boolean acceptance;
    private List<Transition> transitions;

    public Node(Integer number) {
        this.number = number;
        this.transitions = new ArrayList<Transition>();
        this.acceptance = false;
    }

    public boolean isAcceptance() {
        return acceptance;
    }

    public void setAcceptance(boolean acceptance) {
        this.acceptance = acceptance;
    }

    public Integer getNumber() {
        return this.hashCode();
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public boolean hasTransition(Transition t) {
        for (Transition tr : getTransitions()) {
            if (tr.getLetter() == t.getLetter() && tr.getTo() == t.getTo()) {
                return true;
            }
        }
        return false;
    }
}
