package com.example.phonesearcher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregator;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;

public class PhoneService {
	
	Client client;
	
	@SuppressWarnings("resource")
	public PhoneService() {
		
		try {
			
			this.client = new PreBuiltTransportClient(
					Settings.builder().put("client.transport.sniff", true)
									  .put("cluster.name", "elasticsearch").build())
					.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
			
		} catch ( UnknownHostException e) {
			e.printStackTrace();
			System.out.println("The conexion could not be established");
		
		}
	}
	
	public List<PhoneDTO> getData(PhoneDTO phone) {
		
		// Se crea un Map (Clave, valor) con el field y el valor con los campos que el usuario haya decidido utilizar para la búsqueda.
		
		BoolQueryBuilder boolQuery = new BoolQueryBuilder();
		
		Map<String, String> fields = new HashMap<String, String>();
			
		
		if(!phone.brand.equals(""))
			fields.put("brand", phone.brand);
				
		if(!phone.color.equals(""))		
			fields.put("color", phone.color);
			
		if(phone.battery != null)					
			boolQuery.must(QueryBuilders.rangeQuery("battery").gte(phone.battery));
		
		if(!phone.intMemory.equals(""))
			fields.put("intMemory", phone.intMemory.split(" ")[0].substring(0,phone.intMemory.split(" ")[0].length() - 2).concat(" ").concat(phone.intMemory.split(" ")[1]));
		
		if(phone.screenSize != 0.0)			
			boolQuery.must(QueryBuilders.rangeQuery("screenSize").gte(phone.screenSize));
		
		if(!phone.frontCamNum.equals(""))		
			fields.put("frontCamNum", phone.frontCamNum);
			
		if(!phone.backCamNum.equals(""))				
			fields.put("backCamNum", phone.backCamNum);
			
		if(!phone.frontCamRes.equals(""))			
			fields.put("frontCamRes", phone.frontCamRes);
		
		if(!phone.backCamRes.equals(""))		
			fields.put("backCamRes", phone.backCamRes);
		
		if(!phone.ram.equals(""))
			fields.put("ram", phone.ram.split(" ")[0].substring(0,phone.ram.split(" ")[0].length() - 2).concat(" ").concat(phone.ram.split(" ")[1]));
		
		if(phone.price != 0.0) 			
			boolQuery.must(QueryBuilders.rangeQuery("price").lte(phone.price));
		
			
		
				
		
		// Se crea una Query para devolver los móbiles que cumplan las condiciones puestas por el usuario.
		
		
		
		for(Map.Entry<String, String> entry : fields.entrySet()) 
			
			boolQuery.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
		
		
		
		SearchRequestBuilder builder = this.client.prepareSearch("phones");
		builder.setQuery(boolQuery);
		
		builder.setSize(50*phone.more);
		
		List<SearchHit> hits = Arrays.asList(builder.get().getHits().getHits());
		
		
		List<PhoneDTO> results = new ArrayList<PhoneDTO>();
		
		hits.forEach(
				hit -> results.add(JSON.parseObject(hit.getSourceAsString(), PhoneDTO.class)));
		
		
		for ( int i = 0; i < results.size(); i++) {
			
			PhoneDTO p = results.get(i);
			
			//If the price is lower or equal to the one wanted, then the phone is retrieved.
			
			//if(Integer.parseInt(p.getPrice()) <= Integer.parseInt(phone.getPrice()))
			
			System.out.println("Phone " + i + ": " + p.title + ". " + p.battery + ". " +  p.screenSize + ". " + p.model);
		}
		
		this.client.close();
		return results;
	}

	public List<String> findField(String field) {
		List<String> values = new LinkedList<>();
		
		TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder(field + "_Agg", ValueType.STRING);
        aggregationBuilder.field(field);
        aggregationBuilder.size(200);

        QueryBuilder query = QueryBuilders.matchAllQuery();

        SearchResponse response = client.prepareSearch("phones")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query).addAggregation(aggregationBuilder).setSize(600).get();
        if (response == null) return null;
        
        StringTerms terms = ((StringTerms) response.getAggregations().get(field + "_Agg"));
        
        
        if (terms != null) {

            List<StringTerms.Bucket> buckets = terms.getBuckets();
            if (buckets != null && buckets.size() > 0) {
            	

                for (Terms.Bucket bucket : buckets) {
                	
                	if (!bucket.getKeyAsString().isEmpty()) {
                		
                		values.add(bucket.getKeyAsString());

                	}
                }
            }
            if (field == "intMemory" || field == "ram")
            	values = sortBytes(values);
            return values;
        }
        return null;
	}

	private List<String> sortBytes(List<String> values) {
		List<Float> mbValues = new ArrayList<Float>();
		List<Float> gbValues = new ArrayList<Float>();
		List<Float> tbValues = new ArrayList<Float>();
		
		for (String value : values) {
			
			float num = Float.parseFloat(value.split(" ")[0]);
			String mem = value.split(" ")[1];
			
			if (mem.equals("MB"))
				mbValues.add(num);
			
			else if (mem.equals("GB")) 
					gbValues.add(num);
				
				else if (mem.equals("TB"))
						tbValues.add(num);	
		}
		
		
		Collections.sort(mbValues);
		Collections.sort(gbValues);
		Collections.sort(tbValues);
		
		List<String> mbValuesString = new ArrayList<String>();
		List<String> gbValuesString = new ArrayList<String>();
		List<String> tbValuesString = new ArrayList<String>();
		
		for (Float value : mbValues) {
			mbValuesString.add(Float.toString(value)+" MB");
		}
		
		for (Float value : gbValues) {
			gbValuesString.add(Float.toString(value)+" GB");
		}
		
		for (Float value : tbValues) {
			tbValuesString.add(Float.toString(value)+" TB");
		}
		
		List<String> results = new ArrayList<String>();
		
		results.addAll(mbValuesString);
		results.addAll(gbValuesString);
		results.addAll(tbValuesString);
		
		return results;
	}
	
}
