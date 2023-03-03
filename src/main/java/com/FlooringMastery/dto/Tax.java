package com.FlooringMastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**Holds info on state Tax
 */
public class Tax {

    /**Declaration of variables*/
    private String stateAbbreviation;   //use for Map Key
    private String stateName;
    private BigDecimal taxRate;


    /**Constructor for tax info
     * @param stateAbbreviation stateAbbreviation
     * @param stateName stateName
     * @param taxRate taxRate
     */
    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public Tax() {
    }

    //Getters

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }
    public String getStateName() {
        return stateName;
    }
    public BigDecimal getTaxRate() {
        return taxRate;
    }


    //Setters


    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }



    /**for testing*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(stateAbbreviation, tax.stateAbbreviation) && Objects.equals(stateName, tax.stateName) && Objects.equals(taxRate, tax.taxRate);
    }

    /**for testing*/
    @Override
    public int hashCode() {
        return Objects.hash(stateAbbreviation, stateName, taxRate);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "stateAbbreviation='" + stateAbbreviation + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}
