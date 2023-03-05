package com.FlooringMastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {

    /**Declaration of variables*/
    private int orderNumber;        //use for Map key
    private String customerName;
    private Tax state;
    private Product productType;
    private BigDecimal area;
    private LocalDate orderDate;
    private OrderCal orderCal;



    //Constructors

    /**Constructor.
     * Creates a plain Order Object
     */
    public Order() {
        this.state = new Tax();
        this.productType = new Product();
    }

    /**Constructor.
     *For when only the names and no the full object is known for Tax and Product.
     * This will be used for creating new Orders
     * @param customerName Customer Name
     * @param state state
     * @param productType Product Type
     * @param area Area
     * @param orderDate Order Date
     */
    public Order(String customerName, Tax state,
                 Product productType, BigDecimal area,
                 LocalDate orderDate) {

        this.customerName = customerName;
        this.state =  state;
        this.productType = productType;
        this.area = area;
        this.orderDate = orderDate;

        configOrderCal();
    }

    /**Constructor.
     *For when the order number is already established.
     * This will be used for reading Orders or editing orders
     * @param orderNumber order number
     * @param customerName Customer Name
     * @param state State
     * @param productType Product Type
     * @param area Area
     * @param orderDate Order Date
     */
    public Order(int orderNumber, String customerName,
                 Tax state, Product productType,
                 BigDecimal area, LocalDate orderDate) {

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.orderDate = orderDate;

    }


    //Getters

    public int getOrderNumber() {
        return this.orderNumber;
    }
    public String getCustomerName() {
        return this.customerName;
    }
    public Tax getState() {
        return this.state;
    }
    public Product getProductType() {
        return this.productType;
    }
    public BigDecimal getArea() {
        return this.area;
    }
    public LocalDate getOrderDate() {
        return this.orderDate;
    }  ///<-add a print tto string one maybe?

    public OrderCal getOrderCal() {
        return this.orderCal;
    }


    //Setters


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setState(Tax state) {
        this.state = state;
    }
    public void setProductType(Product productType) {
        this.productType = productType;
    }
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    public void setOrderNumber(int orderNumber){
        this.orderNumber = orderNumber;
    }




    /**To calculate the Order info when a new Product type, state or Area are added to order*/
    public void configOrderCal(){
        this.orderCal = new OrderCal(this.area,this.productType,this.state);
    }


    /**for testing*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber && Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(productType, order.productType) && Objects.equals(area, order.area) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderCal, order.orderCal);
    }

    /**for testing*/
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerName, state, productType, area, orderDate, orderCal);
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state.toString() + '\'' +
                ", productType='" + productType.toString() + '\'' +
                ", area=" + area +
                ", orderDate=" + orderDate +
                ", orderInfo=" + orderCal.toString() +
                '}';
    }
}
