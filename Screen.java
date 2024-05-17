package MainRepository;

public class Screen{
    protected static int selectedIndex = 0;
    public static void printScreen(){};
    public static void printCardFrame(){};
    public static void printMenu(){};
    public static void clearConsole() {
        // ANSI 이스케이프 시퀀스를 사용하여 콘솔을 지우기
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}