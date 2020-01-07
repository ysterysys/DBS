/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author jeremylimys93
 */
public class AlertsSettings {
    
    private String username;
    private String categoryDate;
    private String startDate;
    private String endDate;
    private String lessThanConnectedClients;
    private String moreThanConnectedClients;
    private String lessThanUtilization;
    private String moreThanUtilization;
    private String outlierUtilizationRates;
    private String outlierConnectedClients;
    
    public AlertsSettings (String username, String categoryDate, String startDate, String endDate, String lessThanConnectedClients, String moreThanConnectedClients, 
        String lessThanUtilization, String moreThanUtilization, String outlierUtilizationRates, String outlierConnectedClients) {
        this.username = username;
        this.categoryDate = categoryDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lessThanConnectedClients = lessThanConnectedClients;
        this.moreThanConnectedClients = moreThanConnectedClients;
        this.lessThanUtilization = lessThanUtilization;
        this.moreThanUtilization = moreThanUtilization;
        this.outlierUtilizationRates = outlierUtilizationRates;
        this.outlierConnectedClients = outlierConnectedClients;
    }      
    //getters
    public String getUsername() {
        return username;
    }

    public String getCategoryDate() {
        return categoryDate;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }

    public String getLessThanConnectedClients() {
        return lessThanConnectedClients;
    }

    public String getMoreThanConnectedClients() {
        return moreThanConnectedClients;
    }

    public String getLessThanUtilization() {
        return lessThanUtilization;
    }

    public String getMoreThanUtilization() {
        return moreThanUtilization;
    }

    public String getOutlierUtilizationRates() {
        return outlierUtilizationRates;
    }

    public String getOutlierConnectedClients() {
        return outlierConnectedClients;
    }
    
    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setCategoryDate(String categoryDate) {
        this.categoryDate = categoryDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLessThanConnectedClients(String lessThanConnectedClients) {
        this.lessThanConnectedClients = lessThanConnectedClients;
    }

    public void setMoreThanConnectedClients(String moreThanConnectedClients) {
        this.moreThanConnectedClients = moreThanConnectedClients;
    }

    public void setLessThanUtilization(String lessThanUtilization) {
        this.lessThanUtilization = lessThanUtilization;
    }

    public void setMoreThanUtilization(String moreThanUtilization) {
        this.moreThanUtilization = moreThanUtilization;
    }

    public void setOutlierUtilizationRates(String outlierUtilizationRates) {
        this.outlierUtilizationRates = outlierUtilizationRates;
    }

    public void setOutlierConnectedClients(String outlierConnectedClients) {
        this.outlierConnectedClients = outlierConnectedClients;
    }
            
            
            

    
}
