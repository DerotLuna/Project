public class Board {

  private String shape;
  private Layer[] layer;

  public Board(String shape){
    this.shape = shape;

  }

  public String getShape(){
    return this.shape;
  }



}
/*recordatorio: necesito la relacion entre casillas para la vecindad.
creo que si va a ser necesario crear un tablero que extienda de board (ruben lo dijo);
Idea: board solo se encargaria la condicion en comun que deben de cumplir todos los tableros
bien sean rectangulares, sean triangulares o de cualquier forma es decir, verificar las filas, columnas y
crear las capas necesarias para esto*/

