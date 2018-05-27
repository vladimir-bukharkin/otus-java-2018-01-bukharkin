package otus.json;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import otus.json.objects.AggregationObj;
import otus.json.objects.ArraysObj;
import otus.json.objects.EnumObj;
import otus.json.objects.MapObj;
import otus.json.objects.NotAggregationObj;

import java.util.*;

@RunWith(Parameterized.class)
public class JSONParserTest {

    @Parameterized.Parameter
    public Object inputObject;

    @Parameterized.Parameters
    public static List<Object> data() {
       return Arrays.asList(getFirstTestObj(), getSecondTestObj());
    }

    @Test
    public void testParse() throws IllegalAccessException {
        JSONParser JSONParser = new JSONParser();
        JSONObject actualJson = JSONParser.parse(inputObject);

        Gson gson = new Gson();
        Object actual = gson.fromJson(actualJson.toJSONString(), inputObject.getClass());
        Assert.assertEquals(inputObject, actual);
    }


    private static Object getFirstTestObj() {
        return new NotAggregationObj("strvalue",
                Byte.parseByte("100"),
                567567,
                345L,
                'c',
                346f,
                4666.0456,
                true,
                EnumObj.THREE);
    }

    private static Object getSecondTestObj() {
        NotAggregationObj notAggregationObj1 = new NotAggregationObj("strvalue",
                Byte.parseByte("100"),
                567567,
                345L,
                'c',
                346f,
                4666.0456,
                true,
                EnumObj.THREE);
        NotAggregationObj notAggregationObj2 = new NotAggregationObj("strvalue2",
                Byte.parseByte("4"),
                -980,
                -345L,
                '2',
                -346f,
                -4666.0456,
                false,
                EnumObj.TWO);
        String[] strArray = {"asd", "ewr", null, "123"};
        NotAggregationObj[] nArray = {notAggregationObj1, notAggregationObj2, notAggregationObj1};
        float[] floatArray = {45f, 2.35f, 0, -214f};
        List<String> set = new ArrayList<String>() {{
            add("string1");
            add("strin2");
            add("null");
        }};
        ArraysObj arraysObj = new ArraysObj(strArray,
                nArray,
                floatArray,
                set);

        MapObj mapObj = new MapObj(new HashMap<EnumObj, NotAggregationObj>() {{
            put(null, notAggregationObj1);
            put(EnumObj.THREE, notAggregationObj1);
            put(EnumObj.ONE, notAggregationObj2);
            put(EnumObj.TWO, null);
        }});

        return new AggregationObj(notAggregationObj1,
                EnumObj.ONE,
                arraysObj,
                mapObj,
                Arrays.asList(new AggregationObj(notAggregationObj2,
                        EnumObj.THREE,
                        null,
                        null,
                        null,
                        null)),
                "ignoredField");
    }
}
