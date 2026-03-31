package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MenuListServlet extends HttpServlet {
    private static final List<MenuItem> MENU_ITEMS = new ArrayList<>();

    static {
        MENU_ITEMS.add(new MenuItem("Fried Rice", 8));
        MENU_ITEMS.add(new MenuItem("Fried Noodles", 9));
        MENU_ITEMS.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");
        int count = 0;
        int index = 1;

        out.println("Menu List:");
        out.println();

        // 搜索逻辑
        for (MenuItem item : MENU_ITEMS) {
            if (name == null || item.getName().toLowerCase().contains(name.toLowerCase())) {
                out.printf("%d. %s - $%d%n", index++, item.getName(), item.getPrice());
                count++;
            }
        }

        // 如果没有找到任何食物
        if (count == 0) {
            out.println("No items found");
        }
    }
}