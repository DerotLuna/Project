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



}
