package com.FlooringMastery.dto;

/**Enums holding the header of each type of .txt file
 * and the number of columns each one has. The 3 types are:
 * Taxes.txt, Products.txt, and Orders
 */
public enum UnMarshallHeaders {
    TAX("State,StateName,TaxRate"),
    PRODUCT("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot"),
    ORDER(("OrderNumber,CustomerName," +
            "State,TaxRate,ProductType," +
            "Area,CostPerSquareFoot," +
            "LaborCostPerSquareFoot," +
            "MaterialCost,LaborCost," +
            "Tax,Total"));



    private String header;
    private int columns;

    UnMarshallHeaders(String header){
        this.header = header;
        this.columns = this.header.split(",").length;
    }

    /**Returns the header of the given .txt file type (TAX, PRODUCT or ORDER)
     * @return header
     */
    public String getHeader() {
        return header;
    }

    /**Returns the number of columns in header of the given .txt file type (TAX, PRODUCT or ORDER)
     * @return number of columns in header
     */
    public int getColumns() {
        return columns;
    }
}
