package com.clothingstore.service;

public class SecurityService {
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        // Trong thực tế sẽ dùng bcrypt hoặc thuật toán băm khác
        // Ở đây so sánh chuỗi đơn giản
        if (rawPassword == null || hashedPassword == null) return false;
        return rawPassword.equals(hashedPassword);
    }
}
