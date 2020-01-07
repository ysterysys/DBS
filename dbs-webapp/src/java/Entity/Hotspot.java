package Entity;

public class Hotspot {
    //Attributes
    private String apId;
    private String deploymentType;
    private String locationType;
    private String locationName;
    private String streetAddress;
    private String block;
    private String level;
    private String description;
    private String postalCode;
    private String operator;
    private String macAddress;
    private String clientsOrUtilization;
    
    //Constructor
    public Hotspot(String apId, String deploymentType, String locationType, String locationName, String streetAddress, String block, String level, String description, String postalCode, String operator, String macAddress, String clientsOrUtilization){
        this.apId = apId;
        this.deploymentType = deploymentType;
        this.locationType = locationType;
        this.locationName = locationName;
        this.streetAddress = streetAddress;
        this.block = block;
        this.level = level;
        this.description = description;
        this.postalCode = postalCode;
        this.operator = operator;
        this.macAddress = macAddress;
        this.clientsOrUtilization = clientsOrUtilization;
    }
    
    //Methods
    public String getApId() {
        return apId;
    }

    public String getDeploymentType() {
        return deploymentType;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getBlock() {
        return block;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getOperator() {
        return operator;
    }

    
    public String getMacAddress() {
        return macAddress;
    }

    public String getClientsOrUtilization() {
        return clientsOrUtilization;
    }
}
