package all;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/controller")
public class controller {

    private DruidDataSource connect;
    private Connection connection;


    @Autowired
    public void setConnect(DruidDataSource connect) {
        this.connect = connect;
    }

    public DruidDataSource getConnect() {
        return connect;
    }

    @ResponseBody
    @RequestMapping("/getSQL")
    public String getSQL(@RequestParam("sql")String sql,
                         @RequestParam("name")String name,
                         @RequestParam("columns")String columns){
        try {
            Connection connection=connect.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.executeUpdate();
            String store="insert into table_name values(null,?)";
            statement=connection.prepareStatement(store);
            statement.setString(1,name);
            statement.executeUpdate();
            String columns_store="insert into columns values(null,?,?)";
            statement=connection.prepareStatement(columns_store);
            statement.setString(1,name);
            statement.setString(2,columns);
            statement.executeUpdate();
            return "创建成功";
        } catch (SQLException throwable) {
            return "创建失败，请检查sql语句";
        }
    }

    @ResponseBody
    @RequestMapping("/getTableName")
    public String getTableName(){
        try {
            Connection connection=connect.getConnection();
            String sql="select name from table_name";
            PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet res= statement.executeQuery();
            ArrayList<String>names=new ArrayList<>();
            while (res.next()){
                names.add(res.getNString(1));
            }
            return JSON.toJSONString(names);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping("/getTableField")
    public String getTableField(String name){
        try {
            Connection connection=connect.getConnection();
            String sql="select name from columns where table_name=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,name);
            ResultSet res=statement.executeQuery();
            if(res.next()) {
                return JSON.toJSONString(res.getNString(1));
            }else{
                return "error";
            }
        } catch (SQLException throwables) {
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("/saveData")
    public String saveTableData(String data,String name){
        Map<String,Object>map= JSON.parseObject(data);
        Connection connection= null;
        try {
            connection = connect.getConnection();
            String sql="select name from columns where table_name=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,name);
            ResultSet res=statement.executeQuery();
            if(res.next()){
                List<String>fields=JSON.parseArray(res.getNString(1),String.class);
                StringBuilder store=new StringBuilder("insert into `");
                store.append(name).append("` values(");
                int size=fields.size();
                for(int i=0;i<size;i++){
                    store.append("?").append(",");
                }
                int l=store.length();
                store.replace(l-1,l,")");
                statement=connection.prepareStatement(store.toString());
                for(int i=1;i<=size;i++){
                    statement.setObject(i,map.get(fields.get(i-1)));
                }
                statement.executeUpdate();
                return "success";
            }else{
                return "fail";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping("/getData")
    public String getTableData(String name){
        Connection connection;
        try {
            connection = connect.getConnection();
            String sql="select * from `"+name+"`";
            PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet res=statement.executeQuery();
            ResultSetMetaData metaData= res.getMetaData();
            ArrayList<ArrayList<Object>>data=new ArrayList<>();
            while (res.next()){
                ArrayList<Object>row=new ArrayList<>();
                int count= metaData.getColumnCount();
                for(int i=1;i<=count;i++){
                    row.add(res.getObject(i));
                }
                data.add(row);
            }
            return JSON.toJSONString(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping("/deleteData")
    public String deleteTableData(String table_name,int id){
        try {
            Connection connection=connect.getConnection();
            String sql="delete from `"+table_name+"` where id=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setObject(1,id);
            statement.executeUpdate();
            return "success";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "fail";
        }
    }
}
