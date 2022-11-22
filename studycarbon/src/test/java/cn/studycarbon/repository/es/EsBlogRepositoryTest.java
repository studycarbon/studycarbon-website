package cn.studycarbon.repository.es;

import cn.studycarbon.domain.es.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData() {
        // 清除所有数据
        esBlogRepository.deleteAll();
        String[][] blogStrArray = {
                {"登鹳雀楼","王之涣的登鹳雀楼", "白日依山尽，黄河入海流，欲求千里目，更上一层楼"},
                {"相思","王维的相思","红豆生南国，春来发几枝，愿君多采撷，此物最相思"},
                {"静夜思","李白的静夜思","床前明月光，疑是地上霜，举头望明月，低头思故乡"}
        };

        //esBlogRepository.save(blogStrArray[0][0],blogStrArray[0][1],blogStrArray[0][2]);
        for (int i = 0; i < blogStrArray.length; i++) {
            EsBlog esBlog = new EsBlog(blogStrArray[i][0], blogStrArray[i][1], blogStrArray[i][2]);
            esBlogRepository.save(esBlog);
        }
    }

    @Test
    public void testFindBlog() {
        // 参考资料
        // https://blog.csdn.net/xianpingping/article/details/105675340?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105675340-blog-105614972.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105675340-blog-105614972.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=1
        //Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        // Pageable pageable = PageRequest.of(0,size,sort);
        Pageable pageable = PageRequest.of(0,20);

        String title = "思";
        String summary = "相思";
        String content = "相思";
        Page<EsBlog> page = esBlogRepository.findDistinctByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        System.out.println("========================================================");
        for(EsBlog blog : page.getContent()) {
            System.out.println(blog.toString());
        }
    }
}
