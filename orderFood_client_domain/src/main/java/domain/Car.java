package domain;

public class Car {
    private String id;//uuid
    private String productId;//产品id
    private int productCount;//产品数量
    private String memberId;//会员id
    private Product product;//产品
    private String ordersId;//订单id
    private int carStatus;//购物车状态 1 已支付/0 未支付
    private String carStatusStr;//购物车状态字符串

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarStatusStr() {
        return carStatus==1?"已支付":"未支付";
    }

    public void setCarStatusStr(String carStatusStr) {
        this.carStatusStr = carStatusStr;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
