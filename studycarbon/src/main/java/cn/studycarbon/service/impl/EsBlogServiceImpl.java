package cn.studycarbon.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.studycarbon.domain.User;
import cn.studycarbon.domain.es.EsBlog;
import cn.studycarbon.repository.es.EsBlogRepository;
import cn.studycarbon.service.EsBlogService;
import cn.studycarbon.service.UserService;
import cn.studycarbon.vo.TagVO;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchParseException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;


//EsBlog 服务.
//@since 1.0.0 2017年4月12日
//@author <a href="https://waylau.com">Way Lau</a>

//
@Service
public class EsBlogServiceImpl implements EsBlogService {
	@Autowired
	private EsBlogRepository esBlogRepository;

	//ElasticsearchOperations 使用 Transport Client 的接口的实现。从 4.0 版开始不推荐，请改用 ElasticsearchRestTemplate
	//参考：https://blog.csdn.net/ghdqfhw/article/details/113687869
	//@Autowired
	//private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Autowired
	private UserService userService;
	
	private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
	private static final String EMPTY_KEYWORD = "";
	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.EsBlogService#removeEsBlog(java.lang.String)
	 */
	@Override
	public void removeEsBlog(String id) {
		// esBlogRepository.delete(id);
		esBlogRepository.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.EsBlogService#updateEsBlog(com.waylau.spring.boot.blog.domain.es.EsBlog)
	 */
	@Override
	public EsBlog updateEsBlog(EsBlog esBlog) {
		return esBlogRepository.save(esBlog);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.EsBlogService#getEsBlogByBlogId(java.lang.Long)
	 */
	@Override
	public EsBlog getEsBlogByBlogId(Long blogId) {
		return esBlogRepository.findByBlogId(blogId);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.EsBlogService#listNewestEsBlogs(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) throws SearchParseException {
		Page<EsBlog> pages = null;
		// Sort sort = new Sort(Direction.DESC,"createTime");
		Sort sort = Sort.by(Direction.DESC, "createTime");
		if (pageable.getSort() == null) {
			//pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
 
		pages = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword, pageable);
 
		return pages;
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.EsBlogService#listHotestEsBlogs(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) throws SearchParseException{
 
		Sort sort = Sort.by(Direction.DESC,"readSize","commentSize","voteSize","createTime");
		if (pageable.getSort() == null) {
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
 
		return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
	}

	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		return esBlogRepository.findAll(pageable);
	}
 
 
	/**
	 * 最新前5
	 * @param keyword
	 * @return
	 */
	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}
	
	/**
	 * 最热前5
	 * @param keyword
	 * @return
	 */
	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<TagVO> listTop30Tags() {
 
		List<TagVO> list = new ArrayList<>();
		// SearchQuery已过时，采用 NativeSearchQuery
		// 参考：https://blog.csdn.net/csdn_20150804/article/details/105618933
		// given
		//SearchQuery searchQuery = new NativeSearchQueryBuilder()
		//		.withQuery(matchAllQuery())
		//		.withSearchType(SearchType.QUERY_THEN_FETCH)
		//		.withIndices("blog").withTypes("blog")
		//		.addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
		//		.build();

		// 使用NativeSearchQuery实现复杂查询
		// NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
		//		.withQuery(matchAllQuery())
		//		.withSearchType(SearchType.QUERY_THEN_FETCH)
		//		.addAggregation(terms("tags").field("tags").order(Terms.Order.))



		// when
		//Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
		//	@Override
		//	public Aggregations extract(SearchResponse response) {
		//		return response.getAggregations();
		//	}
		//});
		
		//StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags");
	        
        //Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        //while (modelBucketIt.hasNext()) {
        //    Bucket actiontypeBucket = modelBucketIt.next();
 
        //    list.add(new TagVO(actiontypeBucket.getKey().toString(),
        //            actiontypeBucket.getDocCount()));
        //}
		return list;
	}
	
	@Override
	public List<User> listTop12Users() {
 
		//List<String> usernamelist = new ArrayList<>();
		// given
		//SearchQuery searchQuery = new NativeSearchQueryBuilder()
		//		.withQuery(matchAllQuery())
		//		.withSearchType(SearchType.QUERY_THEN_FETCH)
		//		.withIndices("blog").withTypes("blog")
		//		.addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
		//		.build();
		// when
		//Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
		//	@Override
		//	public Aggregations extract(SearchResponse response) {
		//		return response.getAggregations();
		//	}
		//});
		
		//StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("users");
	        
        //Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        //while (modelBucketIt.hasNext()) {
        //    Bucket actiontypeBucket = modelBucketIt.next();
        //    String username = actiontypeBucket.getKey().toString();
        //    usernamelist.add(username);
        //}
        //List<User> list = userService.listUsersByUsernames(usernamelist);
        
		return null;
	}
}
