package MainRepository;

import java.util.List;
import java.util.Scanner;

public class Poker {    
    static void displayResult(int rank) {
        String rankString;
        switch (rank / 1_00_00_00) {
            case 10:
                rankString = "Straight Flush";
                break;
            case 9:
                rankString = "Four of a Kind";
                break;
            case 8:
                rankString = "Full House";
                break;
            case 7:
                rankString = "Flush";
                break;
            case 6:
                rankString = "Straight";
                break;
            case 5:
                rankString = "Three of a Kind";
                break;
            case 4:
                rankString = "Two Pair";
                break;
            case 3:
                rankString = "One Pair";
                break;
            default:
                rankString = "High Card";
                break;
        }

        System.out.println("Rank: " + rankString);
    }
    
    // 플레이어 다수 생성
    static Player[] playerGenerating(int n) {
        Player[] players = new Player[n];
        for (int i = 0; i < n; ++i) {
            players[i] = new Player();
        }
        return players;
    }

    public static void main(String[] args) {
        PokerDeck deck = new PokerDeck();
        deck.initialize(); // 초기 설정
        deck.tableSet();

        Scanner scanner = new Scanner(System.in);
        System.out.println("플레이어 수를 입력해주세요. ");
        int n = scanner.nextInt();

        Player[] players = new Player[n];
        players = playerGenerating(n);

        for (int i = 0; i < n; ++i) {
            players[i].drawing();
            players[i].getHands();
            int rank = Rank.bestRank(players[i].hands);
            displayResult(rank);
        }

        List<Integer> winnersIndex = Rank.determineWinner(players);

        // 우승자 출력
        if (winnersIndex.size() == 1) {
            // 우승자 처리 로직
            System.out.println("우승자는 플레이어 " + (winnersIndex.get(0) + 1) + "입니다!");
        } else {
            // 동점자 처리 로직
            System.out.print("동점자(Split): ");
                for (int i = 0; i < winnersIndex.size(); i++) {
                    System.out.print("플레이어 " + (winnersIndex.get(i) + 1) + ", ");
                }
                System.out.println("입니다!");
            
        }

        scanner.close();
    }
}