
package com.cc.cms.lucene;

import static com.cc.cms.Constants.*;

import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.common.page.*;
import com.cc.common.web.springmvc.*;

@Service
@Transactional
@SuppressWarnings("deprecation")
public class LuceneContentSvcImpl implements LuceneContentSvc {

	@Autowired
	private RealPathResolver	realPathResolver;
	@Autowired
	private IContentSvc			contentMng;
	@Autowired
	private LuceneContentDao	luceneContentDao;

	@Override
	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId, Date startDate, Date endDate, Integer startId, Integer max) throws IOException, ParseException {
		String path = realPathResolver.get(LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		return createIndex(siteId, channelId, startDate, endDate, startId, max, dir);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId, Date startDate, Date endDate, Integer startId, Integer max, Directory dir) throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(siteId, channelId, startDate, endDate, writer);
			}
			Integer lastId = luceneContentDao.index(writer, siteId, channelId, startDate, endDate, startId, max);
			writer.optimize();
			return lastId;
		} finally {
			writer.close();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void createIndex(Content content) throws IOException {
		String path = realPathResolver.get(LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		createIndex(content, dir);
	}

	@Override
	@Transactional(readOnly = true)
	public void createIndex(Content content, Directory dir) throws IOException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			writer.addDocument(LuceneContent.createDocument(content));
		} finally {
			writer.close();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId) throws IOException, ParseException {
		String path = realPathResolver.get(LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		deleteIndex(contentId, dir);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId, Directory dir) throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		if (exist) {
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), false, IndexWriter.MaxFieldLength.LIMITED);
			try {
				LuceneContent.delete(contentId, writer);
			} finally {
				writer.close();
			}
		}
	}

	@Override
	public void updateIndex(Content content) throws IOException, ParseException {
		String path = realPathResolver.get(LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		updateIndex(content, dir);
	}

	@Override
	public void updateIndex(Content content, Directory dir) throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(content.getId(), writer);
			}
			writer.addDocument(LuceneContent.createDocument(content));
		} finally {
			writer.close();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination searchPage(String path, String queryString, Integer siteId, Integer channelId, Date startDate, Date endDate, int pageNo, int pageSize) throws CorruptIndexException, IOException, ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchPage(dir, queryString, siteId, channelId, startDate, endDate, pageNo, pageSize);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination searchPage(Directory dir, String queryString, Integer siteId, Integer channelId, Date startDate, Date endDate, int pageNo, int pageSize) throws CorruptIndexException, IOException, ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString, siteId, channelId, startDate, endDate, analyzer);
			TopDocs docs = searcher.search(query, pageNo * pageSize);
			Pagination p = LuceneContent.getResultPage(searcher, docs, pageNo, pageSize);
			List<?> ids = p.getList();
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				contents.add(contentMng.findById((Integer) id));
			}
			p.setList(contents);
			return p;
		} finally {
			searcher.close();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Content> searchList(String path, String queryString, Integer siteId, Integer channelId, Date startDate, Date endDate, int first, int max) throws CorruptIndexException, IOException, ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchList(dir, queryString, siteId, channelId, startDate, endDate, first, max);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Content> searchList(Directory dir, String queryString, Integer siteId, Integer channelId, Date startDate, Date endDate, int first, int max) throws CorruptIndexException, IOException, ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString, siteId, channelId, startDate, endDate, analyzer);
			if (first < 0) {
				first = 0;
			}
			if (max < 0) {
				max = 0;
			}
			TopDocs docs = searcher.search(query, first + max);
			List<Integer> ids = LuceneContent.getResultList(searcher, docs, first, max);
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				contents.add(contentMng.findById((Integer) id));
			}
			return contents;
		} finally {
			searcher.close();
		}
	}
}
