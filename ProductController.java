package com.shop.controllers;

import com.shop.dao.ProductDAO;
import com.shop.models.Product;
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

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private Connection connection;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseUtil.getConnection();
            productDAO = new ProductDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching products from database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        if (name != null) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            productDAO.addProduct(product);
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        if (name != null) {
            Product product = new Product();
            product.setProductId(productId);
            product.setName(name);
            product.setPrice(price);
            productDAO.updateProduct(product);
            response.sendRedirect(request.getContextPath() + "/products");
        } else {
            request.setAttribute("errorMessage", "Invalid data provided");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));

        productDAO.deleteProduct(productId);
        response.sendRedirect(request.getContextPath() + "/products");
    }
}
