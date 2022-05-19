package board.myboard.domain.member;

import board.myboard.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //primary Key

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    private String password; // 비밀번호

    @Column(nullable = false, length = 30)
    private String name; // 이름(실명)

    @Column(nullable = false, length = 30)
    private String nickName; // 별명

    @Column(nullable = false, length = 30)
    private int age; // 나이

    @Enumerated(EnumType.STRING)
    private Role role; // 권한 -> USER, ADMIN



    // 정보 수정 //
    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateNickName(String nickName){
        this.nickName = nickName;
    }

    public void updataAge(int age){
        this.age = age;
    }

    // 패스워크 암호화 //
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

}

