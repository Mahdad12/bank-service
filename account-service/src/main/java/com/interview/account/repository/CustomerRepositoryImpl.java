package com.interview.account.repository;

import com.interview.account.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class CustomerRepositoryImpl extends NamedParameterJdbcDaoSupport implements CustomerRepository {

    public static final String TABLE = "customer";

    private static final String ID = "id";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_TIME = "update_time";

    public CustomerRepositoryImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Customer findById(Long id) {
        final String sql = "SELECT * FROM "+ TABLE +" WHERE id = ?";
        assert getJdbcTemplate() != null;
        List<Customer> customers = getJdbcTemplate().query(sql, this::mapRow, id);
        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    public Long save(Customer customer) {
        try {
            final SqlParameterSource source = createSource(customer);
            final String sqlInsertQuery =
                    "insert into " + TABLE + " (id, firstname, lastname, create_time, update_time) " +
                            "values (nextval('customer_sequence'), :firstname, :lastname, :create_time, :update_time)";
            assert getNamedParameterJdbcTemplate() != null;
            KeyHolder keyHolder = new GeneratedKeyHolder();

            getNamedParameterJdbcTemplate().update(sqlInsertQuery, source, keyHolder, new String[]{"id"});

            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (Exception ex) {
            log.error("Error saving Customer: {}", customer, ex);
            throw ex;
        }
    }

    private SqlParameterSource createSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue(ID, customer.getId())
                .addValue(FIRSTNAME, customer.getFirstName())
                .addValue(LASTNAME, customer.getLastName())
                .addValue(CREATE_TIME, Optional.ofNullable(customer.getCreatedAt()).orElse(Instant.now()).atOffset(ZoneOffset.UTC))
                .addValue(UPDATE_TIME, Instant.now().atOffset(ZoneOffset.UTC));
    }

    public Customer mapRow(ResultSet rs, int rowNo) throws SQLException {
        return Customer.builder()
                .id(rs.getObject(ID, Long.class))
                .firstName(rs.getObject(FIRSTNAME, String.class))
                .lastName(rs.getObject(LASTNAME, String.class))
                .createdAt(rs.getTimestamp(CREATE_TIME).toInstant())
                .updatedAt(rs.getTimestamp(UPDATE_TIME).toInstant())
                .build();
    }
}
