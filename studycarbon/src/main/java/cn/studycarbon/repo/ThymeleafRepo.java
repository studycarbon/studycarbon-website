package cn.studycarbon.repo;

import cn.studycarbon.domain.ThymeleafUser;
import org.springframework.data.repository.CrudRepository;

public interface ThymeleafRepo extends CrudRepository<ThymeleafUser, Long> {

}
