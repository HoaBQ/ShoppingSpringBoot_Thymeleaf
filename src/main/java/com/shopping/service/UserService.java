package com.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.entity.User;
import com.shopping.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> searchAndPaginate(String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        return userRepository.findByUsernameContainingIgnoreCase(keyword.trim(), PageRequest.of(safePage - 1, size));
    }

    public void save(User user) throws Exception {
        String username = user.getUsername() == null ? "" : user.getUsername().trim();
        String email = user.getEmail() == null ? "" : user.getEmail().trim();
        user.setUsername(username);
        user.setEmail(email);

        if (user.getId() == 0) {
            if (username.isBlank()) {
                throw new Exception("Tên đăng nhập không được để trống!");
            }
            if (userRepository.existsByUsername(username)) {
                throw new Exception("Username đã tồn tại!");
            }
            if (email.isBlank()) {
                throw new Exception("Email không được để trống!");
            }
            if (userRepository.existsByEmail(email)) {
                throw new Exception("Email đã tồn tại!");
            }
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                throw new Exception("Mật khẩu không được để trống!");
            }
        } else {
            User existing = userRepository.findById(user.getId()).orElse(null);
            if (existing == null) {
                throw new Exception("Người dùng không tồn tại!");
            }
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(existing.getPassword());
            }
            // else: giữ nguyên mật khẩu plaintext người dùng nhập, không encode.
        }

        // TODO: đang lưu mật khẩu dạng plaintext để test nhanh, khớp với
        // LoginController đang so sánh plaintext. Khi lên production, bật
        // lại passwordEncoder.encode(...) ở đây và passwordEncoder.matches(...)
        // ở LoginController.
        // if (user.getId() == 0) {
        //     user.setPassword(passwordEncoder.encode(user.getPassword()));
        // }

        userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}