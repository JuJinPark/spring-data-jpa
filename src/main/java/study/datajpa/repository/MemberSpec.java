package study.datajpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.datajpa.entitiy.Member;
import study.datajpa.entitiy.Team;

import javax.persistence.criteria.*;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName){
        return (Specification<Member>) (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(teamName)){
                return null;
            }
            Join<Member, Team> t = root.join("team", JoinType.INNER);
            return criteriaBuilder.equal(t.get("name"),teamName);
        };
    }

    public static Specification<Member> userName(final String userName) {
        return (Specification<Member>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userName"),userName);
    }
}
