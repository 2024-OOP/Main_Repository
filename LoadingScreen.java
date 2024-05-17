package MainRepository;

public class LoadingScreen extends Screen {
   
    // System.out.printf("▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▒▒▒▒▒▒▒▒▒▒▒▓\n");
    // System.out.printf("▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
    
private static void printCardFrame(int frame) {

    System.out.printf("\n\n"); 
    System.out.println("                                            SUFFLILNG CARDS\n"); 
    
    System.out.printf("                "); // 앞 공백 중앙 정렬
    for (int i = 0; i < frame; i++) {
        System.out.printf("'-----------' "); // 카드 윗 부분 출력
    }
    System.out.println(); // 줄 바꿈

    for(int j = 0; j <9; j++){
        System.out.printf("                "); // 앞 공백 중앙 정렬
        for (int i = 0; i < frame; i++) {
            System.out.printf("|▒▒▒▒▒▒▒▒▒▒▒| ");
        }
        System.out.println(); // 줄 바꿈
    }

    System.out.printf("                "); // 앞 공백 중앙 정렬
    for (int i = 0; i < frame; i++) {
        System.out.printf("'-----------' "); // 카드 아래 부분 출력
    }
    System.out.println(); // 줄 바꿈

    System.out.printf("\n\n"); 
    System.out.println("                                   START WITH PLAYER 1. GET READY!\n"); 
}

public static void printScreen() {
    int frame = 0;
    boolean turnpoint = false;
    long startTime = System.currentTimeMillis(); // 시작 시간 기록
    long duration = 5000; // 5초

    try {
        while (System.currentTimeMillis() - startTime < duration) {
            // ANSI 이스케이프 시퀀스를 사용하여 콘솔을 지우기
            clearConsole();

            // 로딩 프레임 출력
            printCardFrame(frame);

            // 다음 프레임으로 이동
            if (frame == 0) turnpoint = false;
            else if (frame == 5) turnpoint = true;

            if (turnpoint == false) frame++;
            else frame--;

            // 잠시 멈춤
            Thread.sleep(250); // 100ms 대기
        }

        // 로딩 시간이 지나면 콘솔을 지움
        clearConsole();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
}