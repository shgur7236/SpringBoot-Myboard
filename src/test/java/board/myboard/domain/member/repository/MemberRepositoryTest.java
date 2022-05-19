package board.myboard.domain.member.repository;

import board.myboard.domain.member.Member;
import board.myboard.domain.member.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.jboss.logging.NDC.clear;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @AfterEach
    private void after(){
        em.clear();
    }

    @Test
    public void MemberSaveOK() throws Exception{
        //given
        Member member = Member.builder().username("username").password("1234567890").name("Member1").nickName("NickName1").role(Role.USER).age(22).build();

        //when
        Member saveMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다."));// 아직 예외 클래스를 만들지 않았기에 RuntimeException으로 처리한다.

        assertThat(findMember).isSameAs(saveMember);
        assertThat(findMember).isSameAs(member);

    }

    @Test
    public void 오류_회원가입시_아이디가_없음() throws Exception{
        //given
        Member member = Member.builder().password("1234567890").name("Member1").nickName("NickName1").role(Role.USER).age(22).build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_이름_없음() throws  Exception{
        //given
        Member member = Member.builder().password("1234567890").username("username").nickName("NickName1").role(Role.USER).age(22).build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_닉네임_없음() throws Exception{
        //given
        Member member = Member.builder().password("1234567890").username("username").name("Member1").role(Role.USER).age(22).build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_나이가_없음() throws Exception {
        //given
        Member member = Member.builder().username("username").password("1234567890").name("Member1").role(Role.USER).nickName("NickName1").build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_중복된_아이디가_있음(){
        //given
        Member member1 = Member.builder().username("username").password("1234567890").name("Member1").nickName("NickName1").role(Role.USER).age(22).build();
        Member member2 = Member.builder().username("username").password("1234567890").name("Member1").nickName("NickName1").role(Role.USER).age(22).build();

        memberRepository.save(member1);
        clear();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member2));

    }
}

