package life.majiang.community.community.dto;

import lombok.Data;

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
    private List<Integer> pages;

    /**
     * 计算页码页数等
     * @param questionCount
     * @param page
     * @param size
     */
    public void setPagination(Integer questionCount, Integer page, Integer size) {
        //计算总页数
        Integer totalPage = 0;
        //页码正好的时候
        if (questionCount / size == 0) {
            totalPage = questionCount / size;
        } else {
            //页码取余的时候
            totalPage = questionCount / size + 1;
        }

        //是否展示上一页
        if(page == 1){
            showFirstPage = false;
        }else {
            showEndPage = true;
        }

        //是否展示下一页
        if(page == totalPage){
            showEndPage = false;
        } else {
            showFirstPage = true;
        }
    }
}
