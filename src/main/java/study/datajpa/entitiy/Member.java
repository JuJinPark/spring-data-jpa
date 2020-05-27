package study.datajpa.entitiy;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","userName","age"})
@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.userName = :username"
)
@NamedEntityGraph(
        name="Member.all",attributeNodes = @NamedAttributeNode("team")
)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userName;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String userName) {
        this.userName=userName;
    }

    public Member(String userName,int age) {
        this.userName=userName;
        this.age = age;
    }

    public Member(String userName, int age, Team team) {
        this.userName=userName;
        this.age=age;
        if(team != null){
            this.changeTeam(team);
        }
    }



    public void changeTeam(Team team){
        this.team=team;
        team.getMembers().add(this);
    }
}
