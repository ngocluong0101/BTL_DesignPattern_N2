package com.clothingstore.controller;

import com.clothingstore.model.Event;
import com.clothingstore.observe.eventObserve.EventManager;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.view.promotion.EventView;


import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventController {
    private final EventView view;
    private final EventManager eventManager;

    public EventController() {
        view = new EventView();
        eventManager = new EventManager();
        init();
        view.setVisible(true);
    }

    private void init() {
        view.getBtnAddEvent().addActionListener(e -> {
            try {
                String name = view.getTxtName().getText().trim();
                String start = view.getTxtStartDate().getText().trim();
                String end = view.getTxtEndDate().getText().trim();
                String content = view.getTxtContent().getText().trim();
                int adminId = Session.getInstance().getId();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = sdf.parse(start);
                Date endDate = sdf.parse(end);

                Event event = new Event(name, startDate, endDate, content, adminId);

                eventManager.addEvent(event);

                JOptionPane.showMessageDialog(view, "Thêm sự kiện thành công và đã gửi thông báo.");
                clearForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void clearForm() {
        view.getTxtName().setText("");
        view.getTxtStartDate().setText("");
        view.getTxtEndDate().setText("");
        view.getTxtContent().setText("");
        view.getTxtAdminId().setText("");
    }
}