package life.majiang.community.community.service;

import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.dto.QuestionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huang
 * @date 2020/1/10 15:19
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有问题列表
     * @return
     * @param page
     * @param size
     */
    public PaginationDTO searchQuestionList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据总行数
        Integer questionCount = questionMapper.selectQuestionCount();
        //分页处理
        paginationDTO.setPagination(questionCount, page, size);
        //分页公式转换
        Integer offset = size*(paginationDTO.getPage()-1);
        //从数据获取问题列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //获取分页数据
        List<Question> questions = questionMapper.List(offset, size);
        //循环列表
        for(Question question: questions){
            //通过问题对象中的ID 查询USER
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把Question对象的值拷贝到Question的对象
            BeanUtils.copyProperties(question,questionDTO);
            //用户对象的值赋值到question
            questionDTO.setUser(user);
            //加入到DTO集合（questionDTO）
            questionDTOList.add(questionDTO);
        }
        //把所有数据传入到pagination
        paginationDTO.setQuestions(questionDTOList);
        //返回值
        return paginationDTO;
    }

    /**
     * 获取个人问题
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO serchOurselvesQuestions(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据总行数
        Integer questionCount = questionMapper.selectOurselvesQuestionCount(userId);
        //分页处理
        paginationDTO.setPagination(questionCount, page, size);
        //分页公式转换
        Integer offset = size*(paginationDTO.getPage()-1);
        //从数据获取问题列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //获取分页数据
        List<Question> questions = questionMapper.selectOurselvesQuestions(userId,offset, size);
        //循环列表
        for(Question question: questions){
            //通过问题对象中的ID 查询USER
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把Question对象的值拷贝到Question的对象
            BeanUtils.copyProperties(question,questionDTO);
            //用户对象的值赋值到question
            questionDTO.setUser(user);
            //加入到DTO集合（questionDTO）
            questionDTOList.add(questionDTO);
        }
        //把所有数据传入到pagination
        paginationDTO.setQuestions(questionDTOList);
        //返回值
        return paginationDTO;
    }
}
