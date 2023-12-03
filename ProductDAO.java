package com.shop.dao;

import com.shop.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAllProducts()  throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }

        return products;
    }

    public void addProduct(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getProductId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }

    public void deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Ovde možete dodati bolji način rukovanja izuzecima
        }
    }
}