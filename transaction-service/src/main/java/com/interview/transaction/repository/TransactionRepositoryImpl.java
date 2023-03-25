package com.interview.transaction.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.interview.transaction.model.Transaction;

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
public class TransactionRepositoryImpl extends NamedParameterJdbcDaoSupport implements TransactionRepository {
    public static final String TABLE = "TRANSACTION_DATA";

    private static final String ID = "id";
    private static final String ACCOUNT_ID = "account_id";
    private static final String AMOUNT = "amount";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_TIME = "update_time";

    public TransactionRepositoryImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        final String sql = "SELECT * FROM " + TABLE + " WHERE account_id = ?";
        assert getJdbcTemplate() != null;
        return getJdbcTemplate().query(sql, this::mapRow, accountId);
    }


    @Override
    public Long save(Transaction transaction) {
        try {
            final SqlParameterSource source = createSource(transaction);
            final String sqlInsertQuery = "insert into " + TABLE + " (id, account_id, amount, create_time, update_time)" +
                    " values (nextval('transaction_sequence'), :account_id, :amount, :create_time, :update_time)";
            assert getNamedParameterJdbcTemplate() != null;
            KeyHolder keyHolder = new GeneratedKeyHolder();

            getNamedParameterJdbcTemplate().update(sqlInsertQuery, source, keyHolder, new String[]{"id"});

            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (Exception ex) {
            log.error("Error saving Transaction: {}", transaction, ex);
            throw ex;
        }
    }

    private SqlParameterSource createSource(Transaction transaction) {
        return new MapSqlParameterSource()
                .addValue(ID, transaction.getId())
                .addValue(ACCOUNT_ID, transaction.getAccountId())
                .addValue(AMOUNT, transaction.getAmount())
                .addValue(CREATE_TIME, Optional.ofNullable(transaction.getCreatedAt()).orElse(Instant.now()).atOffset(ZoneOffset.UTC))
                .addValue(UPDATE_TIME, Instant.now().atOffset(ZoneOffset.UTC));
    }

    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Transaction.builder()
                .id(rs.getObject(ID, Long.class))
                .accountId(rs.getObject(ACCOUNT_ID, Long.class))
                .amount(rs.getObject(AMOUNT, BigDecimal.class))
                .createdAt(rs.getTimestamp(CREATE_TIME).toInstant())
                .updatedAt(rs.getTimestamp(UPDATE_TIME).toInstant())
                .build();
    }
}
