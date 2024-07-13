package indi.muleisy.ra.service.user.repository;

import indi.muleisy.ra.service.user.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserCredentialsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long saveUserCredentials(UserCredentials userCredentials) {
        String sql = "INSERT INTO user_credentials (user_id, password, salt, email, phone) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userCredentials.getPassword());
            ps.setString(2, userCredentials.getSalt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public UserCredentials getUserCredentialsById(Long userId) {
        String sql = "SELECT * FROM user_credentials WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new RowMapper<UserCredentials>() {
            @Override
            public UserCredentials mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserCredentials userCredentials = new UserCredentials();
                userCredentials.setId(rs.getLong("id"));
                userCredentials.setPassword(rs.getString("password"));
                userCredentials.setSalt(rs.getString("salt"));
                return userCredentials;
            }
        });
    }

    public UserCredentials getUserCredentialsByIdentifier(String identifier) {
        String sql = "SELECT * FROM user_credentials WHERE email = ? OR phone = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{identifier, identifier}, new RowMapper<UserCredentials>() {
            @Override
            public UserCredentials mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserCredentials userCredentials = new UserCredentials();
                userCredentials.setId(rs.getLong("id"));
                userCredentials.setPassword(rs.getString("password"));
                userCredentials.setSalt(rs.getString("salt"));
                return userCredentials;
            }
        });
    }
}
