import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Hangman {
    //this function will return the hangman image based on the wrong guess;
     static String gethangman(int wrongguess){

        return switch(wrongguess){
            case 0 -> """
            +---+
            |   |
                |
                |
                |
                |
            =========""";
            case 1 -> """
            +---+
            |   |
            O   |
                |
                |
                |
            =========""";
            case 2 -> """
            +---+
            |   |
            O   |
            |   |
                |
                |
            =========""";
            case 3 -> """
            +---+
            |   |
            O   |
           /|   |
                |
                |
            =========""";
            case 4 -> """
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
            =========""";
            case 5 -> """
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
            =========""";
            case 6 -> """
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
            =========""";
            default -> "";
        };

     }
    public static void main(String[] args) {
        //reding the words from the file name as words.txt
        ArrayList<String> words = new ArrayList<>();
        try {
      File myObj = new File("word.txt");
      Scanner myReader = new Scanner(myObj);
         while (myReader.hasNextLine()) {
        words.add(myReader.nextLine());
      
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

       String word = words.get((int)(Math.random()*words.size()));
       Scanner scanner = new Scanner(System.in);
       ArrayList<Character> wordstate= new ArrayList<>();
       int wrongguess=0;
       for(int i=0; i<word.length();i++){
        wordstate.add('_');
       }
       System.out.println("**************");
       System.out.println("welcome to Hangman Game");
       System.out.println("**************");
      while(wrongguess<6){
        System.out.println(gethangman(wrongguess));
        System.out.print("Word:");
        for(char c:wordstate){
            System.out.print(c+" ");
        }
        System.out.println();
        System.out.println("Guess the letter in the word");
        char guess=   scanner.next().toLowerCase().charAt(0);
       if(word.indexOf(guess)>=0){
        for(int i=0;i<word.length(); i++){
            if(word.charAt(i)==guess){
                wordstate.set(i,guess);
            }
        }
        if(!wordstate.contains('_')){
            System.out.println("You won the game");
            System.out.println("the correct word was::"+word);
            break;
        }

       }
       else{
           wrongguess++;
       }


         
      }
      if(wrongguess==6){
            System.out.println(gethangman(wrongguess));
          System.out.println("You lost the game");
          System.out.println("the correct word was::"+word);
      }



    }
}