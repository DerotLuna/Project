public class Box {
  //atributos
  private int id;
  private int boxPosition;
  private Status statusBox;


  public Box(int boxPosition,Status statusBox){
    this.boxPosition = boxPosition;
    this.statusBox = statusBox;
    this.id =//pensar la manera de conseguir el id de una casilla.
  }

  public void getId(){
    return this.id;
  }

  public Status getStatus(){
    return this.statusBox;
  }



}
