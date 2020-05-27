package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entitiy.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom,JpaSpecificationExecutor<Member> {
    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUserName(@Param("username")String username);

    @Query("select m from Member m where m.userName = :userName and m.age = :age")
    List<Member> findUser(@Param("userName")String userName,@Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id,m.userName,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    List<Member> findListByUserName(String userName);
    Member findMemberByUserName(String userName);
    Optional<Member> findOptionalByUserName(String userName);

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join m.team t",countQuery = "select count(m) from Member m")
    Page<Member> findSeparateCountQueryByAge(int age, Pageable pageable);
    //count 쿼리 일대다 조인 부분 최적화 커리 페이징도되나?
    // 커스텀커리에 Page 객체 사용가능?

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
   // @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})//entity graph는 어디에다 조합해서 사용가능
   List<Member> findGraphByUserName(@Param("username") String username);

    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly", value="true"))
    Member findReadOnlyByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String userName);

    <T>List<T> findProjectionsByUserName(@Param("username") String username,Class<T> type);

    //query 에서는실제 데이터 베이스 컬럼명으로 맞춰야함
    @Query(value = "select * from member where user_name = ?",nativeQuery = true)
    Member findByNativeQuery(String user_name);

    @Query(value="select m.member_id as id, m.user_name as userName, t.name as teamName " +
            "from Member m left join team t",
            countQuery = "select count(*) from Member",nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
