package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            out.println("Error: Order ID is required");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(path.substring(1));
        } catch (NumberFormatException e) {
            out.println("Error: Invalid Order ID");
            return;
        }

        // 查找订单
        Order found = null;
        synchronized (OrderCreateServlet.class) {
            for (Order o : OrderCreateServlet.orderList) {
                if (o.getId() == orderId) {
                    found = o;
                    break;
                }
            }
        }

        if (found == null) {
            out.println("Error: Order not found");
            return;
        }

        // 输出你真实创建的内容
        out.println("Order Detail");
        out.println("Order ID: " + found.getId());
        out.println("Customer: " + found.getCustomer());
        out.println("Food: " + found.getFood());
        out.println("Quantity: " + found.getQuantity());
    }
}