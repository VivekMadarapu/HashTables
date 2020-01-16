import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Open {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner file = new Scanner(new File("open.dat"));

        List<List<String>> fileData = new ArrayList<>();

        List<String> duplicates = new ArrayList<>();
        List<String> used = new ArrayList<>();
        int x = -1;
        while(file.hasNext()){
            String nL = file.nextLine();
            if(nL.equals("0")){
                break;
            }
            else if(nL.equals("1")){
                HashMap<String, Integer> groups = new HashMap<>();
                for (List<String> fileDatum : fileData) {
                    if (fileDatum.size() == 0) {
                        System.out.println();
                    } else {
                        groups.put(fileDatum.get(0), fileDatum.size()-1);
                    }
                }
                List<Map.Entry<String, Integer>> groupList = new ArrayList<>(groups.entrySet());
                sort(groupList);
                for (int i = groupList.size()-1; i >= 0; i--) {
                    System.out.println(groupList.get(i).getKey() + " " + groupList.get(i).getValue());
                }
                System.out.println();
                fileData.clear();
                used.clear();
                duplicates.clear();
                x = -1;
            }
            else if(nL.equals(nL.toUpperCase())){
                fileData.add(new ArrayList<>());
                x++;
                fileData.get(x).add(nL);
            }
            else {
                if(!fileData.get(x).contains(nL) && !duplicates.contains(nL)){
                    if(!used.contains(nL)) {
                        fileData.get(x).add(nL);
                        used.add(nL);
                    }
                    else{
                        for (List<String> stringList : fileData) {
                            stringList.remove(nL);
                        }
                        duplicates.add(nL);
                    }
                }
            }
        }
    }

    public static void sort(List<Map.Entry<String, Integer>> list){
        int n = list.size();
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++){
                if (list.get(j).getValue() < list.get(min_idx).getValue()) {
                    min_idx = j;
                }
                else if(list.get(j).getValue().equals(list.get(min_idx).getValue())){
                    if(list.get(j).getKey().compareTo(list.get(min_idx).getKey()) > 0){
                        min_idx = j;
                    }
                }
            }
            Map.Entry<String, Integer> temp = list.get(min_idx);
            list.set(min_idx, list.get(i));
            list.set(i, temp);
        }
    }

}