public class Square_Piece extends Piece{

    static final int dimension = 2;
    private String[][] squarePiece;
    //public ???? pivote; necesito colocar la casilla pivote en la pieza, NECESITO IDEAAAAAAAAASSSS!!!!!!!!!!!!

    public Square_Piece(String color ,String shape){
      super(color,shape);
      this.squarePiece = new String[this.dimension][this.dimension];
    }

    public void square_Piece_Construction(){

        for (int row = 0; row < this.squarePiece.length; row ++){
          for (int column = 0; column < this.squarePiece.length ;column ++){
                this.squarePiece[row][column] = "*";
                //debo de hacer algo aca para que se le asigne la casilla pivote;
          }
        }
    }
}
