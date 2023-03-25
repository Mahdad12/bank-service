package com.interview.account.repository;

import com.interview.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class AccountRepositoryImpl extends NamedParameterJdbcDaoSupport implements AccountRepository {
     public static final String TABLE = "ACCOUNT";
 
     private static final String ID = "id";
     private static final String CUSTOMER_ID = "customer_id";
     private static final String BALANCE = "balance";
     private static final String CREATE_TIME = "create_time";
     private static final String UPDATE_TIME = "update_time";

     public AccountRepositoryImpl(DataSource dataSource) {
         setDataSource(dataSource);
     }


    @Override
    public Account findById(Long id) {
        final String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        assert getJdbcTemplate() != null;
        List<Account> accounts = getJdbcTemplate().query(sql, this::mapRow, id);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public Long save(Account account) {
         try {
             final SqlParameterSource source = createSource(account);
             final String sqlInsertQuery = "insert into " + TABLE + " (id, customer_id, balance, create_time, update_time) values (nextval('account_sequence'), :customer_id, :balance, :create_time, :update_time)";
             assert getNamedParameterJdbcTemplate() != null;
             KeyHolder keyHolder = new GeneratedKeyHolder(); // create a KeyHolder to hold the generated ID value

             getNamedParameterJdbcTemplate().update(sqlInsertQuery, source, keyHolder, new String[]{"id"}); // specify the column name of the generated ID

             return Objects.requireNonNull(keyHolder.getKey()).longValue(); // get the generated ID value
         } catch (Exception ex) {
             log.error("Error saving Account: {}", account, ex);
             throw ex;
         }
    }


    private SqlParameterSource createSource(Account account) {
        return new MapSqlParameterSource()
                .addValue(ID, account.getId())
                .addValue(CUSTOMER_ID, account.getCustomerId())
                .addValue(BALANCE, account.getBalance())
                .addValue(CREATE_TIME, Optional.ofNullable(account.getCreatedAt()).orElse(Instant.now()).atOffset(ZoneOffset.UTC))
                .addValue(UPDATE_TIME, Instant.now().atOffset(ZoneOffset.UTC));
    }

    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Account.builder()
                .id(rs.getObject(ID, Long.class))
                .customerId(rs.getObject(CUSTOMER_ID, Long.class))
                .balance(rs.getObject(BALANCE, BigDecimal.class))
                .createdAt(rs.getTimestamp(CREATE_TIME).toInstant())
                .updatedAt(rs.getTimestamp(UPDATE_TIME).toInstant())
                .build();
    }
}

