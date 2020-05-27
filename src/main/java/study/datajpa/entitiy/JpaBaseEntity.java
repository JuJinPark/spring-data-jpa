package study.datajpa.entitiy;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updateDate = now;
        //update null  이있으면 쿼리가 지저분해짐 그래서 넣는다.
    }

    @PreUpdate
    public void preUpdate(){
        updateDate = LocalDateTime.now();
    }
}
