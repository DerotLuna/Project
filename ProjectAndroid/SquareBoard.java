import java.awt.Color;

public class SquareBoard extends Board{
  private static final Status STATUS_FREE = Status.FREE; //se utiliza para verificar si las casillas estan libres.
  private static final Status  STATUS_FULL = Status.FULL;
  public SquareBoard(byte dimension, byte id){
    super(dimension, id);
    //Aqui hago la creacion de mis capas, por cada fila y columna.
    layers = new Layer[dimension * 2];
    byte counterLayers = 0, counterRows = 0, counterColumns = 0, positionBoxLayer = 0;
    while(counterLayers < dimension){
      layers[counterLayers] = new Layer("Row " + counterLayers, dimension);
      layers[counterLayers + dimension] = new Layer("Column " + counterLayers, dimension);
      positionBoxLayer = 0;
      while(positionBoxLayer < dimension){
        //verificar si es realmente necesario, que las casillas sepan a que capa pertenecen...
        layers[counterLayers].setBoxToLayer(boxes[counterRows], positionBoxLayer);
        layers[counterLayers + dimension].setBoxToLayer(boxes[counterColumns], positionBoxLayer);
        counterRows ++;
        counterColumns += dimension;
        positionBoxLayer ++;
      }
      counterLayers ++;
      counterColumns = counterLayers;
    }

    //super.printLayers();
  }

  public void shapeBoard(){
    //Se llena la matriz de adyacencia donde se dice quienes son vecinos.
    byte rowAndColumn = 0, counterDown = dimension, counter = (byte)(counterDown - 1);
    while (rowAndColumn < (numberOfBoxes - 1)){
      if(rowAndColumn <= counter){
        if (counter < numberOfBoxes - 1){
          neighborhood[rowAndColumn][counterDown] = true;
          neighborhood[counterDown][rowAndColumn] = true;
        }
        if (rowAndColumn < counter){
          neighborhood[rowAndColumn + 1][rowAndColumn] = true;
          neighborhood[rowAndColumn][rowAndColumn + 1] = true;
        }
      }
      if (rowAndColumn == counter) counter = counterDown;
      if (counterDown < numberOfBoxes - 1) counterDown ++;
      rowAndColumn ++;
    }
  }

  public boolean checkPositions(Piece piece, int positionBox ,int answer){
    boolean checked = true;
    /*puede que le a~ada un parametro mas que se llame o answer o algo por el estilo para saber si se esta chequeando
    por el endofGame o se esta chequeando para anadir una pieza*/

    //creo que el pivote que definiremos seria un pivote logico, no podemos tener una variable, o no la veo por lo menos.
    //estas variables pueden que no sean necesarias.
    //si answer es igual a 0 el checkposition sera para verificar si una pieza cabe, si es 1 sera para  verificar endofGame
    String typePiece = piece.getTypePiece();
    byte sizePiece = piece.getSizePiece();

      if (typePiece == "SQUARE") checked = squareEvaluations(sizePiece, positionBox, checked);
      else if (typePiece == "VERTICAL LINE") checked = lineEvaluations(sizePiece, positionBox, 2, checked);
      else if (typePiece == "HORIZONTAL LINE") checked = lineEvaluations(sizePiece, positionBox, 1, checked);
      else if(typePiece == "L") checked = lEvaluation(sizePiece, positionBox, checked);
      else if(typePiece == "L LEFT") checked = leftLEvaluation(sizePiece, positionBox, checked);
      else if (typePiece == "L INVESTED") checked = investedLEvaluation(sizePiece, positionBox, checked);
      else checked = leftInvestedLEvaluation(sizePiece, positionBox, checked);
      if ((answer == 0) && (checked))  addPiece(typePiece, sizePiece, positionBox);

    return checked;
  }
  public boolean squareEvaluations(byte sizeSquare, int positionBox, boolean checked){
    //Aqui verificamos cualquier pieza que sea cuadrada, no importa a dimension.
    if (sizeSquare == 1){
      if (boxes[positionBox].getStatus() != STATUS_FREE) checked = false;
    }
    else{ //sera general para todos las dimensiones de los cuadrados
      int positionBoxDown = positionBox + dimension;
      int referenceBox = positionBoxDown;
      byte counterExit = 1;
      exit:
      while(counterExit < sizeSquare){
        byte counterJump = 1;
        while(counterJump < sizeSquare){ //estar pendiente de la 2da condicion
          if(positionBoxDown >= numberOfBoxes || !neighborhood[positionBox][positionBox + 1]){
            checked = false;
            break exit;
          }
          else{
            if(boxes[positionBox].getStatus() == STATUS_FREE && boxes[positionBox + 1].getStatus() == STATUS_FREE && boxes[positionBoxDown].getStatus() == STATUS_FREE){
              positionBox ++;
              positionBoxDown ++;
            }
            else{
              checked = false;
              break exit;
            }
          }
          counterJump ++;
        }

        if (counterExit == sizeSquare - 1){
          positionBoxDown ++;
          if (boxes[positionBoxDown].getStatus() != STATUS_FREE) checked = false;
        }
        counterExit ++;
        positionBox = referenceBox;
        positionBoxDown = referenceBox + dimension;
        referenceBox = positionBoxDown;
      }
    }
    return checked;
  }

  public boolean lineEvaluations(byte sizeLine, int positionBox, int option, boolean checked){
    //Aqui se verifican las piezas en linea, horizontal o vertical de cualquier size.
    byte counterExit = 0;

      if(option == 1){
        while(counterExit < sizeLine){

          if (counterExit == sizeLine - 1){
            if(boxes[positionBox].getStatus() == STATUS_FREE) positionBox ++;
            else{
              checked = false;
              break;
            }
          }
          else{
            if ((positionBox > numberOfBoxes - sizeLine) || !neighborhood[positionBox][positionBox + 1]){
              checked = false;
              break;
            }
            else{
              if(boxes[positionBox].getStatus() == STATUS_FREE) positionBox ++;
              else{
                checked = false;
                break;
              }
            }
          }
        counterExit ++;
        }
      }
      else{
        while(counterExit < sizeLine && positionBox <= numberOfBoxes){ //estar pendiente de la 2da condicion

          if (counterExit == sizeLine - 1){
            if(boxes[positionBox].getStatus() == STATUS_FREE) positionBox ++;
            else{
              checked = false;
              break;
            }
          }
          else{
            if((positionBox >= numberOfBoxes - dimension) || !neighborhood[positionBox + dimension][positionBox]){
              checked = false;
              break;
            }
            else{
              if(boxes[positionBox].getStatus() == STATUS_FREE) positionBox += dimension;
              else{
                checked = false;
                break;
              }
            }
          }
        counterExit ++;
        }
      }
    return checked;
  }

  public boolean lEvaluation(byte lSize ,int positionBox ,boolean checked){
    //Aqui se evaluan las piezas en forma de L normal, de cualquier size en su parte mas larga.
    //errores corregidos, ahora esta pieza no se coloca en cualquier sitio.
    int pivot = positionBox;
    byte exitCounter = 0;
    boolean verifyWay = false;//esta es la variable que me dara la senal para recorrer mi L por columnas o por filas.
    /* mi pivote siempre estara en el vertice de la L
       PD: no queria usar el break y por eso los quite.*/

      while ((exitCounter < lSize) && (checked)){// begin loop

          if(!verifyWay){
            if (pivot + 1 >= numberOfBoxes) pivot -= 1;//esta condicion se realiza para que no se pase del arreglo booleano de la matriz de adyacencia y no me de un exception.
            if ( (positionBox >= numberOfBoxes - (lSize / 2)) || (positionBox >= numberOfBoxes - 1) || (!neighborhood[pivot][pivot + 1])) checked = false;
            else{
              if (boxes[pivot].getStatus() == STATUS_FREE) pivot ++;
              else checked = false;
            }
          }
          else {
            if (pivot < 0) checked = false; // aca no es necesario verificar la vecindad de casillas;
            else {
              if (boxes[pivot].getStatus() == STATUS_FREE) pivot -= dimension;
              else checked = false;
            }
          }

          if (exitCounter == lSize / 2){
          verifyWay = true;
          pivot = positionBox - dimension;
          }

      exitCounter++;
      }//end loop
    System.out.println("/////////////////////" + lSize/2);
    return checked;
  }

  public boolean leftLEvaluation(byte lSize ,int positionBox ,boolean checked){
    //Aqui se evaluan las piezas en forma de L pero hacia la izquierda, de cualquier size en su parte mas larga.
      byte exitCounter = 0;
      boolean verifyWay = false;
      int pivot = positionBox;
      /*corregido los errores de la colocacion de esta pieza, ahora se posiciona de manera correcta*/

    while ((exitCounter < lSize) && (checked)){
        if (!verifyWay){
          if (exitCounter != lSize/2){
            if ((positionBox < dimension*(lSize / 2)) || (!neighborhood[pivot][pivot - 1])) checked = false;
            else{
              if (boxes[pivot].getStatus() == STATUS_FREE) pivot --;
              else checked = false;
            }
          }
          else{
              if (boxes[pivot].getStatus() == STATUS_FREE) pivot = positionBox - dimension;
              else checked = false;
              verifyWay = true;
          }
        }
        else{
          if (pivot < 0) checked = false;// no hay necesidad de verificar la vecindad entre casillas
          else{
            if (pivot < dimension){
              if (boxes[pivot].getStatus() == STATUS_FREE) dimension -= pivot;
              else checked = false;
            }
            else{
              if (boxes[pivot].getStatus() == STATUS_FREE) pivot -= dimension;
              else checked = false;
            }
          }
        }
      exitCounter++;
      }
      System.out.println("/////////////////////" + lSize/2);
      return checked;
  }

  public boolean investedLEvaluation(byte lSize ,int positionBox ,boolean checked){
    //Aqui se evaluan las L invertidas, de cualquier size en su parte mas larga.
    byte exitCounter = 0;
    boolean verifyWay = false;
    int pivot = positionBox;
    // me esta dando un error con la vecindad, ARREGLAR!!!!.
      while ((exitCounter < lSize) && (checked)){
          if (!verifyWay){
            if (exitCounter != lSize/2){
                System.out.println("///////////////1//////" + pivot);
              if ((positionBox > numberOfBoxes - (dimension * (lSize/2))) || (!neighborhood[pivot][pivot + 1])) checked = false;
              else{
                if (boxes[pivot].getStatus() == STATUS_FREE) pivot ++;
                else checked = false;
                System.out.println("///////////////2//////" +pivot);
              }
            }
            else{
                if (boxes[pivot].getStatus() == STATUS_FREE) pivot = positionBox + dimension;
                else checked = false;
                verifyWay = true;
                System.out.println("////////////3/////////" + pivot);
            }
          }
          else{
            System.out.println("////////////checked/////////" + pivot);
             if (pivot > numberOfBoxes) checked = false;// no hay necesidad de verificar la vecindad entre casillas
             else{
               System.out.println("/////////////////4////" + pivot);
               if (boxes[pivot].getStatus() == STATUS_FREE) pivot += dimension;
               else checked = false;
               System.out.println("///////5//////////////" + pivot);
            }
          }

      exitCounter ++;
      }
    System.out.println("/////////pieza l invertidas////////////" + pivot);
    return checked;
  }

  public boolean leftInvestedLEvaluation(byte lSize ,int positionBox ,boolean checked){
    //Aqui se evaluan las piezas en forma de L invertidas y a las izquierda, de cualquier size en su parte mas larga.
    byte exitCounter = 0;
    boolean verifyWay = false;
    int pivot = positionBox;
    /*error, esta pieza se esta colocando en sitios que no se deben.
      se debe corregir el algoritmo*/

    while ((exitCounter < lSize) && (checked)){
      if (!verifyWay){
        if (pivot - 1 < 0) pivot += 1;
        if ((positionBox < lSize /2 -1) || (!neighborhood[pivot][pivot - 1])) checked = false;
        else{
          System.out.println("/////////pase por aca y se cumplio la condicion ////////////" + pivot);
          if (boxes[pivot].getStatus() == STATUS_FREE) pivot --;
          else checked = false;
        }
      }
      else{
        if (pivot > numberOfBoxes) checked = false;// no hay necesidad de verificar la vecindad entre casillas
        else{
          if (boxes[pivot].getStatus() == STATUS_FREE) pivot += dimension;
          else checked = false;
        }
      }

      if (exitCounter == lSize / 2){
        verifyWay = true;
        pivot = positionBox + dimension;
      }
      System.out.println("/////////////////////");
      exitCounter++;
    }
    System.out.println("///////ya sali del ciclo y retorno//////////////" + lSize/2);
    return checked;
  }

  public void addSquare(byte sizeSquare, int positionBox){
    if(sizeSquare == 1){
      boxes[positionBox].setStatus(STATUS_FULL);
    }
    else{
      int positionBoxDown = positionBox + dimension;
      byte counterExit = 1;
      while(counterExit <= sizeSquare){
        byte counterJump = 1;
        while(counterJump <= sizeSquare){
          boxes[positionBox].setStatus(STATUS_FULL);
          counterJump ++;
          positionBox++;
        }
        positionBox = positionBoxDown;
        positionBox += dimension;
        counterExit ++;
      }
    }
    System.out.println("positionBox: " + positionBox);
  }

  public void addLine(byte sizeLine, int positionBox, int answer){
    byte counterExit = 1;
    while(counterExit <= sizeLine){
      boxes[positionBox].setStatus(STATUS_FULL);
      counterExit ++;
      System.out.println("positionBox: " + positionBox);
      if(answer == 1)  positionBox++;
      else positionBox += dimension;
    }
  }

  public void addL(byte lSize, int positionBox){
    byte exitCounter = 0;
    int pivot = positionBox;
    boolean verifyWay = false;

      while (exitCounter < lSize){
        if (!verifyWay){
          boxes[pivot].setStatus(STATUS_FULL);
          pivot++;
        }
        else {
          boxes[pivot].setStatus(STATUS_FULL);
          pivot -= dimension;
        }

        if (exitCounter == lSize / 2){
          verifyWay = true;
          pivot =  positionBox - dimension;
        }
      exitCounter ++;
      }
    }


    public void addLeftL(byte lSize, int positionBox){
      byte exitCounter = 0;
      boolean verifyWay = false;
      int pivot = positionBox;

      while (exitCounter < lSize){
        if (!verifyWay){
          boxes[pivot].setStatus(STATUS_FULL);
          pivot --;
        }
        else {
          boxes[pivot].setStatus(STATUS_FULL);
          pivot -= dimension;
        }

        if (exitCounter == lSize / 2){
          verifyWay = true;
          pivot = positionBox - dimension;
        }
      exitCounter ++;

      }
    }

    public void addInvestedL(byte lSize, int positionBox){
      byte exitCounter = 0;
      int pivot = positionBox;
      boolean verifyWay = false;

      while (exitCounter < lSize){
        if (!verifyWay){
          boxes[pivot].setStatus(STATUS_FULL);
          pivot++;
        }
        else {
          boxes[pivot].setStatus(STATUS_FULL);
          pivot += dimension;
        }

        if (exitCounter == lSize / 2){
          verifyWay = true;
          pivot = positionBox + dimension;
        }
      exitCounter ++;

      }
    }

    public void addLeftInvestedL(byte lSize, int positionBox){
      byte exitCounter = 0;
      boolean verifyWay = false;
      int pivot = positionBox;

      while (exitCounter < lSize){
        if (!verifyWay){
          boxes[pivot].setStatus(STATUS_FULL);
          pivot--;
        }
        else {
          boxes[pivot].setStatus(STATUS_FULL);
          pivot += dimension;
        }

        if (exitCounter == lSize / 2){
          verifyWay = true;
          pivot = positionBox + dimension;
        }
        exitCounter++;
      }
    }

    public void addPiece(String typePiece, byte sizePiece,int positionBox){
      //Despues de que se evalue si la pieza cabe, se llena las posiciones requeridas.
      if (typePiece == "SQUARE") addSquare(sizePiece, positionBox);
      else if (typePiece == "VERTICAL LINE") addLine(sizePiece, positionBox, 2);
      else if (typePiece == "HORIZONTAL LINE") addLine(sizePiece, positionBox, 1);
      else if (typePiece == "L") addL(sizePiece, positionBox);
      else if (typePiece == "L LEFT") addLeftL(sizePiece, positionBox);
      else if (typePiece == "L INVESTED") addInvestedL(sizePiece, positionBox);
      else  addLeftInvestedL (sizePiece, positionBox);
    }
}
