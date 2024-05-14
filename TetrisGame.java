
import javax.swing.*;
import java.io.*;
import java.util.*;

public class TetrisGame {
    TetrisBrick fallingBrick;
    private ArrayList<String> saved_Files = new ArrayList<>();
    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 6;
    private int state = 1;
    private int score;
    Random randomGen;
    public TetrisGame(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;

        randomGen = new Random();
        background = new int[rows][cols];
        recover_old_games();
        spawnBrick();

    }
    public String toString(){
        String previous_board = rows +"  "+cols+"   "+score+"\n";
        for(int row = 0; row < background.length; row++){
            for(int col = 0; col < background[0].length;col++){
                previous_board += background[row][col]+" ";
            }
            previous_board += "\n";
        }
        previous_board = previous_board.substring(0,previous_board.length()-1);
        return previous_board;
    }
    public void newGame(){

        for (int row = 0; row <rows; row++){
            for (int col = 0; col <cols; col++){
                background[row][col] = 0;
                spawnBrick();
            }
        }
        state = 1;

    }
    public int fetchBoardPosition(int row, int col){
        return background[row][col];
    }
    private void spawnBrick(){

        int brick = randomGen.nextInt(numBrickTypes)+1;
        int centerCol = (cols)/2 ;
        if(state == 1){
            if(brick == 1)
                fallingBrick = new StackBrick(centerCol);
            if(brick == 2)
                fallingBrick = new EssBrick(centerCol);
            if(brick == 3)
                fallingBrick = new ElBrick(centerCol);
            if(brick == 4)
                fallingBrick = new ZeeBrick(centerCol);
            if(brick == 5)
                fallingBrick = new SquareBrick(centerCol);
            if(brick == 6)
                fallingBrick = new LongBrick(centerCol);
            if(brick == 7)
                fallingBrick = new JayBrick(centerCol);
        }
    }
    public void makeMove(char move){

        int deleted_rows;
        if(state == 1){
            if(move == 'D' ){
                fallingBrick.moveDown();
                if(validateMove() == false){
                    transferColor();
                    deleted_rows = row_deleter();
                    score = scoring_machine(deleted_rows);
                    fallingBrick.orientation = 1;
                    spawnBrick();
                }
            }
            if (move == 'S') {
                fallingBrick.rotate();
                if(validateMove() ==false){
                    fallingBrick.unrotate();
                }
            }
            if(move == 'R'){
                fallingBrick.moveRight();
                if(validateMove() ==false){
                    fallingBrick.moveLeft();
                }
            }
            if(move == 'L'){
                fallingBrick.moveLeft();
                if(validateMove() ==false){
                    fallingBrick.moveRight();
                }
            }
        }
    }
    private boolean validateMove(){
        if(state == 1){
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    for(int seg = 0; seg < fallingBrick.numSegments; seg++){
                        if(fallingBrick.position[seg][0] == row && fallingBrick.position[seg][1] == col){
                            if (background[1][col] != 0 && state == 1){
                                if(score != 0)
                                    recordScore(score);
                                state = 0;
                            }
                            if (background[row][col] != 0){
                                return false;
                            }


                        }
                    }

                }
            }

            for(int seg = 0; seg <getNumSegs(); seg++) {
                if (fallingBrick.position[seg][0] == rows) {
                    return false;
                }
            }
            for(int seg = 0; seg <getNumSegs(); seg++){
                if(fallingBrick.position[seg][1] < 0 || fallingBrick.position[seg][1] == cols){
                    return false;
                }
            }
        }

        return true;
    }
    public int getNumSegs(){
        return fallingBrick.getNumSegments();
    }
    public int getFallingBrickColor(){
        return fallingBrick.colorNum;
    }
    public int getSegRows(int seg_number) {
        return fallingBrick.position[seg_number][0];
    }
    public int getSegCols(int seg_number) {
        return fallingBrick.position[seg_number][1];
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public int getState(){
        return state;
    }
    public int getScore(){
        return score;
    }
    private void transferColor(){
        for(int seg = 0; seg < getNumSegs(); seg++)
            background[fallingBrick.position[seg][0] - 1][fallingBrick.position[seg][1]] = getFallingBrickColor();

    }
    private int scoring_machine(int deletedRows){
        switch( deletedRows){
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;

        }
        return score;

    }
    private int row_deleter(){
        int start_row =0;
        int end_row = 0;
        int deletedBricks = 0;
        int complete = 0;
        for(int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if(background[row][col] != 0){
                    complete ++;

                }
                else if(background[row][col] == 0){
                    complete = 0;
                    break;}
            }
            if (complete == cols ){
                if(start_row == 0)
                    start_row = row;
                deletedBricks ++;
                complete = 0;

            }
        }
        end_row = start_row + (deletedBricks-1);
        deleteBricks(start_row, end_row);
        drop_down(start_row, deletedBricks);
        return deletedBricks;
    }
    private void deleteBricks(int start_row, int end_row){
        for(int row = start_row; row <= end_row; row++){
            for (int col = 0; col < cols; col++) {
                background[row][col] = 0;
            }
        }
    }
    private void drop_down(int start_row, int drop_rows){
        int color_code = 0;
        for(int row = start_row; row > 0; row--){
            for(int col = 0; col < cols; col ++){
                if(background[row][col] != 0){
                    color_code = background[row][col];
                    background[row][col] =0;
                    background[row +drop_rows][col] =color_code;
                }
            }
        }
    }
    public void saveToFile(String fName){
        File fileConnection = new File(fName);
        if(fileConnection.exists() && !fileConnection.canWrite()){
            System.err.print("Trouble opening to Write, file: "+fName);
            return;
        }
        try{
            saved_Files.add(fName);
            name_directory(fName);
            FileWriter outWriter =new FileWriter (fileConnection);
            outWriter.write(this.toString());
            outWriter.close();
        }
        catch (IOException ioe){
            System.err.print("Trouble writing to file:"+ fName);
        }
    }
    public void retrieveFromFile(){
        try{

            Object[] saved_games = saved_Files.toArray();
            int choice = JOptionPane.showOptionDialog(null, "Saved Games", "Test", 0,0,null, saved_games, saved_games[0]);
            String fName = saved_games[choice].toString();
            File fileConnection = new File(fName);
            Scanner inScan = new Scanner(fileConnection);
            rows = inScan.nextInt();
            cols = inScan.nextInt();
            score = inScan.nextInt();
            background = new int[rows][cols];

            for (int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col ++){
                    background[row][col] = inScan.nextInt();
                }
            }
        }
        catch (FileNotFoundException e){
            System.err.print("Trouble opening file to read:   ");
            return;
        }
        catch (Exception e){
            System.err.print("Error occured during read from File");
        }
    }
    private void name_directory(String new_name){
        File fileConnection = new File("All_saved_games.dat");
        if(fileConnection.exists() && !fileConnection.canWrite()){
            System.err.print("Trouble opening to Write, file: ");
            return;
        }
        try{
            FileWriter outWriter =new FileWriter (fileConnection, true);
            outWriter.write(new_name+"\n");
            outWriter.close();
        }
        catch (IOException ioe){
            System.err.print("Trouble writing to file:");
        }
    }
    private void recover_old_games(){
        try {
            File directory_name = new File("All_saved_games.dat");
            Scanner inScan = new Scanner(directory_name);
            while(inScan.hasNextLine()){
                saved_Files.add(inScan.nextLine());
            }
            inScan.close();
        }
        catch (FileNotFoundException e){
            System.err.print("Trouble opening file: All_saved_games.dat");
        }
    }
    private void recordScore(int score){
        File fileConnection = new File("Score.dat");
        if(fileConnection.exists() && !fileConnection.canWrite()){
            System.err.print("Trouble opening to Write, file: Score");
            return;
        }
        try{
            FileWriter outWriter =new FileWriter (fileConnection, true);
            outWriter.write(score+"\n");
            outWriter.close();
        }
        catch (IOException ioe){
            System.err.print("Trouble writing to file:");
        }
    }
    public String score_board(String file_name){
        ArrayList<Integer> unsorted_Scores = new ArrayList();
        String scores = "";
        try {
            File directory_name = new File(file_name);
            Scanner inScan = new Scanner(directory_name);
            while(inScan.hasNextLine()){
                unsorted_Scores.add(Integer.parseInt(inScan.nextLine()));
                scores = score_sorter(unsorted_Scores);
            }
            inScan.close();
        }
        catch (FileNotFoundException e){
            System.err.print("Trouble opening file: All_saved_games.dat");
        }
        return scores;
    }
    private String score_sorter(ArrayList scores){
        String new_scores = "";
        int number = 1;
        Collections.sort(scores);
        for(int dex = scores.size()-1; dex > 0; dex--){
            new_scores += "Score "+number+": "+scores.get(dex).toString() +"\n";
            number ++;
        }
        return new_scores;
    }
    public void clearScore(){
        try{
            FileWriter file = new FileWriter("Score.dat", false);
            PrintWriter flusher = new PrintWriter(file, false);
            flusher.flush();
            flusher.close();
            file.close();
        }
        catch (IOException e){
            System.err.print("Cant find file: Score");
        }
    }
}
