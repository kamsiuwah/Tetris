
public class ElBrick extends TetrisBrick{
    int cur_row;
    int cur_col;
    public ElBrick(int cen_col) {
        initPosition(cen_col);
        colorNum = 3;
    }
    public void initPosition( int center_col){
        position = new int[][]{
                { 2,center_col+1},
                { 2, center_col},
                { 1, center_col},
                { 0, center_col}
        };
    }
    public void  rotate(){

        cur_row = position[1][0];
        cur_col  = position[1][1];
        if(orientation == 1){
            position = new int[][]{
                    { cur_row+1,cur_col},
                    { cur_row, cur_col},
                    { cur_row, cur_col+1},
                    { cur_row, cur_col+2}
            };
            orientation =2;
        }
        else if(orientation == 2){
            position = new int[][]{
                    { cur_row,cur_col-1},
                    { cur_row, cur_col},
                    { cur_row+1, cur_col},
                    { cur_row+2, cur_col}
            };
            orientation =3;
        }
        else if(orientation == 3){
            position = new int[][]{
                    { cur_row-1,cur_col},
                    { cur_row, cur_col},
                    { cur_row, cur_col-1},
                    { cur_row, cur_col-2}
            };
            orientation = 4;
        }
        else if (orientation == 4){
            position = new int[][]{
                    { cur_row,cur_col+1},
                    { cur_row, cur_col},
                    { cur_row-1, cur_col},
                    { cur_row-2, cur_col}
            };
            orientation = 1;
        }

    }
    public void unrotate(){
        cur_row = position[1][0];
        cur_col  = position[1][1];
        if (orientation == 1){
            position = new int[][]{
                    { cur_row-1,cur_col},
                    { cur_row, cur_col},
                    { cur_row, cur_col-1},
                    { cur_row, cur_col-2}
            };
            orientation = 2;
        }
        else if(orientation == 2){
                position = new int[][]{
                        { cur_row,cur_col-1},
                        { cur_row, cur_col},
                        { cur_row+1, cur_col},
                        { cur_row+2, cur_col}
                };
                orientation = 3;
            }
        else if(orientation == 3){
            position = new int[][]{
                    { cur_row+1,cur_col},
                    { cur_row, cur_col},
                    { cur_row, cur_col+1},
                    { cur_row, cur_col+2}
            };
            orientation =4;
        }
        else if(orientation == 4){
            position = new int[][]{
                    { cur_row,cur_col+1},
                    { cur_row, cur_col},
                    { cur_row-1, cur_col},
                    { cur_row-2, cur_col}
            };
            orientation =1;
        }
    }
}
