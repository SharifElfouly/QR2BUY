package swp.shafou.qr2buy.POJO;

/**
 *------------------------------------*
 * - Klasse erstellt. 06.02.17 ELFOULY
 *------------------------------------*
 *
 * POJO Bestellungs Positions Klasse
 *
 *--------------WARNUNG--------------*
 * - Attribute müssen den gleichen Namen wie in der Datenbank haben
 * --------------WARNUNG--------------*
 */
public class OrderPosition {

    private int orderCounter;
    private int orderForiegnKey;
    private int orderArticleID;
    private int orderQuantity;
    private double oderPriceNet;
    private double orderPriceGross;
    private String orderDate;
    private String orderStatus;

    public OrderPosition() {

    }

    public OrderPosition(int orderCounter, int orderForiegnKey, int orderArticleID, int orderQuantity, double oderPriceNet, double orderPriceGross, String orderDate, String orderStatus) {
        this.orderCounter = orderCounter;
        this.orderForiegnKey = orderForiegnKey;
        this.orderArticleID = orderArticleID;
        this.orderQuantity = orderQuantity;
        this.oderPriceNet = oderPriceNet;
        this.orderPriceGross = orderPriceGross;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPosition orderPosition = (OrderPosition) o;

        if (orderCounter != orderPosition.orderCounter) return false;
        if (orderForiegnKey != orderPosition.orderForiegnKey) return false;
        if (orderArticleID != orderPosition.orderArticleID) return false;
        if (orderQuantity != orderPosition.orderQuantity) return false;
        if (Double.compare(orderPosition.oderPriceNet, oderPriceNet) != 0) return false;
        if (Double.compare(orderPosition.orderPriceGross, orderPriceGross) != 0) return false;
        if (orderDate != null ? !orderDate.equals(orderPosition.orderDate) : orderPosition.orderDate != null)
            return false;
        return orderStatus != null ? orderStatus.equals(orderPosition.orderStatus) : orderPosition.orderStatus == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = orderCounter;
        result = 31 * result + orderForiegnKey;
        result = 31 * result + orderArticleID;
        result = 31 * result + orderQuantity;
        temp = Double.doubleToLongBits(oderPriceNet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(orderPriceGross);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }

    public int getOrderCounter() {
        return orderCounter;
    }

    public void setOrderCounter(int orderCounter) {
        this.orderCounter = orderCounter;
    }

    public int getOrderForiegnKey() {
        return orderForiegnKey;
    }

    public void setOrderForiegnKey(int orderForiegnKey) {
        this.orderForiegnKey = orderForiegnKey;
    }

    public int getOrderArticleID() {
        return orderArticleID;
    }

    public void setOrderArticleID(int orderArticleID) {
        this.orderArticleID = orderArticleID;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getOderPriceNet() {
        return oderPriceNet;
    }

    public void setOderPriceNet(double oderPriceNet) {
        this.oderPriceNet = oderPriceNet;
    }

    public double getOrderPriceGross() {
        return orderPriceGross;
    }

    public void setOrderPriceGross(double orderPriceGross) {
        this.orderPriceGross = orderPriceGross;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
