package com.xiaoniucr.common.dto;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分页参数
 */
public class PageQueryDto extends HashMap {


    //数据库字段驼峰命名转换
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 开始条数，sql语句起始索引
     */
    private Integer offset;

    /**
     * 每页几条
     */
    private Integer limit = 10;

    /**
     * 当前第几页
     */
    private Integer page = 1;

    /**
     * 排序的列名
     */
    private String sort;

    /**
     * 排序方式'asc' 'desc'
     */
    private String sortOrder;


    public PageQueryDto() {
    }

    public PageQueryDto(Map<String,Object> map){

        this.putAll(map);
        //dataTable分页参数
        if(map.containsKey("start")){
            this.put("start",Integer.valueOf(map.get("start").toString()));
        }
        if(map.containsKey("length")){
            limit = Integer.valueOf(map.get("length").toString());
            this.put("limit",limit);
        }
        //自定义分页参数
        if(map.containsKey("page")){
            page = Integer.parseInt(map.get("page").toString());
            //page为-1时，不分页，查询全部
            if(page != -1){
                this.put("start",limit * (page - 1));
            }
        }
        //排序字段，有些字段，前端传过来的是驼峰命名，需要装为数据库中对应的带-写法
        if(map.containsKey("sort")){
            this.put("sort", humpToLine2(map.get("sort").toString()));
        }
    }


    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
