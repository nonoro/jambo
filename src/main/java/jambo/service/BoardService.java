package jambo.service;

import jambo.domain.board.Board;
import jambo.domain.board.NormalBoard;
import jambo.domain.board.type.Category;
import jambo.domain.user.Point;
import jambo.domain.user.User;
import jambo.dto.NormalBoardDTO;
import jambo.repository.BoardRepository;
import jambo.repository.NormalBoardRepository;
import jambo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private NormalBoardRepository normalBoardRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 카테고리에 맞는 전체 NormalBoard 검색
     * */
    public Page<Board> findAll(Category category, Pageable page){
        return boardRepository.serachByCategory(category, page);
    }

    /**
     * NormalBoard 상세보기
     * */
    public NormalBoard read(Long id, Boolean state){
        if(state) {//조회수 증가
            boardRepository.updateViews(id);
        }
        NormalBoard normalBoardById = normalBoardRepository.findNormalBoardById(id);
        return normalBoardById;
    }
    /**
     * 게시글 작성하기
     * */
    public void insert(NormalBoardDTO normalBoardDTO, User user){
        NormalBoard normalBoard = normalBoardDTO.toEntity(user);
        boardRepository.save(normalBoard);
        user.addPoint(30);

        userRepository.save(user);
    }
    /**
     * 게시글 삭제하기
     * */
    public void delete(Long id){
        int result = boardRepository.deleteBoardById(id);
        // 예외처리
    }

    public Board findBoardById(Long id){

        return boardRepository.findBoardById(id);
    }

    /**
     * 내가쓴 모든 NomalBoard 조회
     */
    public List<NormalBoard> showNormalBoard(User user){

        return normalBoardRepository.SearchNomalBoardByEmail(user);
    }
}