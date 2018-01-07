package com.cjy.WechatHome.web.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



class TrieNode{
	private boolean isEnd = false;
	private Map<Character, TrieNode>subNode = new HashMap<>();
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Map<Character, TrieNode> getSubNode() {
		return subNode;
	}
	public void setSubNode(Map<Character, TrieNode> subNode) {
		this.subNode = subNode;
	}
	
	
}
@Service
public class SensitiveService implements InitializingBean{
	private TrieNode rootNode = new TrieNode();
	public void addWord(String word){
		TrieNode now = rootNode;
		for(int i=0; i<word.length(); i++){
			char c = word.charAt(i);
			if(!now.getSubNode().containsKey(c)){
				TrieNode trieNode = new TrieNode();
				now.getSubNode().put(c, trieNode);
				now = trieNode;
			}
			else{
				now = now.getSubNode().get(c);
			}
			if(i==word.length()-1){
				now.setEnd(true);
			}		
		}
	}
	public String doFilter(String content){
		StringBuilder sb = new StringBuilder();
		TrieNode node = rootNode;
		int begin=0;
		int position = 0;
		if(StringUtils.isEmpty(content))
			return content;
		while(position!=content.length()){
			char c = content.charAt(position);
			if(!node.getSubNode().containsKey(c)){
				sb.append(content.substring(begin,position+1));
				begin++;
				position++;
				node = rootNode;
			}
			else if(node.getSubNode().get(c).isEnd()){
				position++;
				begin = position;
				sb.append("***");
			}
			else{
				if(position==content.length()-1)
					sb.append(content.substring(begin,position+1));
				node = node.getSubNode().get(c);
				position++;
			}
		}
		return sb.toString();
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		while(( line = bufferedReader.readLine())!=null){
			addWord(line);
		}
	}
	
//	public static void main(String[] args){
//		SensitiveService service = new SensitiveService();
//		service.addWord("色情");
//		String a = service.filter("你很色情");
//		System.out.println(a);
//	}
}
