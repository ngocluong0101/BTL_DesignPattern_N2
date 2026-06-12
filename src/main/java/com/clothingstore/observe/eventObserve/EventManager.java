package com.clothingstore.observe.eventObserve;

import com.clothingstore.dao.ConnectionManager;
import com.clothingstore.model.Customer;
import com.clothingstore.model.Event;
import com.clothingstore.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventManager implements Subject {
    private List<Observer> observers;

    public EventManager() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        List<Customer> subscribedCustomers = getSubscribedCustomers();
        for (Customer customer : subscribedCustomers) {
            String message = "Sự kiện mới: " + event.getName() + ". Nội dung: " + event.getContent();
            Notification notification = new Notification(customer.getId(), event.getId(), message, new Date(), "sent");
            saveNotification(notification);
            for (Observer observer : observers) {
                observer.update(notification);
            }
        }
    }

    public void addEvent(Event event) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "INSERT INTO events (name_events, start_date, end_date, content, id_admin) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, event.getName());
            stmt.setDate(2, new java.sql.Date(event.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(event.getEndDate().getTime()));
            stmt.setString(4, event.getContent());
            stmt.setInt(5, event.getAdminId());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                event.setId(rs.getInt(1));
            }

            notifyObservers(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    private List<Customer> getSubscribedCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT c.id_customers, c.full_name, c.phone " +
                "FROM customers c " +
                "JOIN customer_event_subscriptions ces ON c.id_customers = ces.id_customer";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id_customers"),
                        rs.getString("full_name"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return customers;
    }

    private void saveNotification(Notification notification) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO notifications (id_customer, id_event, message, sent_at, status) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, notification.getCustomerId());
            stmt.setInt(2, notification.getEventId());
            stmt.setString(3, notification.getMessage());
            stmt.setTimestamp(4, new Timestamp(notification.getSentAt().getTime()));
            stmt.setString(5, notification.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }
}