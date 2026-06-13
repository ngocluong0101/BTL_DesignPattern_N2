package com.clothingstore.component;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DivComponent extends JPanel {

    public DivComponent(String title, Runnable onClick) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(100, 30));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBackground(Color.LIGHT_GRAY);
                setBorder(BorderFactory.createLineBorder(null));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (onClick != null) {
                    onClick.run();
                }
            }
        });


    }
}