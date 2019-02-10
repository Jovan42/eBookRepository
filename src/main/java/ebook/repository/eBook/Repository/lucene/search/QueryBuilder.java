package ebook.repository.eBook.Repository.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import ebook.repository.eBook.Repository.lucene.SerbianAnalyzer;

public class QueryBuilder {
	
	private static SerbianAnalyzer analyzer = new SerbianAnalyzer();
	private static int maxEdits = 1;
	
	public static int getMaxEdits(){
		return maxEdits;
	}
	
	public static void setMaxEdits(int maxEdits){
		QueryBuilder.maxEdits = maxEdits;
	}
	
	public static Query buildQuery(String field, String value) throws IllegalArgumentException, ParseException{
		QueryParser parser = new QueryParser(field, analyzer);
		String errorMessage = "";
		if(field == null || field.equals("")){
			errorMessage += "Field not specified";
		}
		if(value == null){
			if(!errorMessage.equals("")) errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if(!errorMessage.equals("")){
			throw new IllegalArgumentException(errorMessage);
		}
		
		Query query = null;
		
        String[] values = value.split(" ");
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        for(String word : values){
            Term term = new Term(field, word);
            builder.add(term);
        }
        query = builder.build();
		
		
		return parser.parse(query.toString(field));
	}

}
