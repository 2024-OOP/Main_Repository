import java.util.LinkedList;
import java.util.Collections;

class Card {
    public String suit;
    public int number;
}

class Deck {
    // public static Card deck[] = new Card[52];
    public static LinkedList<Card> deck = new LinkedList<>();
    public final String[] SUIT = { "스페이드", "클로버", "다이아몬드", "하트" };

    Deck() {
        for (int i = 0; i < SUIT.length; ++i) { // 카드 덱 생성
            for (int j = 0; j < 13; ++j) {
                Card card = new Card();
                card.suit = SUIT[i];
                card.number = j + 1;
                deck.add(card);
                /*
                 * deck[i * 13 + j] = new Card();
                 * deck[i * 13 + j].suit = SUIT[i];
                 * deck[i * 13 + j].number = j + 1;
                 */
            }
        }
    }

    void shuffle() { // 덱 셔플
        Collections.shuffle(deck);
    }

    static Card draw() {
        Card card = deck.getLast();
        deck.removeLast();

        return card;
    }
}

class Player {
    LinkedList<Card> hands = new LinkedList<>(); // 보유한 패

    void clear() { // 패 초기화
        hands.clear();
    }

    void drawing() {
        for (int i = 0; i < 7; ++i) {
            draw();
        }
    }

    void draw() {
        hands.add(Deck.draw());
    }

    void getHands() {
        for (int i = 0; i < hands.size(); ++i) {
            System.out.println(hands.get(i).suit + " " + hands.get(i).number);
        }
    }
}

public class Poker {
    public static int cardRank(LinkedList<Card> cards) {
        /*
         * 다섯 장을 판별하는 방법:
         * 1번째 카드: 2~5번째 카드와 비교
         * 2번째 카드: 3~5번째 카드와 비교
         * 3번째 카드: 4~5번째 카드와 비교
         * 4번째 카드: 5번째 카드와 비교
         * 
         * i == 0, j == 1~4
         * i == 1, j == 2~4
         * i == 2, j == 3~4
         * i == 3, j == 4
         */
        int pairCount = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = i + 1; j < 5; ++j) {
                if (cards.get(i).number == cards.get(j).number) {
                    pairCount++;
                    /*
                     * 원페어: 1
                     * 투페어: 2
                     * 트리플: 3
                     * 풀하우스: 4 ∵ 3 + 1
                     * 포카드: 6 ∵ 2s, 2h, 2d, 2c, 3c일 때 똑같은 숫자의 쌍: 2s-2h, 2s-2d, 2s-2c, 2h-2d, 2h-2c, 2d-2c
                     */
                }
            }
        }

        // straight 여부 판정
        LinkedList<Integer> sortedNum = new LinkedList<>(); // sortedNum: 숫자별 정렬
        for (int i = 0; i < 5; ++i) {
            sortedNum.add(cards.get(i).number); // sortedNum에 값 복사: cards의 number값
        }
        Collections.sort(sortedNum);

        boolean straight = false;
        if (pairCount == 0) {
            // [1, 2, 3, 4, 5], [2, 3, 4, 5, 6], …, [9, 10, 11, 12, 13]: 가장 큰 수와 가장 작은 수의 차가 4
            if (sortedNum.get(4) - sortedNum.get(0) == 4) {
                straight = true;
            }
            if (sortedNum.get(0) == 1 && sortedNum.get(1) == 10) { // [A, 10, J, Q, K]
                straight = true;
            } // 1 10 11 12 13
        }

        // flush 여부 판정
        LinkedList<String> sortedSuit = new LinkedList<>(); // suit: 문양별 정렬
        for (int i = 0; i < 5; ++i) {
            sortedSuit.add(cards.get(i).suit); // sortedSuit에 값 복사: cards의 suit값
        }
        Collections.sort(sortedSuit);

        // 문양별로 정렬한 후 suit[0]과 suit[4]가 같을 경우, index 0~4까지 같은 문양
        boolean flush = false;
        if (sortedSuit.get(0).equals(sortedSuit.get(4))) {
            flush = true;
        }

        // rank: 숫자가 작을 수록 높은 순위
        int rank;
        if (straight && flush) {
            rank = 1;
        } else if (pairCount == 6) {
            rank = 2;
        } else if (pairCount == 4) {
            rank = 3;
        } else if (flush) {
            rank = 4;
        } else if (straight) {
            rank = 5;
        } else if (pairCount == 3) {
            rank = 6;
        } else if (pairCount == 2) {
            rank = 7;
        } else if (pairCount == 1) {
            rank = 8;
        } else {
            rank = 9;
        }

        return rank;
    }

    public static int bestRank(LinkedList<Card> cards) {
        int bestRank = 9; // 가장 낮은 랭크로 초기화
        /*
         * 7장의 카드 중 5장을 선택하는 모든 조합을 확인
         * i = 0, j = 1 ~ 6까지 검사
         * i = 1, j = 2 ~ 6까지 검사
         * ...
         * i = 5, j = 6까지 검사
         */ 

        for (int i = 0; i < cards.size(); ++i) {
            for (int j = i + 1; j < cards.size(); ++j) {
                LinkedList<Card> temp = new LinkedList<>(cards); // temp에 cards의 값 복사
                
                // i, j의 카드 제외: 뒤의 index부터 제거해야 오류 X
                temp.remove(j);
                temp.remove(i);

                int rank = cardRank(temp); // 선별된 5장의 카드 랭크 계산
                if (rank < bestRank) {
                    bestRank = rank; // 더 좋은 랭크가 나오면 bestRank에 업데이트
                }
            }
        }
        return bestRank;
    }

    static void displayResult(int rank) {
        String rankString;
        switch (rank) {
            case 1:
                rankString = "Straight Flush";
                break;
            case 2:
                rankString = "Four of a Kind";
                break;
            case 3:
                rankString = "Full House";
                break;
            case 4:
                rankString = "Flush";
                break;
            case 5:
                rankString = "Straight";
                break;
            case 6:
                rankString = "Three of a Kind";
                break;
            case 7:
                rankString = "Two Pair";
                break;
            case 8:
                rankString = "One Pair";
                break;
            default:
                rankString = "High Card";
                break;
        }

        System.out.println("Rank: " + rankString);
    }

    static Player[] playerGenerating(int n) {
        Player[] players = new Player[n];
        for (int i = 0; i < n; ++i) {
            players[i] = new Player();
        }
        return players;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle(); // 초기 설정

        int n = 3;
        Player[] players = new Player[n];
        players = playerGenerating(n);

        for (int i = 0; i < n; ++i) {
            players[i].drawing();
            players[i].getHands();
            int rank = bestRank(players[i].hands);
            displayResult(rank);
        }

        /*
        Player player1 = new Player();
        player1.drawing();
        player1.getHands();

        int rank = bestRank(player1.hands);
        displayResult(rank);
         */

    }
}
