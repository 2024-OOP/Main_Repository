package MainRepository;

import java.util.Collections;
import java.util.LinkedList;


class PokerDeck extends Deck {
    public final String[] SUIT = { "♠", "♣", "◈", "♥" };
    static private LinkedList<Card> table = new LinkedList<>(); // 포커 테이블

    PokerDeck() {
        initialize();
    }
    
    @Override
    void initialize() {
        super.getDeck().clear();
        table.clear();
        deckGenerate();
        Collections.shuffle(super.getDeck());
    }

    @Override
    void deckGenerate() {
        for (int i = 0; i < SUIT.length; ++i) {
            for (int j = 0; j < 13; ++j) {
                Card card = new Card();
                card.suit = SUIT[i];
                card.number = j + 1;
                super.getDeck().add(card);
            }
        }
    }

    void tableSet() {
        for (int i = 0; i < 5; ++i) {
            table.add(draw());
        }
    }

    // 외부에서 table로의 접근 제공
    static LinkedList<Card> getTable() {
        return table;
    }

}