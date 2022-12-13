package jambo.controller;


import jambo.domain.Comment;
import jambo.domain.board.Board;
import jambo.domain.board.StudyBoard;
import jambo.domain.user.User;
import jambo.dto.StudyBoardDTO;
import jambo.service.CommentService;
import jambo.service.FileService;
import jambo.service.StudyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/StudyBoard")
public class StudyBoardController {

    @Autowired
    private StudyBoardService service;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

    @RequestMapping("/StudyBoardMain")
    public String main(Model model) {
        List<StudyBoard> boards = service.selectAll();
        model.addAttribute("list", boards);
        model.addAttribute("savePath", fileService.getUrlPath());
        return "/StudyBoard/StudyBoardMain";
    }

    @RequestMapping("/StudyBoardWrite")
    public String openWriteForm() {

        return "/StudyBoard/StudyBoardWriteForm";
    }

    @RequestMapping("/insert")
    public String studyBoardInsert(StudyBoardDTO studyBoardDTO, @AuthenticationPrincipal User user, Model model) throws IOException {

        service.insert(studyBoardDTO, user);
        System.out.println("con studyBoardDTO = " + studyBoardDTO);

        return "redirect:/StudyBoard/StudyBoardMain";
    }

    @RequestMapping("/read/{id}")
    public ModelAndView read(@PathVariable Long id, String flag, Model model, @AuthenticationPrincipal User user) {
        boolean state = flag == null ? true : false;
        StudyBoard dbBoard = service.read(id, state);
        List<StudyBoard> boards = service.selectAll();

        List<Comment> commentsByBoardId = commentService.findCommentsByBoardId(id);
        model.addAttribute("comments", commentsByBoardId);
        model.addAttribute("savePath", fileService.getUrlPath());
        model.addAttribute("authUser", user);
        model.addAttribute("boardStacks", boards);

        return new ModelAndView("StudyBoard/StudyBoardRead", "board", dbBoard);

    }
}
