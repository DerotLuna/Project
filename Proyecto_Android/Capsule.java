public class Capsule{
    //Atributos
    public Object[] capsule;
    public Piece piece;
    private int  numberCapsule;/*cuando quiera generar una pieza nueva, este atributo me permitira saber
                                      en que posicion poner esa pieza nueva generada*/
    private boolean estateCapsule;//me va a decir si la capsula esta vacia y necesita una pieza;
    public int dimension;

      public Capsule(){
          this.numberCapsule = 3; // por ahora coloco el 3 de esta manera;

      }

      public int getNumberCapsule(){
        return this.numberCapsule;
      }

      public void generate_Piece(){

        Random randomPieceId = new Random();
        int number = randomPieceId.nextInt((18)+1);
          this.piece.generate_Piece(number);//el generar lo decide la pieza no Capsule, por lo tanto delega
      }

      /*public void generate_Piece(){

        Random randomPieceId = new Random();
        int number = randomPieceId.nextInt((18)+1);
        this.piece.generate_Piece(number);//el generar lo decide la pieza no Capsule, por lo tanto delega
        
          if number = 1 {
          instancio la pieza y llamo al metodo tal para construirla
          else number = 2
          lo mismo
          else ......
          y asi sucesivamente hasta el 18.
            - me parece que es enrollarse mucho al realizarlo de esta manera,son 18 metodos
            que hay que hacer dentro de piece, y este metodo lo realizaria capsule.
        }
                        necesito una idea de como asignar la pieza a la capsula */

}
