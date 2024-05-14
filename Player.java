package MainRepository;

import java.util.LinkedList;

public class Player {
    LinkedList<Card> hands = new LinkedList<>();
    
    void clear() {
        hands.clear();
    }

    void drawing() {
        hands.addAll(PokerDeck.getTable());
        for (int i = 0; i < 2; ++i) {
            hands.add(PokerDeck.draw());
        }
    }
 
    void getHands() {
        for (int i = 0; i < hands.size(); ++i) {
            System.out.println(hands.get(i).suit + " " + hands.get(i).number);
        }
    }
}
