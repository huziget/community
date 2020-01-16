package life.majiang.community.community.service;

import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Entity.QuestionExample;
import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Entity.UserExample;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.apache.ibatis.session.RowBounds;
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
        //调用获取数据方法
        PaginationDTO paginationDTO = this.questionPage(new Integer(0), page, size, true);
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
        //调用获取数据方法
        PaginationDTO paginationDTO = this.questionPage(userId, page, size, false);
        return paginationDTO;
    }

    /**
     * 获取问题详情根据ID
     * @param id
     * @return
     */
    public QuestionDTO serchById(Integer id) {
        //根据ID获取问题
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        //把结果copy到DTO中
        BeanUtils.copyProperties(question,questionDTO);
        //获取User对象
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        //放入DTO
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     *更新或者添加问题
     * @param question
     * @param user
     */
    public void editOrAddQuestion(Question question, User user) {
        Question  questionData = questionMapper.selectByPrimaryKey(question.getId());
        if(questionData == null){
            //新增问题.
            question.setCreator(user.getId());
            question.setGmtCreate(user.getGmtCreate());
            question.setGmtModified(user.getGmtModified());
            questionMapper.insert(question);
        }else {
            //更新问题
            question.setGmtUpdatedata(user.getGmtModified());
            Question updateQuestion = new Question();
            updateQuestion.setGmtUpdatedata(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            //判断数据是否更新成功
            if(update != 1){
                //抛出一个异常
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 封装公用的问题列表方法
     * @param userId
     * @param page
     * @param size
     * @param allOrOurselves
     * @return
     */
    public PaginationDTO questionPage(Integer userId, Integer page, Integer size,Boolean allOrOurselves){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer questionCount;
        List<Question> questions;
        //数据总行数
        if(allOrOurselves){
            //如果为ture 就是查询全部的问题
            questionCount = (int)questionMapper.countByExample(new QuestionExample());
        }else{
            //查询Id的问题
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andCreatorEqualTo(userId);
            questionCount = (int)questionMapper.countByExample(questionExample);
        }

        //分页处理
        paginationDTO.setPagination(questionCount, page, size);
        //分页公式转换
        Integer offset = size*(paginationDTO.getPage()-1);
        //从数据获取问题列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //获取分页数据
        if(allOrOurselves){
           //如果为ture 就是查询全部的问题
             questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        }else{
            //查询Id的问题
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andCreatorEqualTo(userId);
            questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        }
        //循环列表
        for(Question question: questions){
            //通过问题对象中的ID 查询USER
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
