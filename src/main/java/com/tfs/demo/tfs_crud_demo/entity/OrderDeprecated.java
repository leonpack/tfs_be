package com.tfs.demo.tfs_crud_demo.entity;

//@Entity
//@Table(name = "order")
public class OrderDeprecated {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_id")
//    private int orderId;
//
//    @Column(name = "total_price")
//    private double totalPrice;
//
//    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinColumn(name = "promotionCode")
//    @JsonManagedReference(value = "order-promotion")
//    private Promotion thePromotion;
//
//    //TODO fix this nested resultSet
////    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
////    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
////    @JsonBackReference(value = "customer-order")
////    private Customer theCustomerOrder;
////
////    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
////    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
////    @JsonBackReference(value = "staff-order")
////    private Staff theStaffOrder;
////
////    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
////    @JoinColumn(name = "restaurant_id",referencedColumnName = "restaurant_id")
////    @JsonManagedReference(value = "restaurant-order")
////    private Restaurant theRestaurantOrder;
//
//    @Column(name = "payment_method")
//    private String paymentMethod;
//
//    @Column(name = "delivery_address")
//    private String deliveryAddress;
//
//    @Column(name = "order_date")
//    private Date orderDate;
//
//    @Column(name = "delivery_date")
//    private Date deliveryDate;
//
//    @Column(name = "receive_time")
//    private Date receiveDate;
//
//    @Column(name = "activity_status")
//    private String activity_status;
//
//
//    public Order(){
//
//    }
//
//    public Order(double totalPrice, Promotion thePromotion, Customer theCustomerOrder, Staff theStaffOrder, Restaurant theRestaurantOrder, String paymentMethod, String deliveryAddress, Date orderDate, Date deliveryDate, Date receiveDate, String activity_status) {
//        this.totalPrice = totalPrice;
//        this.thePromotion = thePromotion;
////        this.theCustomerOrder = theCustomerOrder;
////        this.theStaffOrder = theStaffOrder;
////        this.theRestaurantOrder = theRestaurantOrder;
//        this.paymentMethod = paymentMethod;
//        this.deliveryAddress = deliveryAddress;
//        this.orderDate = orderDate;
//        this.deliveryDate = deliveryDate;
//        this.receiveDate = receiveDate;
//        this.activity_status = activity_status;
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//
//    public double getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Promotion getThePromotion() {
//        return thePromotion;
//    }
//
//    public void setThePromotion(Promotion thePromotion) {
//        this.thePromotion = thePromotion;
//    }
//// TODO fix this issues
////    public Customer getTheCustomerOrder() {
////        return theCustomerOrder;
////    }
////
////    public void setTheCustomerOrder(Customer theCustomerOrder) {
////        this.theCustomerOrder = theCustomerOrder;
////    }
////
////    public Staff getTheStaffOrder() {
////        return theStaffOrder;
////    }
////
////    public void setTheStaffOrder(Staff theStaffOrder) {
////        this.theStaffOrder = theStaffOrder;
////    }
////
////    public Restaurant getTheRestaurantOrder() {
////        return theRestaurantOrder;
////    }
////
////    public void setTheRestaurantOrder(Restaurant theRestaurantOrder) {
////        this.theRestaurantOrder = theRestaurantOrder;
////    }
//
//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public String getDeliveryAddress() {
//        return deliveryAddress;
//    }
//
//    public void setDeliveryAddress(String deliveryAddress) {
//        this.deliveryAddress = deliveryAddress;
//    }
//
//    public Date getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Date orderDate) {
//        this.orderDate = orderDate;
//    }
//
//    public Date getDeliveryDate() {
//        return deliveryDate;
//    }
//
//    public void setDeliveryDate(Date deliveryDate) {
//        this.deliveryDate = deliveryDate;
//    }
//
//    public Date getReceiveDate() {
//        return receiveDate;
//    }
//
//    public void setReceiveDate(Date receiveDate) {
//        this.receiveDate = receiveDate;
//    }
//
//    public String getActivity_status() {
//        return activity_status;
//    }
//
//    public void setActivity_status(String activity_status) {
//        this.activity_status = activity_status;
//    }
////TODO fix this issues
////    @Override
////    public String toString() {
////        return "Order{" +
////                "orderId=" + orderId +
////                ", totalPrice=" + totalPrice +
////                ", thePromotion=" + thePromotion +
////                ", theCustomerOrder=" + theCustomerOrder +
////                ", theStaffOrder=" + theStaffOrder +
////                ", theRestaurantOrder=" + theRestaurantOrder +
////                ", paymentMethod='" + paymentMethod + '\'' +
////                ", deliveryAddress='" + deliveryAddress + '\'' +
////                ", orderDate=" + orderDate +
////                ", deliveryDate=" + deliveryDate +
////                ", receiveDate=" + receiveDate +
////                ", activity_status='" + activity_status + '\'' +
////                '}';
////    }
}
