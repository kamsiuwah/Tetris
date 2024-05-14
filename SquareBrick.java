public class SquareBrick extends TetrisBrick{
    public SquareBrick(int cen_col) {
        initPosition(cen_col);
        super.colorNum = 5;
    }
    public void initPosition(int center_col) {
        position = new int[][]{
                { 0,center_col+1},
                { 0, center_col},
                { 1, center_col+1},
                { 1, center_col}
        };
    }
    public void rotate(){

    }
    public void unrotate(){

    }
}
