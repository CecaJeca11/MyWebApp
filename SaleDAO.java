package com.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.shop.models.Sale;

public class SaleDAO {

    private Connection connection;

    public SaleDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM sales";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Sale sale = new Sale();
                sale.setSaleId(resultSet.getInt("sale_id"));
                sale.setProductId(resultSet.getInt("product_id"));
                sale.setBuyerId(resultSet.getInt("buyer_id"));
                sale.setSaleDate(resultSet.getDate("sale_date").toLocalDate());
                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }

        return sales;
    }

    public void addSale(Sale sale) {
        String query = "INSERT INTO sales (product_id, buyer_id, sale_date) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sale.getProductId());
            statement.setInt(2, sale.getBuyerId());
            statement.setDate(3, java.sql.Date.valueOf(sale.getSaleDate()));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void updateSale(Sale sale) {
        String query = "UPDATE sales SET product_id = ?, buyer_id = ?, sale_date = ? WHERE sale_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sale.getProductId());
            statement.setInt(2, sale.getBuyerId());
            statement.setDate(3, java.sql.Date.valueOf(sale.getSaleDate()));
            statement.setInt(4, sale.getSaleId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void deleteSale(int saleId) {
        String query = "DELETE FROM sales WHERE sale_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, saleId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }
}
