package jambo.domain.board;

import jambo.domain.board.type.Category;
import jambo.domain.user.User;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class NormalBoard extends Board {
    public NormalBoard() {
    }

    public NormalBoard(User user, String title, String content, String category) {
        super(user, title, content, category);
    }

    public NormalBoard(Long id, User user, String title, String content, List<Recommendation> recommendation, LocalDateTime writeDate, int views, boolean isReported, Category category) {
        super(id, user, title, content, recommendation, writeDate, views, isReported, category);
    }
}
