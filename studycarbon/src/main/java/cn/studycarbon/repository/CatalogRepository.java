package cn.studycarbon.repository;

import java.util.List;

import cn.studycarbon.domain.Catalog;
import cn.studycarbon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Catalog 仓库.
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 1.0.0 2017年4月10日
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    /**
     * 根据用户查询
     *
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户查询
     *
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user, String name);
}
