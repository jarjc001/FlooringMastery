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

    public static int overallOrderNumber =1;


    //Constructors


    /**Constructor.
     *For when the order number is not already established. It will give it
     * the next order number available then +1 to  overallOrderNumber.
     * This keeps track of what is the highest Order Number.
     * This will be used for adding new Orders
     * @param customerName Customer Name
     * @param state State
     * @param productType Product Type
     * @param area Area
     * @param orderDate Order Date
     * @param orderCal Object holding the info of order determined by other variables
     */
    public Order(String customerName, Tax state,
                 Product productType, BigDecimal area,
                 LocalDate orderDate, OrderCal orderCal) {

        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.orderDate = orderDate;
        this.orderCal = orderCal;

        configOrderCal();

        this.orderNumber = overallOrderNumber++;
    }

    /**Constructor.
     *For when the order number is already established.
     *If overallOrderNumber is smaller or equal to the given
     * orderNumber, overallOrderNumber will change to become
     * orderNumber+1. This keeps track of what is the highest Order Number.
     * This will be used for reading Orders or editing orders
     * @param orderNumber order number
     * @param customerName Customer Name
     * @param state State
     * @param productType Product Type
     * @param area Area
     * @param orderDate Order Date
     * @param orderCal Object holding the info of order determined by other variables
     */
    public Order(int orderNumber, String customerName,
                 Tax state, Product productType,
                 BigDecimal area, LocalDate orderDate,
                 OrderCal orderCal) {

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.orderDate = orderDate;
        this.orderCal = orderCal;
        configOrderCal();

        if(orderNumber>=overallOrderNumber){
            overallOrderNumber = orderNumber+1;
        }
    }


    //Getters

    public int getOrderNumber() {
        return orderNumber;
    }
    public String getCustomerName() {
        return customerName;
    }
    public Tax getState() {
        return state;
    }
    public Product getProductType() {
        return productType;
    }
    public BigDecimal getArea() {
        return area;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public OrderCal getOrderInfo() {
        return orderCal;
    }


    //Setters


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setState(Tax state) {
        this.state = state;
        configOrderCal();
    }
    public void setProductType(Product productType) {
        this.productType = productType;
        configOrderCal();
    }
    public void setArea(BigDecimal area) {
        this.area = area;
        configOrderCal();
    }

//    public void setOrderInfo(OrderInfo orderInfo) { ///edit so input is order by creating a new one?
//        this.orderInfo = orderInfo;
//    }


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
