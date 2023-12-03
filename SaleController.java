package com.shop.controllers;

import com.shop.dao.SaleDAO;
import com.shop.models.Sale;
import com.shop.util.DatabaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sales")
public class SaleController extends HttpServlet {
    private Connection connection;
    private SaleDAO saleDAO;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseUtil.getConnection();
            saleDAO = new SaleDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Sale> sales = saleDAO.getAllSales();
        request.setAttribute("sales", sales);
        request.getRequestDispatcher("/sales.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));

        if (productId > 0 && buyerId > 0) {
            Sale sale = new Sale();
            sale.setProductId(productId);
            sale.setBuyerId(buyerId);

            saleDAO.addSale(sale);
            response.sendRedirect(request.getContextPath() + "/sales");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int saleId = Integer.parseInt(request.getParameter("saleId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));

        if (productId > 0 && buyerId > 0) {
            Sale sale = new Sale();
            sale.setSaleId(saleId);
            sale.setProductId(productId);
            sale.setBuyerId(buyerId);

            saleDAO.updateSale(sale);
            response.sendRedirect(request.getContextPath() + "/sales");
        } else {
            request.setAttribute("errorMessage", "Invalid data provided");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int saleId = Integer.parseInt(request.getParameter("saleId"));

        saleDAO.deleteSale(saleId);
        response.sendRedirect(request.getContextPath() + "/sales");
    }
}
