package MainRepository;

import java.util.Scanner;

public class Poker {
    public static int numPlayers = 0; // 플레이어수 전역변수
    public static int selectedIndex = 0;

    static Player[] playerGenerating(int n) {
        Player[] players = new Player[n];
        for (int i = 0; i < n; ++i) {
            players[i] = new Player();
        }
        return players;
    }

    public static void main(String[] args) {
        while(true){
        // 시작화면(front)
        Screen.clearConsole();
        StartScreen.printScreen();

        // 시작화면(back)
            PokerDeck deck = new PokerDeck();
            deck.initialize(); // 초기 설정
            deck.tableSet();
    
            Player[] players = new Player[numPlayers];
            players = playerGenerating(numPlayers);
    
        // 로딩화면(front)
            LoadingScreen.printScreen();
            
        // 로딩화면(back)
            for (int i = 0; i < numPlayers; ++i) {
                players[i].drawing(); // 각 플레이별 7장
            }
          
        // 본게임화면
            // 게임 중
            MainGameScreen.printScreen(players);
    
            // 우승자 출력화면 
            Scanner scanner = new Scanner(System.in);
            char input;
            while (true) {
                // 우승자 출력
                EndingScreen.printScreen(players);

                // 재시작 / 종료 선택
                System.out.println("          R: Restart, E: Exit");
                System.out.print("          Select option >> ");
                input = scanner.next().charAt(0);
    
                if (input == 'E' || input == 'e') {
                    System.exit(0);
                } 
                
                else if (input == 'R' || input == 'r') {
                    break;
                } 
                
                else {
                    Screen.clearConsole();
                }
            }
        }
        // scanner.close();
    }
}
