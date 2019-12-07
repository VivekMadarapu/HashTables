import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plant {


    public static void main(String[] args) throws FileNotFoundException {

        Scanner file = new Scanner(new File("plant.dat"));
        List<String> fileData = new ArrayList<>();

        while(file.hasNext()){
            fileData.add(file.nextLine());
        }

        List<String> trees = new ArrayList<>();
        for (int i = 2; i < fileData.size(); i++) {
            String line = fileData.get(i);
            if(line.equals("")){
                System.out.println(trees);
                trees.clear();
            }
            else {
                trees.add(line);
            }
        }
        System.out.println(trees);

    }

}
