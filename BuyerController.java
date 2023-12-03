package com.shop.controllers;

import com.shop.dao.BuyerDAO;
import com.shop.models.Buyer;
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

@WebServlet("/buyers")
public class BuyerController extends HttpServlet {
    private Connection connection;
    private BuyerDAO buyerDAO;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseUtil.getConnection();
            buyerDAO = new BuyerDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            BuyerDAO buyerDAO = new BuyerDAO(connection);
            List<Buyer> buyers = buyerDAO.getAllBuyers();
            request.setAttribute("buyers", buyers);
            request.getRequestDispatcher("/buyers.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching buyers from database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name != null && email != null) {
            try (Connection connection = DatabaseUtil.getConnection()) {
                BuyerDAO buyerDAO = new BuyerDAO(connection);
                Buyer buyer = new Buyer();
                buyer.setName(name);
                buyer.setEmail(email);
                buyerDAO.addBuyer(buyer);
                response.sendRedirect(request.getContextPath() + "/buyers");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error adding buyer to database");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            try (Connection connection = DatabaseUtil.getConnection()) {
                BuyerDAO buyerDAO = new BuyerDAO(connection);
                Buyer buyer = new Buyer();
                buyer.setBuyerId(buyerId);
                buyer.setName(name);
                buyer.setEmail(email);
                buyerDAO.updateBuyer(buyer);
                response.sendRedirect(request.getContextPath() + "/buyers");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error updating buyer in database");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Invalid data provided");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));

        try (Connection connection = DatabaseUtil.getConnection()) {
            BuyerDAO buyerDAO = new BuyerDAO(connection);
            buyerDAO.deleteBuyer(buyerId);
            response.sendRedirect(request.getContextPath() + "/buyers");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting buyer from database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
