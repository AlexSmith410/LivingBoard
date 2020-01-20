import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class Solution {
    private static int currentIndex = -1;

    private static Integer next(String numbers[]) {
        currentIndex++;
        while (currentIndex < numbers.length && numbers[currentIndex].equals(""))
            currentIndex++;
        return currentIndex < numbers.length ? Integer.parseInt(numbers[currentIndex]) : null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n, m;
        System.out.println("Введите число N: ");
        n = Integer.parseInt(reader.readLine());
        System.out.println("Введите число M: ");
        m = Integer.parseInt(reader.readLine());
        System.out.println("1 - сгенерировать матрицу");
        System.out.println("2 - считать матрицу из файла");
        Element[][] elements = new Element[n][m];
        if (Integer.parseInt(reader.readLine()) == 1){
            genStartStatement(elements, n, m);
        }
        else{
            System.out.println("Введите имя файла: ");
            String fileName = reader.readLine();
            FileInputStream inFile = new FileInputStream(fileName);
            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            String text = new String(str);

            String[] numbers = text.split("\\D");
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++) {
                    elements[i][j] = new Element();
                    if (next(numbers) == 1)
                        elements[i][j].isAlive = true;
                    else
                        elements[i][j].isAlive = false;
                }
        }
        linkNeighbors(elements, n, m);
        while(true){
            printBoard(elements, n, m);
            System.out.println();
            updateBoard(elements, n, m);
            Thread.sleep(1000);

        }


    }

    public static void printBoard(Element[][] board, int size_n, int size_m){
        for (int i = 0; i < size_n; i++) {
            for (int j = 0; j < size_m; j++) {
                if (board[i][j].isAlive)
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println();
        }
    }

    public static void genStartStatement(Element[][] board, int size_n, int size_m){
        for (int i = 0; i < size_n; i++)
            for (int j = 0; j < size_m; j++){
                board[i][j] = new Element();
                int rand = (int) (Math.random() * 2);
                if (rand == 0)
                    board[i][j].isAlive = false;
                else
                    board[i][j].isAlive = true;
            }
    }

    public static void linkNeighbors(Element[][] board, int size_n, int size_m){
        board[0][0].neighbors.add(board[0][1]);
        board[0][0].neighbors.add(board[1][0]);
        board[0][0].neighbors.add(board[1][1]);
        board[0][size_m - 1].neighbors.add(board[0][size_m - 2]);
        board[0][size_m - 1].neighbors.add(board[1][size_m - 2]);
        board[0][size_m - 1].neighbors.add(board[1][size_m - 1]);
        board[size_n - 1][0].neighbors.add(board[size_n - 2][0]);
        board[size_n - 1][0].neighbors.add(board[size_n - 2][1]);
        board[size_n - 1][0].neighbors.add(board[size_n - 1][1]);
        board[size_n - 1][size_m - 1].neighbors.add(board[size_n - 1][size_m - 2]);
        board[size_n - 1][size_m - 1].neighbors.add(board[size_n - 2][size_m - 2]);
        board[size_n - 1][size_m - 1].neighbors.add(board[size_n - 2][size_m - 1]);

        for (int i = 1; i < size_m - 1; i++){
            board[0][i].neighbors.add(board[0][i - 1]);
            board[0][i].neighbors.add(board[0][i + 1]);
            board[0][i].neighbors.add(board[1][i - 1]);
            board[0][i].neighbors.add(board[1][i]);
            board[0][i].neighbors.add(board[1][i + 1]);
        }

        for (int i = 1; i < size_m - 1; i++){
            board[size_n - 1][i].neighbors.add(board[size_n - 1][i - 1]);
            board[size_n - 1][i].neighbors.add(board[size_n - 1][i + 1]);
            board[size_n - 1][i].neighbors.add(board[size_n - 2][i - 1]);
            board[size_n - 1][i].neighbors.add(board[size_n - 2][i]);
            board[size_n - 1][i].neighbors.add(board[size_n - 2][i + 1]);
        }

        for (int i = 1; i < size_n - 1; i++){
            board[i][0].neighbors.add(board[i - 1][0]);
            board[i][0].neighbors.add(board[i + 1][0]);
            board[i][0].neighbors.add(board[i - 1][1]);
            board[i][0].neighbors.add(board[i][1]);
            board[i][0].neighbors.add(board[i + 1][1]);
        }

        for (int i = 1; i < size_n - 1; i++){
            board[i][size_m - 1].neighbors.add(board[i - 1][size_m - 1]);
            board[i][size_m - 1].neighbors.add(board[i + 1][size_m - 1]);
            board[i][size_m - 1].neighbors.add(board[i - 1][size_m - 2]);
            board[i][size_m - 1].neighbors.add(board[i][size_m - 2]);
            board[i][size_m - 1].neighbors.add(board[i + 1][size_m - 2]);
        }

        for (int i = 1; i < size_n - 1; i++)
            for (int j = 1; j < size_m - 1; j++){
                board[i][j].neighbors.add(board[i - 1][j - 1]);
                board[i][j].neighbors.add(board[i - 1][j]);
                board[i][j].neighbors.add(board[i - 1][j + 1]);
                board[i][j].neighbors.add(board[i][j - 1]);
                board[i][j].neighbors.add(board[i][j + 1]);
                board[i][j].neighbors.add(board[i + 1][j - 1]);
                board[i][j].neighbors.add(board[i + 1][j]);
                board[i][j].neighbors.add(board[i + 1][j + 1]);
            }
    }

    public static void checkElement(Element element){
        int countOfLive = 0;
        for (Element neighbor: element.neighbors
        ) {
            if (neighbor.isAlive)
                countOfLive++;
        }
        if (!element.isAlive){
            if (countOfLive == 3)
                element.needChange = true;
            else
                element.needChange = false;
        }
        else{
            if (countOfLive < 2)
                element.needChange = true;
            if (countOfLive == 2 || countOfLive == 3)
                element.needChange = false;
            if (countOfLive > 3)
                element.needChange = true;
        }
    }

    public static void updateBoard(Element[][] board, int size_n, int size_m){
        for (int i = 0; i < size_n; i++) {
            for (int j = 0; j < size_m; j++) {
                checkElement(board[i][j]);
            }
        }

        for (int i = 0; i < size_n; i++) {
            for (int j = 0; j < size_m; j++){
                if (board[i][j].needChange)
                    board[i][j].isAlive = !board[i][j].isAlive;
            }
        }

    }
}
