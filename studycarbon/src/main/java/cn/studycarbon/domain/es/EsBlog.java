package cn.studycarbon.domain.es;

import cn.studycarbon.domain.Blog;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
//import org.springframework.data.elasticsearch.annotations.FieldIndex; // es2.2

import javax.persistence.Id;
import javax.persistence.Index;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

// pojo
// java实体对象为啥一定要实现Serializable接口
// Serializable是java.io中定义的，用于实现java类序列化操作而提供的一个语义级别接口
// Serializable序列化接口没有任何方法或者字段，只是用于标识可序列化的语义
// 实现了Serializable接口的类可以被ObjectOutputStream转换为字节流，同时也可以通过ObjectInputStream再将其解析为对象
// 参考博客：https://zhuanlan.zhihu.com/p/66210653
@Document(indexName = "blog")
public class EsBlog implements Serializable {

    // 应该可以不写，没报警告
    // private static final long serialVersionUID = 1L;

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    // 不做全文检索字段
    @Field(type = FieldType.Long)
    private Long blogId;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String summary;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String content;

    // 不做全文检索字段
    @Field(type = FieldType.Keyword)
    private String username;

    // 不做全文检索字段
    @Field(type = FieldType.Keyword, index = false)
    private String avatar;

    // 不做全文检索字段
    @Field(type = FieldType.Date, index = false)
    private Date createTime;

    // 不做全文检索字段  // 访问量、阅读量
    @Field(type = FieldType.Integer)
    private Integer readSize = 0;

    // 不做全文检索字段 // 评论量
    @Field(type = FieldType.Integer)
    private Integer commentSize = 0;

    //不做全文检索字段 //点赞量
    @Field(type = FieldType.Integer)
    private Integer voteSize = 0;

    @Field(type = FieldType.Text)
    private String tags;  // 标签

    // jpa规范要求，设置为protected,这里设置为public
    protected EsBlog() {
    }

    public EsBlog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public EsBlog(Long blogId, String title, String summary, String content, String username, String avatar, Timestamp createTime,
                  Integer readSize, Integer commentSize, Integer voteSize, String tags) {
        System.out.println(" EsBlog(Blog blog)1=================================================");
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = new Date(createTime.getTime());;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public EsBlog(Blog blog) {
        System.out.println(" EsBlog(Blog blog)2=================================================");
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = new Date(blog.getCreateTime().getTime());
        this.readSize = blog.getReadSize();
        this.commentSize = blog.getCommentSize();
        this.voteSize = blog.getVoteSize();
        this.tags = blog.getTags();
    }

    public void update(Blog blog) {
        System.out.println(" update(Blog blog)=================================================");
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = new Date(blog.getCreateTime().getTime());
        this.readSize = blog.getReadSize();
        this.commentSize = blog.getCommentSize();
        this.voteSize = blog.getVoteSize();
        this.tags = blog.getTags();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
