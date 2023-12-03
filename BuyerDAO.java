package com.shop.dao;

import com.shop.models.Buyer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAO {

    private Connection connection;

    public BuyerDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Buyer> getAllBuyers() {
        List<Buyer> buyers = new ArrayList<>();
        String query = "SELECT * FROM buyers";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Buyer buyer = new Buyer();
                buyer.setBuyerId(resultSet.getInt("buyer_id"));
                buyer.setName(resultSet.getString("name"));
                buyer.setEmail(resultSet.getString("email"));
                buyers.add(buyer);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }

        return buyers;
    }

    public void addBuyer(Buyer buyer) {
        String query = "INSERT INTO buyers (name, email) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, buyer.getName());
            statement.setString(2, buyer.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void updateBuyer(Buyer buyer) {
        String query = "UPDATE buyers SET name = ?, email = ? WHERE buyer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, buyer.getName());
            statement.setString(2, buyer.getEmail());
            statement.setInt(3, buyer.getBuyerId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void deleteBuyer(int buyerId) {
        String query = "DELETE FROM buyers WHERE buyer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, buyerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }
}
