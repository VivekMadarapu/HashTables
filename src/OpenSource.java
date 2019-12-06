import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OpenSource {

    public static void main(String[] args) throws FileNotFoundException {

        HashMap groups = new HashMap();

        Scanner file = new Scanner(new File("open.dat"));

        List<String> fileData = new ArrayList<>();
        while(file.hasNext()){
            fileData.add(file.nextLine());
        }

        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i);
            if(line.equals("0")){
                break;
            }
            else if(line.equals("1")){
                System.out.println();
            }
            else if(line.equals(line.toUpperCase())){
                List<String> members = new ArrayList<>();
                String group = line;
                i++;
                String id = fileData.get(i);
                while (id.equals(id.toLowerCase())){
                    if(!members.contains(id)) {
                        members.add(id);
                    }
                    i++;
                    id = fileData.get(i);
                }
                i--;
                groups.put(group, members.toArray());
            }
        }
        System.out.println(groups);


    }


}
