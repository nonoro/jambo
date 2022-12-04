package jambo.domain.user;

import jambo.domain.user.Icon;
import jambo.domain.user.IconShop;
import jambo.domain.user.User;
import jambo.dto.IconShopDTO;
import jambo.dto.UserBuyIconDTO;
import jambo.repository.IconRepository;
import jambo.repository.IconShopRepository;
import jambo.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
public class IconShopTest {
    @Autowired
    private IconShopRepository iconShopRepository;

    @Autowired
    private IconRepository iconRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void iconShopSaveTest() {
        IconShopDTO iconShopDTO1 = new IconShopDTO("코끼리1", "움직이는 코끼리", 2000, 100, null);
        IconShopDTO iconShopDTO2 = new IconShopDTO("코끼리2", "움직이는 코끼리 얼굴", 2000, 100, null);
        IconShopDTO iconShopDTO3 = new IconShopDTO("코끼리E", "안움직이는 코끼리", 500, 100, null);
        IconShop icon1 = iconShopDTO1.toEntity();
        IconShop icon2 = iconShopDTO2.toEntity();
        IconShop icon3 = iconShopDTO3.toEntity();

        iconShopRepository.save(icon1);
        iconShopRepository.save(icon2);
        iconShopRepository.save(icon3);
    }

    @Test
    public void iconShopFindAllTest() {
        iconShopRepository.findAll().forEach(iconList -> System.out.println("iconList = " + iconList));
    }

    @Test
    public void buyUserIcon() {
//        UserBuyIconDTO userBuyIconDTO = new UserBuyIconDTO("kkk@naver.com", "elephant1.gif");
        UserBuyIconDTO userBuyIconDTO = new UserBuyIconDTO("qqq@naver.com", "elephant1.gif");
        User user = userRepository.findByEmail(userBuyIconDTO.getEmail());
        IconShop icon = iconShopRepository.findByName(userBuyIconDTO.getIconName());
        Icon purchasedIcon = new Icon(user, icon);

        purchasedIcon.setUser(user);
        purchasedIcon.setIconShop(icon);

        iconRepository.save(purchasedIcon);
    }

    @DisplayName("병신개쓰레기 페이징처리 테스트")
    @Test
    @Disabled
    public void pagingTest() {
        int currentPage = 12;
        int pageSize = 2;
        int blockCount = 3;
        boolean exitLoop = false;
        Pageable page = PageRequest.of(currentPage - 1, pageSize, Sort.Direction.DESC, "id");

        String actual = "";

        Page<IconShop> iconShops = iconShopRepository.findAllByOrderBySaveDateDesc(page);

        int totalPages = iconShops.getTotalPages();

        int tmp = (currentPage - 1) % blockCount;
        int startPage = currentPage - tmp;

        if (startPage - blockCount > 0) {
            actual += "<";
        }

        for (int i = startPage; i <= ((startPage - 1) + blockCount); i++) {
            if ((i - 1) >= totalPages) {
                exitLoop = true;
            }
            if (!exitLoop) {
                actual += i;
            }
        }

        if (startPage + blockCount <= totalPages) {
            actual += ">";
        }
        System.out.println("totalPages = " + totalPages);

        assertThat(actual).isEqualTo("<101112");
    }
}
