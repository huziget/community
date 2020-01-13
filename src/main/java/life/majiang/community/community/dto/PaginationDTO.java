package life.majiang.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huang
 * @date 2020/1/10 18:21
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private int page;
    private int size;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    /**
     * 计算页码页数等
     * @param questionCount
     * @param page
     * @param size
     */
    public void setPagination(Integer questionCount, Integer page, Integer size) {
        //结果是否为0
        if(questionCount == 0){
            totalPage = 1;
        }else {
            //页码正好的时候
            if (questionCount % size == 0) {
                totalPage = questionCount / size;
            } else {
                //页码取余的时候
                totalPage = questionCount / size + 1;
            }
        }

        //页码越界处理
        if(page > totalPage){
            page = totalPage;
        }
        if(page < 1){
            page = 1;
        }

        //赋值页码
        this.page =  page;
        this.size = size;
        //页面显示页码
        pages.add(page);
        for(int i = 1; i <= 3; i++){
            if(page - i > 0){
                //头部插入
                pages.add(0,page - i);
            }
            if(page + i <= totalPage){
                //尾部插入
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if(page == 1){
            showPrevious= false;
        }else {
            showPrevious = true;
        }

        //是否展示下一页
        if(page == totalPage){
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
