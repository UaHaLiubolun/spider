package spider.pipeline.impl.mysql.example.netease;


import spider.Page;
import spider.model.example.netease.PlayInfo;
import spider.pipeline.Pipeline;
import spider.pipeline.impl.mysql.ConManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NeteasePipeline implements Pipeline{

    @Override
    public void save(Page page) {
       PlayInfo playInfo =  page.getItem("playInfo");
       insert(playInfo);
    }


    private void insert(PlayInfo playInfo) {
        PreparedStatement smt = null;
        Connection con = null;
        try {
            con = ConManager.getCon();
            smt = con.prepareStatement(
                    "insert into play_list values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                    "share=?, play=?, collect=?");

            smt.setLong(1, playInfo.getId());
            smt.setString(2, playInfo.getName());
            smt.setString(3, playInfo.getAuthor());
            smt.setLong(4, playInfo.getAuthorId());
            smt.setString(5, playInfo.getDes());
            smt.setString(6, playInfo.getLabels());
            smt.setLong(7, playInfo.getShareCount());
            smt.setLong(8, playInfo.getCollectCount());
            smt.setLong(9, playInfo.getPlayCount());
            smt.setInt(10, playInfo.getNum());
            smt.setString(11, playInfo.getCreateTime());

            smt.setLong(12, playInfo.getShareCount());
            smt.setLong(14, playInfo.getCollectCount());
            smt.setLong(13, playInfo.getPlayCount());
            smt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (smt != null) {
                try {
                    smt.close();
                } catch (SQLException e) {}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {}
            }
        }
    }

}
