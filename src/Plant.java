import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Plant {

    private static void computeTreePercentages(List<String> list){
        Collections.sort(list);
        HashMap<String, Integer> treeFreq = new LinkedHashMap<>();
        String cur = list.get(0);
        int curNum = 1;
        for (int j = 1; j < list.size(); j++) {
            String tree = list.get(j);
            if(!tree.equals(cur)){
                treeFreq.put(cur, curNum);
                cur = tree;
                curNum = 1;
            }
            else{
                curNum++;
            }
        }
        treeFreq.put(cur, curNum);
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        for (Map.Entry<String, Integer> entry : treeFreq.entrySet()) {
            System.out.println(entry.getKey() + " " + df.format(100.0*entry.getValue()/ list.size()));
        }
    }

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
                computeTreePercentages(trees);
                System.out.println();
                trees.clear();
            }
            else {
                trees.add(line);
            }
        }
        computeTreePercentages(trees);
    }
}
