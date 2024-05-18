package MainRepository;

import java.util.List;

class EndingScreen extends Screen{
    // 랭크 입력하면 문자열 반환해주는 함수
    static String rankOfCards(int rank) {
        String rankString;
        switch (rank / 1_00_00_00_00) {
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
        return rankString;
    }

    // 플레이어 별 rank를 담은 배열 만드는 함수(n은 플레이어 수)
    static String[] makeRankArr(Player[] players,int n){
        String[] playersRank = new String[n];
        
        for (int i = 0; i < playersRank.length; ++i) {
            int rank = Rank.bestRank(players[i].hands);
            playersRank[i] = rankOfCards(rank);
        }
        return playersRank;
    }

    public static void printScreen(Player[] players) {
        List<Integer> winnersIndex = Rank.determineWinner(players);
        String[] playersRank = makeRankArr(players, Poker.numPlayers);

        // 우승자 출력
        if (winnersIndex.size() == 1) {
            // 우승자 처리 로직
            System.out.println("\n                                   WINNER IS PLAYER " + (winnersIndex.get(0) + 1) + "!(" + playersRank[winnersIndex.get(0)] + ")");
            MainGameScreen.printCardFrame(players[winnersIndex.get(0)], winnersIndex.get(0), 5);
        } 
        
        else {
            // 동점자 처리 로직
            System.out.print("\n                                   TIE WINNERS ARE: ");
                for (int i = 0; i < winnersIndex.size(); i++) {
                    System.out.print("PLAYER " + (winnersIndex.get(i) + 1) + "(" + playersRank[winnersIndex.get(0)] + ")" + ", ");
                }
                System.out.println("!\n");

                for (int i = 0; i < winnersIndex.size(); i++) {
                    MainGameScreen.printCardFrame(players[winnersIndex.get(i)], winnersIndex.get(i), 5);
                }
            
        }
    }
}