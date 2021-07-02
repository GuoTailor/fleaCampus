package com.gyh.fleacampus.core.service.lucene

import com.gyh.fleacampus.common.toEpochMilli
import com.gyh.fleacampus.core.model.Post
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.document.*
import org.apache.lucene.document.Document
import org.apache.lucene.index.*
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.lionsoul.jcseg.ISegment
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer
import org.lionsoul.jcseg.dic.DictionaryFactory
import org.lionsoul.jcseg.segmenter.SegmenterConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import kotlin.io.path.Path

/**
 * Created by GYH on 2021/5/31
 */
@Service
class Document {
    @Value("\${fileUploadPath}")
    lateinit var rootPath: String
    val directory: Directory by lazy { FSDirectory.open(Path(rootPath + File.separator + "indedx")) }
    val indexWriter: IndexWriter by lazy {
        val config = SegmenterConfig()
        //创建默认单例词库实现，并且按照config配置加载词库
        val dic = DictionaryFactory.createSingletonDictionary(config)
        // 引入分词器
        val analyzer: Analyzer = JcsegAnalyzer(ISegment.Type.COMPLEX, config, dic)
        // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
        val conf = IndexWriterConfig(analyzer)
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE 会先清空原来数据，再提交新的索引
        conf.openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter(directory, conf)
    }
    val reader: IndexReader by lazy { DirectoryReader.open(directory) }
    val searcher: IndexSearcher by lazy { IndexSearcher(reader) }
    val parser: MultiFieldQueryParser by lazy {
        val config = SegmenterConfig(true)
        //创建默认单例词库实现，并且按照config配置加载词库
        val dic = DictionaryFactory.createSingletonDictionary(config)
        // 引入分词器
        val analyzer: Analyzer = JcsegAnalyzer(ISegment.Type.COMPLEX, config, dic)
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser(arrayOf(Post::type.name, Post::content.name), analyzer)
    }

    fun createDocument(post: Post): Document {
        // 创建文档对象
        val document = Document()
        //StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
        document.add(StoredField(Post::id.name, post.id?.toString() ?: ""))
        document.add(StoredField(Post::createTime.name, post.createTime?.toEpochMilli()?.toString() ?: ""))
        document.add(StringField(Post::type.name, post.type ?: "", Field.Store.NO))
        document.add(TextField(Post::content.name, post.content ?: "", Field.Store.NO))
        return document
    }

    fun createIndex(post: Post) {
        // 创建文档对象
        val document = createDocument(post)
        // 把文档集合交给IndexWriter
        indexWriter.addDocument(document)
        indexWriter.commit()
    }

    fun searchText(query: String, numHits: Int = 30): List<Int> {
        // 获取前十条记录
        val topDocs = searcher.search(parser.parse(query), numHits)
        // 获取总条数
        println("本次搜索共找到" + topDocs.totalHits + "条数据")
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        return topDocs.scoreDocs.map { reader.document(it.doc)[Post::id.name].toInt() }
    }

    fun updateIndex(post: Post) {
        // 创建文档对象
        val document = createDocument(post)
        // 把文档集合交给IndexWriter
        indexWriter.updateDocument(Term(Post::id.name, post.id.toString()), document)
        indexWriter.commit()
    }
    // TODO 更新索引
    // TODO 删除索引

    fun deleteIndex(id: Int) {
        indexWriter.deleteDocuments(Term(Post::id.name, id.toString()))
        indexWriter.commit()
    }
}