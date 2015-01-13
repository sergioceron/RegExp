/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package regexp;

/**
 *
 * @author sergio
 */
public class Transition {

    private Node to;
    private Character letter;

    public Transition(Node to, Character letter) {
        this.to = to;
        this.letter = letter;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }
}
