package MainRepository;

import java.util.Scanner;

public class MainGameScreen extends Screen {
    private static final String[] OPTIONS = makeOPTIONS(Poker.numPlayers);

    // 카드 숫자를 출력 환경, 조건에 맞게 컨버트
    private static String convertNumber(int number, boolean front) {
        String result;
        if (number == 10 && !front) {
            result = "\b10";
        } else {
            switch (number) {
                case 1:
                case 14:
                    result = "A";
                    break;
                case 10:
                    result = "10";
                    break;
                case 11:
                    result = "J";
                    break;
                case 12:
                    result = "Q";
                    break;
                case 13:
                    result = "K";
                    break;
                default:
                    result = String.valueOf(number);
                    break;
            }
            
            if (front && number != 10) {
                result += " ";
            }
        }
        return result;
    }

    // 입력한 플레이어 수만큼 옵션 배열 생성해 주는 함수
    private static String[] makeOPTIONS(int n){
        String[] optionsArr = new String[n];
        for(int i = 0; i < n; ++i){
            optionsArr[i] = "PLAYER " + (i+1);
        }
        return optionsArr;
    }

    // Table 카드 회차 별 출력
    public static void printCardFrame(Player selectedPlayerHands, int selectedPlayer, int turn){
        // 0 ~ 4 인덱스에는 Table 카드
        // 5 ~ 6 인덱스에는 My Hand 카드
        // Table 카드 출력은 turn으로 개수 조절, turn은 5회차까지 
        // My hand 카드는 직접 인덱스 5, 6을 넣어줌
    
    // Table
        System.out.printf("\n"); 
        System.out.println("                                             >> Table <<\n"); 

        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < turn; i++) {
            System.out.printf("'-----------' "); // 카드 윗 부분 출력
        }
        System.out.println(); // 줄 바꿈

        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < turn; i++) {
            System.out.printf("|%s         | ", convertNumber(selectedPlayerHands.hands.get(i).number, true));// 카드 숫자 출력
        }
        System.out.println(); // 줄 바꿈

        for(int j = 0; j <3; j++){
            System.out.printf("                "); // 앞 공백 중앙 정렬
            for (int i = 0; i < turn; i++) {
                System.out.printf("|           | "); 
            }
            System.out.println(); // 줄 바꿈
        }

        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < turn; i++) {
            System.out.printf("|     %s     | ", selectedPlayerHands.hands.get(i).suit);// 카드 문양 출력
        }
        System.out.println(); // 줄 바꿈
        
        for(int j = 0; j <3; j++){
            System.out.printf("                "); // 앞 공백 중앙 정렬
            for (int i = 0; i < turn; i++) {
                System.out.printf("|           | "); 
            }
            System.out.println(); // 줄 바꿈
        }

        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < turn; i++) {
                System.out.printf("|          %s| ", convertNumber(selectedPlayerHands.hands.get(i).number, false));// 카드 숫자 출력
        }
        System.out.println(); // 줄 바꿈

        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < turn; i++) {
            System.out.printf("'-----------' "); // 카드 아래 부분 출력
        }
        System.out.println(); // 줄 바꿈

    // My Hand
        System.out.printf("\n\n"); 
        System.out.println("                                            >> PLAYER " + (selectedPlayer+1) + " <<\n");

        System.out.printf("                                    '-----------'    '-----------'\n");
        System.out.printf("                                    |%s         |    |%s         |\n", convertNumber(selectedPlayerHands.hands.get(5).number, true), convertNumber(selectedPlayerHands.hands.get(6).number, true));
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |     %s     |    |     %s     |\n", selectedPlayerHands.hands.get(5).suit, selectedPlayerHands.hands.get(6).suit);
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |           |    |           |\n");
        System.out.printf("                                    |          %s|    |          %s|\n",convertNumber(selectedPlayerHands.hands.get(5).number, false), convertNumber(selectedPlayerHands.hands.get(6).number, false));
        System.out.printf("                                    '-----------'    '-----------'\n\n");
    }

    public static void printMenu() {
        for (int i = 0; i < OPTIONS.length; i++) {
            if (i == selectedIndex) {
                System.out.print("                                             -> ");
            } else {
                System.out.print("                                                ");
            }
            System.out.println(OPTIONS[i]);
        }
        System.out.println("\n                           W: Move Up, S: Move Down, N: Next Turn, E: Select");
        System.out.print("                                        Select option >>  ");
    }

    private static void moveSelectionUp() {
        selectedIndex = (selectedIndex - 1 + OPTIONS.length) % OPTIONS.length;
    }

    private static void moveSelectionDown() {
        selectedIndex = (selectedIndex + 1) % OPTIONS.length;
    }

    public static void printScreen(Player[] players){
        int turn = 3;
        int selectedPlayer = 0;
        Player selectedPlayerHands = players[0];
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            printCardFrame(selectedPlayerHands, selectedPlayer, turn);
            printMenu(); // 메뉴 출력

            if (scanner.hasNext()) {
                char input = scanner.next().charAt(0);
                if (input == 'W' || input == 'w') {
                    moveSelectionUp();
                    clearConsole();
                }

                else if (input == 'S' || input == 's') {
                    moveSelectionDown();
                    clearConsole();
                }

                else if (input == 'N' || input == 'n') {
                    ++turn;
                    if(turn == 6) {
                        clearConsole();
                        break;
                    }
                }

                else if (input == 'E' ||input == 'e') {
                    for(int i = 0; i < Poker.numPlayers; ++i){
                        if (selectedIndex == i) {
                            selectedPlayer = i;
                            selectedPlayerHands = players[selectedPlayer];
                        }
                    }  
                }
            }
        }
    }
}

