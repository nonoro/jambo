package jambo.service;


import jambo.domain.TechStack;
import jambo.domain.board.Board;


import jambo.domain.board.StudyBoard;
import jambo.domain.user.User;
import jambo.dto.StudyBoardDTO;
import jambo.repository.BoardRepository;
import jambo.repository.StudyBoardRepository;
import jambo.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class StudyBoardService {

    @Autowired
    private TechStackRepository techStackRepository;
    @Autowired
    private StudyBoardRepository studyBoardRepository;
    @Autowired
    private BoardRepository boardRepository;

    public void insert(StudyBoardDTO studyBoardDTO, User user){
        List<String> studyBoardTechStacks = studyBoardDTO.getTechStacks();
        StudyBoard board = studyBoardDTO.toEntity();
        board.setUser(user);

        if(studyBoardTechStacks != null){
            List<TechStack> techStacks = techStackRepository.findAllByTechStackNameIn(studyBoardTechStacks);
            board.setTechStacks(techStacks);
        }


        boardRepository.save(board);

    }

    public List<StudyBoard> selectAll(){

//        return studyBoardRepository.findBoardsByCategory("STUDY_BOARD");
//        return studyBoardRepository.findAll();
        return studyBoardRepository.findAllByOrderByWriteDateDesc();
    }

    public StudyBoard read(Long id, boolean state){

        if(state) {//조회수 증가
            boardRepository.updateViews(id);
        }
        return studyBoardRepository.findStudyBoardById(id);
    }

    /**
     * 내가쓴 모든 StudyBoard 조회
     */
    public List<StudyBoard> showStudyBoard(User user) {
        return studyBoardRepository.SearchStudyBoardByEmail(user);
    }
}
