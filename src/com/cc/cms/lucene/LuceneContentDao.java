
package com.cc.cms.lucene;

import java.io.*;
import java.util.*;

import org.apache.lucene.index.*;

public interface LuceneContentDao {

	public Integer index(IndexWriter writer, Integer siteId, Integer channelId, Date startDate, Date endDate, Integer startId, Integer max)
			throws CorruptIndexException, IOException;
}
