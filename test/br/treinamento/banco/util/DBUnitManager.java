package br.treinamento.banco.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import br.treinamento.dao.ConnectionFactory;

public class DBUnitManager {

    public void cleanAndInsert(String dbUnitXmlPath) {
        execute(DatabaseOperation.CLEAN_INSERT, dbUnitXmlPath);
    }
    
    public void delete(String dbUnitXmlPath) {
        execute(DatabaseOperation.DELETE, dbUnitXmlPath);
    }

    protected void execute(DatabaseOperation operation, String dbUnitXmlPath) {
        try {
            IDatabaseConnection dbconn = this.getDbUnitConnection();
            operation.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
            dbconn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected IDatabaseConnection getDbUnitConnection() throws DatabaseUnitException, SQLException {
        IDatabaseConnection dbconn = new DatabaseConnection(this.getConnection());
        return dbconn;
    }

    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao tentar obter uma conexão com o banco: " + e.getMessage());
        }
        return conn;
    }

    private IDataSet getDataSetFrom(String dbUnitXmlPath) throws IOException, DataSetException, FileNotFoundException {
        DataFileLoader loader = new FlatXmlDataFileLoader();
        return loader.load(dbUnitXmlPath);
    }

}
