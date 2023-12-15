package repository;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.repository.AccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountRepositoryTest {
    private Connection connection;

    @BeforeEach
    public void connectionForTest(){
        connection = ConnectionToDb.getConnection();
        Assertions.assertNotNull(connection);
    }

    @Test
    public void testFindById() throws SQLException{
            // Given
            AccountRepository accountRepository = new AccountRepository(connection);

            // When
            Account resultAccount = accountRepository.findById(4);

            // Then
            Assert.assertNotNull(resultAccount);
            System.out.println("Found account: " + resultAccount);

    }
}
