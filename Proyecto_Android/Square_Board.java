public class Square_Board extends Board{
//atributos
  private int dimension;
  private String shape;
  private Box[] boxes;
  private int boxesCounter;
  public boolean[][] matrixAdjacency;
  public Status statusBox;
  
  public Square_Board(int dimension, String shape){
    super(shape);
    this.dimension = dimension;
    this.boxesCounter = dimension*dimension;
    this.boxes[] = new Box[boxesCounter];//si es 10*10 seran 100 casillas en total.
    this.matrixAdjacency[][] = new boolean[dimension][dimension];

    for (int numberBox = 0; numberBox < boxesCounter ; numberBoxes++){
    //aca se crean las casillas del tablero.
      boxes[numberBox] = new Box(numberBox , this.statusBox.EMPTY);//cuando se crean por ahora se colocaran libres.
    }

   //no estoy seguro de que esta matriz sea de esta forma
  /*for (int row = 0; row < dimension ; row++){ matriz de Adyacencias
      for (int column = 0; column <dimension ; column++){
         this. matrixAdjacency[row][column] = false;
                      **FALTA CODIGO**
      solo es una idea} 
    }*/

  }


}
