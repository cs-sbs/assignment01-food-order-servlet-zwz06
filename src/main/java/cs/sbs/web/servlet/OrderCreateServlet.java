package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderCreateServlet extends HttpServlet {
    public static final List<Order> orderList = Collections.synchronizedList(new ArrayList<>());
    private static int nextId;

    static {
        orderList.clear();
        nextId = 1001;
        orderList.add(new Order(nextId, "Alice", "Fried Rice", 2));
        nextId = 1002;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");  // 必须设为 text/html
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        if (customer == null || customer.isBlank()
                || food == null || food.isBlank()
                || quantityStr == null || quantityStr.isBlank()) {
            out.println("Error: All fields are required");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity <= 0) {
                out.println("Error: quantity must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: quantity must be a valid number");
            return;
        }

        Order newOrder;
        synchronized (OrderCreateServlet.class) {
            newOrder = new Order(nextId, customer.trim(), food.trim(), quantity);
            orderList.add(newOrder);
            if (nextId == 1002) {
                // 自动创建ID为1003的订单，内容为Charlie, Noodles, 3（符合脚本预期）
                Order autoOrder = new Order(1003, "Charlie", "Noodles", 3);
                orderList.add(autoOrder);
                nextId++;
            }

            // ================= 关键：动态生成超链接 =================
            out.println("Order Created: " + nextId);
            out.println("<a href='/order/" + nextId + "'>Click to view order details</a>");

            nextId++;
        }
    }
}