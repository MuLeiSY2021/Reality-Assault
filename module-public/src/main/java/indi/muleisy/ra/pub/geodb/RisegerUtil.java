package indi.muleisy.ra.pub.geodb;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.riseger.jrdbc.driver.connector.Connection;
import org.riseger.jrdbc.driver.session.Statement;
import org.riseger.protocol.compiler.result.ResultSet;

@Log4j2
public class RisegerUtil {
    private static final String RESIGER_HOST = "localhost";
    private static final int RESIGER_PORT = 10086;

    @Getter
    private static Connection connection;

    static {
        try {
            connection = Connection.connect(RESIGER_HOST, RESIGER_PORT);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void close() {
        if (connection != null) {
            connection.close();
        }
    }

    public static ResultSet search(String query) {
        Statement stmt = connection.search();
        stmt.setSqlText(query);
        try {
            return stmt.send().getResult();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
