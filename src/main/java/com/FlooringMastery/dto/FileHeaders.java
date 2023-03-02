package com.FlooringMastery.dto;

/**Enums holding the header of each type of .txt file
 * and the number of columns each one has. The 4 types are:
 * Taxes.txt, Products.txt, Orders, and Backup
 */
public enum FileHeaders {
    TAX("State,StateName,TaxRate"),
    PRODUCT("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot"),
    ORDER(("OrderNumber,CustomerName," +
            "State,TaxRate,ProductType," +
            "Area,CostPerSquareFoot," +
            "LaborCostPerSquareFoot," +
            "MaterialCost,LaborCost," +
            "Tax,Total")),
    BACKUP("OrderNumber,CustomerName," +
            "State,TaxRate,ProductType," +
            "Area,CostPerSquareFoot," +
            "LaborCostPerSquareFoot," +
            "MaterialCost,LaborCost," +
            "Tax,Total,OrderDate");





    private String header;

    private int columns;

    FileHeaders(String header){
        this.header = header;
        this.columns = this.header.split(",").length;
    }

    /**Returns the header of the given .txt file type (TAX, PRODUCT, ORDER or BACKUP)
     * @return header
     */
    public String getHeader() {
        return header;
    }

    /**Returns the number of columns in header of the given .txt file type (TAX, PRODUCT, ORDER or BACKUP)
     * @return number of columns in header
     */
    public int getColumns() {
        return columns;
    }
}
