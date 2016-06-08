import java.awt.Color;

public class Box{
  private Status status;
  private byte id;
  private Color color;

  public Box(int id){
    this.id = (byte) id;
    this.status = new FreeBox(); //cambiara, puede tener otro estado al crearse (dependiendo del estilo de juego).
  }

  public void setStatus(Status status){
    this.status = status;
  }

  public String getStatusBox(){
      return status.getStatusBox();
  }

  public byte getIdBox(){
      return this.id;
  }

  public Color getColorBox(){
    return this.color;
  }

  public void setColorBox(Color color){
    this.color = color;
  }
}
