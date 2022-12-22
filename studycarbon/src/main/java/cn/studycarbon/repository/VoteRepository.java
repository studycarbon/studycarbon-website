package cn.studycarbon.repository;

import cn.studycarbon.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

//
// Vote 仓库.
//
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
