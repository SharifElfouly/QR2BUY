package swp.shafou.qr2buy.POJO;

/**
 *------------------------------------*
 * - Klasse erstellt. 09.02.17 ELFOULY
 *------------------------------------*
 *
 * POJO Bestellungs Kopf Klasse
 */
public class OrderHeader {

    private int orderID;
    private String orderCreationDate;
    private int orderCustomerID;
    private double orderPriceNet;
    private double orderPriceGross;
    private String orderStatus;

    public OrderHeader() {

    }

    public OrderHeader(int orderID, String orderCreationDate, int orderCustomerID, double orderPriceNet, double orderPriceGross, String orderStatus) {
        this.orderID = orderID;
        this.orderCreationDate = orderCreationDate;
        this.orderCustomerID = orderCustomerID;
        this.orderPriceNet = orderPriceNet;
        this.orderPriceGross = orderPriceGross;
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(String orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public int getOrderCustomerID() {
        return orderCustomerID;
    }

    public void setOrderCustomerID(int orderCustomerID) {
        this.orderCustomerID = orderCustomerID;
    }

    public double getOrderPriceNet() {
        return orderPriceNet;
    }

    public void setOrderPriceNet(double orderPriceNet) {
        this.orderPriceNet = orderPriceNet;
    }

    public double getOrderPriceGross() {
        return orderPriceGross;
    }

    public void setOrderPriceGross(double orderPriceGross) {
        this.orderPriceGross = orderPriceGross;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHeader that = (OrderHeader) o;

        if (orderID != that.orderID) return false;
        if (orderCustomerID != that.orderCustomerID) return false;
        if (Double.compare(that.orderPriceNet, orderPriceNet) != 0) return false;
        if (Double.compare(that.orderPriceGross, orderPriceGross) != 0) return false;
        if (orderCreationDate != null ? !orderCreationDate.equals(that.orderCreationDate) : that.orderCreationDate != null)
            return false;
        return orderStatus != null ? orderStatus.equals(that.orderStatus) : that.orderStatus == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = orderID;
        result = 31 * result + (orderCreationDate != null ? orderCreationDate.hashCode() : 0);
        result = 31 * result + orderCustomerID;
        temp = Double.doubleToLongBits(orderPriceNet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(orderPriceGross);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
