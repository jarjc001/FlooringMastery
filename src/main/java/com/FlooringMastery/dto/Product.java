package com.FlooringMastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Holds info of product type */
public class Product {

    /**Declaration of variables*/
    private String productType;         //use for Map key
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;


    /**
     * Constructor product type info
     * @param productType  productType
     * @param costPerSquareFoot costPerSquareFoot
     * @param laborCostPerSquareFoot laborCostPerSquareFoot
     */
    public Product(String productType,
                   BigDecimal costPerSquareFoot,
                   BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public Product() {
    }

    //Getters

    public String getProductType() {
        return this.productType;
    }
    public BigDecimal getCostPerSquareFoot() {
        return this.costPerSquareFoot;
    }
    public BigDecimal getLaborCostPerSquareFoot() {
        return this.laborCostPerSquareFoot;
    }


    //setters

    public void setProductType(String productType) {
        this.productType = productType;
    }
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }


    /**for testing*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) && Objects.equals(costPerSquareFoot, product.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, product.laborCostPerSquareFoot);
    }

    /**for testing*/
    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                '}';
    }
}
