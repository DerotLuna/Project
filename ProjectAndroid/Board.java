import java.util.Arrays;

public abstract class Board{
  protected byte id, dimension;
  protected Box[] boxes;
  protected int numberOfBoxes;
  protected boolean[][] neighborhood;
  protected Layer[] layers;

  public Board(byte dimension, byte id){
    this.dimension = dimension;
    this.numberOfBoxes = dimension*dimension;
    this.id = id;
    //this.numberOfBoxes = columns*rows;
    boxes = new Box[numberOfBoxes];
    for (byte counter = 0; counter < numberOfBoxes; counter++) { //no creo que se necesiten mas de 127 casillas, digo que el max sera 100.
      boxes[counter] = new Box(counter);
    }
    neighborhood = new boolean[numberOfBoxes][numberOfBoxes]; //inicializar la topologia de vencindad en false
    for (int counterRows = 0; counterRows < numberOfBoxes; counterRows++) {
      for (int counterColumns = 0; counterColumns < numberOfBoxes; counterColumns++) {
          neighborhood[counterRows][counterColumns] = false;
      }
    }

    //testDelete();
  }

  public Box getBox(int positionBox){
    return this.boxes[positionBox];
  }
  public byte getIDBoard(){
    return this.id;
  }

  public abstract void shapeBoard();

  public abstract boolean checkPositions(Piece piece, int positionBox ,int answer);

  public int verifyLayer(){
    for (byte counter = 0; counter < (dimension * 2); counter ++) { //verifico las capas
      layers[counter].verifyLayer(dimension);
    }
    byte counter = 0; int addRelease = 0; //libero las capas
    while(counter < (dimension * 2)) {
      addRelease += layers[counter].boxesRelease(dimension);
      counter ++;
    }
    return addRelease;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////PRUEBAS//////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void printNeighborhood(){
    for (int counterRows = 0; counterRows < numberOfBoxes; counterRows++) {
      for (int counterColumns = 0; counterColumns < numberOfBoxes; counterColumns++)
          System.out.print("| " + neighborhood[counterRows][counterColumns] + " | ");
      System.out.println();
    }
  }

  public void testDelete(){ //Metodo solo de prueba, para verificar la liberacion de filas y columnas, tambien sumar puntaje.
    Status fullBox = Status.FULL;
    byte counterJump = 0, positionBoxR = 10, positionBoxC = 7, positionBoxR2 = 20;
    while(counterJump < dimension){
      boxes[positionBoxR].setStatus(fullBox);
      boxes[positionBoxC].setStatus(fullBox);
      boxes[positionBoxR2].setStatus(fullBox);
      positionBoxR ++;
      positionBoxR2 ++;
      if ((positionBoxC + dimension) <= numberOfBoxes){
        positionBoxC += dimension;
      }
      counterJump ++;
    }
  }

  public void printLayers(){ // REVISAR ESTO!!!!!!!
    for (byte counter = 0; counter < dimension*2; counter ++) {
      layers[counter].printLayers(dimension);
    }
  }
}
