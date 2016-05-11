public class Player{
  //atributos
  private String name;
  private int score;
  //private Trofy trofy; tal vez sea bueno trabajar el trofeo como un objeto.

  public Player(String name) {
      this.name = name;
      this.score = 0;//tiene que inicializarse en 0, luego sera llamado y este valor sera cambiado.
  }

/*  public Trofy setTrofy(){
       dependiendo de la condicion se
       crea el objeto trofeo;
    }*/
}
