import java.util.ArrayList;

public class VIN {

    private String vin;

    public VIN() {
        vin = "00000000000000000";
    }

    public VIN(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(VIN.class)){
            return false;
        }
        return this.vin.equals(((VIN) o).getVin());

    }

    @Override
    public int hashCode() {
        ArrayList<String> vinHashes = new ArrayList<String>();
        int hashesIndex = -1;

        for (int i = 0; i < vin.getBytes().length;i++){
            if(i%4==0){
                vinHashes.add("");
                hashesIndex++;
            }
            vinHashes.set(hashesIndex, vinHashes.get(hashesIndex) + Integer.toBinaryString(vin.getBytes()[i]));
        }
        int hashCode = 0;
        for (String s:vinHashes){
            hashCode += Integer.parseInt(s, 2);
        }

        return hashCode;
    }


}
