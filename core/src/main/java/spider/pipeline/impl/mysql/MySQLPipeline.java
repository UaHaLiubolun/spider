package spider.pipeline.impl.mysql;

import spider.Page;
import spider.pipeline.Pipeline;

import java.sql.SQLException;
import java.sql.Statement;


public class MySQLPipeline implements Pipeline {

    @Override
    public void save(Page page) {

    }


    private void insertItem() {
        try {
            Statement smt = ConManager.getCon().createStatement();
        } catch (SQLException e) {

        } finally {

        }
    }
}
