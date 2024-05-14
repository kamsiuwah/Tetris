public abstract class TetrisBrick {
    protected int numSegments = 4;
    protected int[][] position;
    int orientation = 1;
    protected int colorNum;

    public TetrisBrick() {
        this.position = position;
        this.numSegments = numSegments;
        this.colorNum = colorNum;


    }

    public int getNumSegments() {
        return numSegments;
    }
    public abstract void initPosition( int center_col);
    public abstract void rotate();
    public abstract void unrotate();
    public void moveLeft(){
        for(int seg = 0; seg < numSegments; seg++){
            position[seg][1] -= 1;
        }
    }
    public void moveRight(){
        for(int seg = 0; seg < numSegments; seg++){
            position[seg][1] += 1;
        }
    }
    public void moveDown(){
        for(int seg = 0; seg < numSegments; seg++){
            for(int dex = 0; dex < position.length/2; dex +=2){
                position[seg][dex] += 1;
            }
        }
    }
    public void moveUp(){
        for(int seg = 0; seg < numSegments; seg++){
            for(int dex = 0; dex < position.length/2; dex +=2){
                position[seg][dex] -= 1;
            }
        }
    }

}
