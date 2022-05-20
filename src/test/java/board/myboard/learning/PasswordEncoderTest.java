package board.myboard.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
     PasswordEncoder passwordEncoder;


    @Test
    public void 패스워드_암호화() throws Exception{
        //given
        String password = "노혁Nohhyuk";

        //when
        String encodePassword = passwordEncoder.encode(password); //password encode

        //then
        assertThat(encodePassword).startsWith("{"); // encodePassword에 '{' 로 시작하는지 확인
        assertThat(encodePassword).contains("{bcrypt}"); // encodePassword에 '{bcrypt}' 포합하고 있는지 확인
        assertThat(encodePassword).isNotEqualTo(password); // 기존에 있는 password와 encodePassword와 같지않은지 확인

    }

    @Test
    public void 패스워드_랜덤_암호화() throws Exception{

        //암호화시 항상 랜덤한 salt를 통해 암호화가 된다.
        //항상 다른 결과가 반환된다.

        //given
        String password = "노혁Nohhyuk";

        //when
        String encodePassword = passwordEncoder.encode(password);
        String encodePassword2 = passwordEncoder.encode(password);

        //then
        assertThat(encodePassword).isNotEqualTo(encodePassword2);

    }

    @Test
    public void 암호화된_비밀번호_매치() throws Exception{
        //given
        String password = "노혁Nohhyuk";

        //when
        String encodePassword = passwordEncoder.encode(password);

        //then
        assertThat(passwordEncoder.matches(password, encodePassword)).isTrue();
    }
}
