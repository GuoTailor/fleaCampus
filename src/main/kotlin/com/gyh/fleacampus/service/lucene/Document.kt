package com.gyh.fleacampus.service.lucene

import com.gyh.fleacampus.model.Post
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
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
    val indexPath = File.separator + "indedx"
    val directory: Directory by lazy { FSDirectory.open(Path(rootPath + indexPath)) }
    val indexWriter by lazy {
        val config = SegmenterConfig()
        //创建默认单例词库实现，并且按照config配置加载词库
        val dic = DictionaryFactory.createSingletonDictionary(config)
        // 引入分词器
        val analyzer: Analyzer = JcsegAnalyzer(ISegment.Type.COMPLEX, config, dic)
        // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
        val conf = IndexWriterConfig(analyzer)
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.openMode = IndexWriterConfig.OpenMode.CREATE
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter(directory, conf)
    }
    val reader: IndexReader by lazy { DirectoryReader.open(directory); }
    val searcher: IndexSearcher by lazy { IndexSearcher(reader) }
    val parser: MultiFieldQueryParser by lazy {
        val config = SegmenterConfig(true)
        //创建默认单例词库实现，并且按照config配置加载词库
        val dic = DictionaryFactory.createSingletonDictionary(config)
        // 引入分词器
        val analyzer: Analyzer = JcsegAnalyzer(ISegment.Type.COMPLEX, config, dic)
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser(arrayOf(Post::title.name, Post::content.name), analyzer)
    }

    fun createIndex(post: Post) {
        // 创建文档对象
        val document = Document()
        //StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
        document.add(TextField(Post::title.name, post.title, Field.Store.YES))
        document.add(TextField(Post::content.name, post.content, Field.Store.YES))
        // 把文档集合交给IndexWriter
        indexWriter.addDocument(document)
    }

    fun searchText(query: String) {
        // 获取前十条记录
        val topDocs = searcher.search(parser.parse(query), 100)
        // 获取总条数
        println("本次搜索共找到" + topDocs.totalHits + "条数据")
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        val scoreDocs = topDocs.scoreDocs
        for (scoreDoc in scoreDocs) {
            // 取出文档编号
            val docID = scoreDoc.doc
            // 根据编号去找文档
            val doc = reader.document(docID)
            println(doc["id"] + " " + doc["title"] + " ---- " + doc["descs"])
        }
    }
    //TODO 删除索引
}