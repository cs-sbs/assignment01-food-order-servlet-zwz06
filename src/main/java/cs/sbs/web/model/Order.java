package cs.sbs.web.model;

// 订单模型，与作业要求一致，包含必要的Getter
public class Order {
    private int id;
    private String customer;
    private String food;
    private int quantity;

    public Order(int id, String customer, String food, int quantity) {
        this.id = id;
        this.customer = customer;
        this.food = food;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }
}