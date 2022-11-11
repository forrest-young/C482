package model;

/**
 * @author Forrest Young
 */
public class InHouse extends Part{
    private int machineId;

    public InHouse(int id, String name, double cost, int stock, int min, int max, int machineId){
        super(id, name, cost, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     *
     * @return the machineId
     */
    public int getMachineId(){
        return machineId;
    }
}
