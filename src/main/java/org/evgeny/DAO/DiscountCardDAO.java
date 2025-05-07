package org.evgeny.DAO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.Exception.ExceptionDAO;
import org.evgeny.Model.DiscountCard;
import org.evgeny.Utill.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DiscountCardDAO implements DAO<DiscountCardDAO, Integer> {

    @Getter
    private static final DiscountCardDAO INSTANCE = new DiscountCardDAO();

    private static final String SQL_SELECT_BY_ID = """
               SELECT id, discount_card.code
               FROM discount_card
               WHERE code = ?
            """;


    public Optional<DiscountCard> findByCardCode(String cardCode) {
        try (Connection connection = GetConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            DiscountCard discountCard;

            preparedStatement.setString(1, cardCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(discountCard = DiscountCard.builder()
                        .id(resultSet.getInt("id"))
                        .code(resultSet.getString("code"))
                        .build());
            }
            return Optional.empty();
        }
        catch (SQLException e)
        {
            log.error("failed attempt to query the database", e.getMessage());
            throw new ExceptionDAO(e.getMessage());
        }
    }

    @Override
    public Optional<DiscountCardDAO> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public DiscountCardDAO create(DiscountCardDAO entity) {
        return null;
    }

    @Override
    public DiscountCardDAO update(DiscountCardDAO entity) {
        return null;
    }

    @Override
    public void delete(DiscountCardDAO entity) {

    }

    @Override
    public List<DiscountCardDAO> findAll() {
        return List.of();
    }
}
