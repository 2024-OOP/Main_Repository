package MainRepository;

import java.util.Scanner;

public class StartScreen extends Screen {
    private static final String[] OPTIONS = {"Start Game", "Exit"};

    private static void printCover() {
        System.out.println("\n");
        System.out.println("                ┏■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■┓ ");
        
        System.out.println("                ■                                                                                            ■");
        System.out.println("                ■        ■■■■■■■■■■■■    ■■■■■■■■■■■■    ■■        ■■    ■■■■■■■■■■■■    ■■■■■■■■■■          ■");
        System.out.println("                ■        ■■        ■■    ■■        ■■    ■■      ■■      ■■              ■■        ■■        ■");
        System.out.println("                ■        ■■■■■■■■■■■■    ■■        ■■    ■■■■■■■■■       ■■■■■■■■■■■■    ■■■■■■■■■■          ■");
        System.out.println("                ■        ■■              ■■        ■■    ■■      ■■      ■■              ■■      ■■          ■");
        System.out.println("                ■        ■■              ■■■■■■■■■■■■    ■■        ■■    ■■■■■■■■■■■■    ■■        ■■        ■");
        System.out.println("                ■                                                                                            ■");
        System.out.println("                ┗■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■┛\n");
    }

    public static void printMenu() {
        for (int i = 0; i < OPTIONS.length; i++) {
            if (i == selectedIndex) {
                System.out.print("                                                       -> ");
            } else {
                System.out.print("                                                          ");
            }
            System.out.println(OPTIONS[i]);
        }
        System.out.println("\n                                             W: Move Up, S: Move Down, E: Select");
        System.out.print("                                             Select option >>  "); 
    }

    private static void moveSelectionUp() {
        selectedIndex = (selectedIndex - 1 + OPTIONS.length) % OPTIONS.length;
    }

    private static void moveSelectionDown() {
        selectedIndex = (selectedIndex + 1) % OPTIONS.length;
    }

    public static void printScreen(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearConsole();
            printCover(); // 시작 화면 표지 출력
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

                else if (input == 'E' ||input == 'e') {
                    if (selectedIndex == 0) {
                        System.out.print("                                             Enter the number of players >>  ");
                        Poker.numPlayers = scanner.nextInt();
                        // Start Game을 선택한 경우, 다음 화면으로 넘어가는 작업 추가
                        break;
                    } 
                    
                    else if (selectedIndex == 1) {
                        // Exit를 선택한 경우, 프로그램 종료
                        System.exit(0);
                    }
                }
            }
        }
    }
}
