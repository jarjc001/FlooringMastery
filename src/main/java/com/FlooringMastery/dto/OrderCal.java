package com.FlooringMastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**A DTO to hold the order info that is calculated by other variables in Order.
 * To make it easier to read the information*/
public class OrderCal {

    /**Declaration of variables*/
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    /**
     * Constructor.
     * Calculates the variables when created with constructor     *
     * @param area area
     * @param productType Product object
     * @param state Tax object
     */
    public OrderCal(BigDecimal area, Product productType,
                    Tax state) {
        this.materialCost = calculateMaterialCost(area,productType).setScale(2,RoundingMode.HALF_UP);
        this.laborCost = calculateLaborCost(area,productType).setScale(2,RoundingMode.HALF_UP);
        this.tax = calculateTax(this.materialCost,this.laborCost,state).setScale(2,RoundingMode.HALF_UP);
        this.total = calculateTotal(this.materialCost,this.laborCost,this.tax).setScale(2,RoundingMode.HALF_UP);
    }

    //Getters

    public BigDecimal getMaterialCost() {
        return materialCost;
    }
    public BigDecimal getLaborCost() {
        return laborCost;
    }
    public BigDecimal getTax() {
        return tax;
    }
    public BigDecimal getTotal() {
        return total;
    }



    /** Calculates the material cost using:
     * MaterialCost = (Area * CostPerSquareFoot)
     * @param area area
     * @param product Product object
     * @return material cost
     */
    private BigDecimal calculateMaterialCost(BigDecimal area, Product product){
        return area.multiply(product.getCostPerSquareFoot());
    }

    /**Calculates the Labor Cost using:
     *LaborCost = (Area * LaborCostPerSquareFoot)
     * @param area area
     * @param product Product object
     * @return Labor Cost
     */
    private BigDecimal calculateLaborCost(BigDecimal area, Product product){
        return area.multiply(product.getLaborCostPerSquareFoot());
    }

    /**Calculates the Tax using:
     *Tax = (MaterialCost + LaborCost) * (TaxRate/100)
     * @param materialCost material cost
     * @param laborCost Labor Cost
     * @param state Tax object
     * @return Tax
     */
    private BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, Tax state){
        BigDecimal percent = new BigDecimal(0.01);

        BigDecimal tax = state.getTaxRate().multiply(percent);

        return (materialCost.add(laborCost)).multiply(tax);
    }

    /**Calculates the Total cost using:
     *Total = (MaterialCost + LaborCost + Tax)
     * @param materialCost material cost
     * @param laborCost Labor Cost
     * @param tax Tax
     * @return Total cost
     */
    private BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax){
        return materialCost.add(laborCost).add(tax);
    }



    /**for testing*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCal orderCal = (OrderCal) o;
        return  Objects.equals(materialCost, orderCal.materialCost) && Objects.equals(laborCost, orderCal.laborCost) && Objects.equals(tax, orderCal.tax) && Objects.equals(total, orderCal.total);
    }

    /**for testing*/
    @Override
    public int hashCode() {
        return Objects.hash(materialCost, laborCost, tax, total);
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}
