import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new ArrayList<>(){{add("a");add("b");}}));
    }
}
