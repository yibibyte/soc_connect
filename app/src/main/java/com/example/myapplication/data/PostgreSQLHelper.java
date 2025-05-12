package com.example.myapplication.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/android_items_db";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // CREATE operation
    public void createItem(Item item) {
        new Thread(() -> {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "INSERT INTO items(name, description) VALUES (?, ?)")) {

                pstmt.setString(1, item.getName());
                pstmt.setString(2, item.getDescription());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // READ operation (single item)
    public void getItem(int id, DatabaseCallback<Item> callback) {
        new Thread(() -> {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "SELECT * FROM items WHERE id = ?")) {

                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    Item item = new Item(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    callback.onSuccess(item);
                } else {
                    callback.onFailure("Item not found");
                }
            } catch (SQLException e) {
                callback.onFailure(e.getMessage());
            }
        }).start();
    }

    // READ operation (all items)
    public void getAllItems(DatabaseCallback<List<Item>> callback) {
        new Thread(() -> {
            List<Item> items = new ArrayList<>();

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "SELECT * FROM items");
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    items.add(new Item(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    ));
                }
                callback.onSuccess(items);
            } catch (SQLException e) {
                callback.onFailure(e.getMessage());
            }
        }).start();
    }

    // UPDATE operation
    public void updateItem(Item item) {
        new Thread(() -> {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "UPDATE items SET name = ?, description = ? WHERE id = ?")) {

                pstmt.setString(1, item.getName());
                pstmt.setString(2, item.getDescription());
                pstmt.setInt(3, item.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // DELETE operation
    public void deleteItem(int id) {
        new Thread(() -> {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "DELETE FROM items WHERE id = ?")) {

                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public interface DatabaseCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }
}