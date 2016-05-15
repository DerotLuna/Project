public class Board {

  private String shape;
 // private Layer[] layer;

  public Board(String shape){
    this.shape = shape;

  }

  public String getShape(){
    return this.shape;
  }



}
/*recordatorio: necesito la relacion entre casillas para la vecindad.
creo que si va a ser necesario crear un tablero que extienda de board (ruben lo dijo);
World es quien se encargara de posicionar las pieza en el tablero*/

