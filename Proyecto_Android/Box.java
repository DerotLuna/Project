public class Box {
  //atributos
  private int id;
  private Status statusBox;


  public Box(int id,Status statusBox){
    this.id = id; // pensar mejor la obtencion del id de una casilla
    this.statusBox = statusBox;
  }

  public void getId(){
    return this.id;
  }

  public Status getStatus(){
    return this.statusBox;
  }
  
  public boolean validatesBox(){// ya vere que pasar por parametro
    /*este metodo me funcionara para verificar si la casilla esta ocupada o vacia*/
    if (this.statusBox == statusBox.EMPTY) return true;
    else return false;

  }
}
