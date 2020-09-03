package com.cm.common.web;

import com.cm.common.json.JacksonUtil;
import com.cm.common.utils.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


@Controller
@ApiIgnore
public class ApiController {

	@Value("${benz.api.url}")
	private String apiUrl;
	@Value("${server.servlet.context-path}")
	private String serverName;
	
	private static final List<String> noTokenList=Arrays.asList("/sys/user/login","/wx/user/login");
	
	@ResponseBody
    @RequestMapping(value = "api-docs", method = RequestMethod.GET, produces = "application/json")
    public JsonNode meg(HttpServletRequest request) throws Exception {
		String url = "http://"+apiUrl+"/"+serverName+"/v2/api-docs";
		System.out.println("ApiController.meg, URL = " + url);
		String json = HttpUtil.get(url);
        return generate(json);
		
    }
	private JsonNode generate(String json) throws Exception{
		JsonNode root = JacksonUtil.get().json2Node(json);
		ObjectNode defin=(ObjectNode)root.get("definitions");
		Iterator<Entry<String, JsonNode>> fields = root.get("paths").fields();
		int i=0;
		while(fields.hasNext()){
			
			Entry<String, JsonNode> entry=fields.next();
			boolean ispage=entry.getKey().toLowerCase().contains("page");
			JsonNode path=entry.getValue().get("post");
			/*if (path.get("").equals("")) {
				
			}*/
			if(path == null){
				path = entry.getValue().get("get");
				if(path == null){
					i++;
					continue;
				}
			}else {
				//处理请求参数
				handleParams(path,defin,i,!noTokenList.contains(entry.getKey()));
				//处理响应结果
				handleResponse(path,defin,ispage,i);
				i++;
			}
			
			
		}
		return root;
	}
	
	private void handleParams(JsonNode path, ObjectNode defin, int x, boolean hasToken)throws Exception{
		ArrayNode parameters = (ArrayNode)path.get("parameters");
		List<String> params=new ArrayList<String>();
		ArrayNode required= JacksonUtil.get().createArrayNode();
		JsonNode node = null;
		if(parameters==null){
			return ;
		}
		for(int i=0;i<parameters.size();i++){
			JsonNode param=parameters.get(i);
			if(param.get("name").asText().startsWith("$")){
				//处理必填和选填
				if(param.get("required").asBoolean()){
					for(String name:param.get("name").asText().substring(1).split(",")){
						if(!"".equals(name)){
							required.add(name);
							params.add(name);
						}
					}
				}else{
					for(String name:param.get("name").asText().substring(1).split(",")){
						if(!"".equals(name)){
							params.add(name);
						}
					}
				}
			}else{
				node = param;
			}
		}
		parameters.removeAll();
		parameters.add(node);
		ObjectNode schemaNode =(ObjectNode)node.get("schema");
		if(schemaNode==null){//todo
			return;
		}
		JsonNode ref1 = schemaNode.get("$ref");
		if(ref1==null){//todo
			return;
		}
		String ref = ref1.asText();
		ObjectNode request = (ObjectNode)defin.get(ref.substring(ref.lastIndexOf("/")+1)).deepCopy();
		request.set("required", required);
		List<String> delete =new ArrayList<String>();
		Iterator<String> fieldNames = request.get("properties").fieldNames();
		while(fieldNames.hasNext()){
			String name = fieldNames.next();
			if(!params.contains(name)){
				delete.add(name);
			}
		}
		for(String name:delete){
			((ObjectNode)request.get("properties")).remove(name);
		}
		String newName="Request"+x;
		defin.set(newName,request);
		schemaNode.put("$ref", "#/definitions/"+newName);
		if(hasToken){
			//添加token node
			parameters.add(createTokenNode());
		}
	}
	
	private void handleResponse(JsonNode path, ObjectNode defin, boolean ispage, int i)throws Exception{
		ObjectNode responsesNode=(ObjectNode)path.get("responses");
		Iterator<Entry<String, JsonNode>> responses=responsesNode.fields();
		List<String> resDelete=new  ArrayList<String>();
		while(responses.hasNext()){
			Entry<String, JsonNode> temp=responses.next();
			ObjectNode response = (ObjectNode)temp.getValue();
			if("200".equals(temp.getKey())){
				ObjectNode schemaNode = (ObjectNode)response.get("schema");
				String newName="Response"+i;
				ObjectNode node = JacksonUtil.get().createNode();
				if(schemaNode.get("$ref")!=null){
					node.put("$ref",schemaNode.get("$ref").asText());
				}else{
					node=schemaNode.deepCopy();
				}
				node.put("description", response.get("description").asText());
				if(ispage){
					defin.set(newName, getPageNode(node));
				}else{
					defin.set(newName, getNode(node));
				}
				schemaNode.removeAll();
				response.put("description", "success");
				schemaNode.put("$ref", "#/definitions/"+newName);
			}else if("404".equals(temp.getKey())){
				//do nothing
			}else{
				resDelete.add(temp.getKey());
			}
		}
		for(String name:resDelete){
			responsesNode.remove(name);
		}
	}
	
	private JsonNode getNode(JsonNode body) throws Exception{
		ObjectNode node = JacksonUtil.get().createNode();
		ObjectNode code = JacksonUtil.get().createNode();
		code.put("type", "integer");
		code.put("format", "int32");
		code.put("description", "响应码");
		code.put("default",200);
		node.set("code", code);
		ObjectNode msg = JacksonUtil.get().createNode();
		msg.put("type", "string");
		msg.put("description", "响应信息");
		node.set("msg", msg);
		node.set("body", body);
		ObjectNode result = JacksonUtil.get().createNode();
		result.put("type", "object");
		result.set("properties", node);
		return result;
	}
	
	private JsonNode getPageNode(JsonNode body)throws Exception{
		ObjectNode node = JacksonUtil.get().createNode();
		
		ObjectNode pageSize = JacksonUtil.get().createNode();
		pageSize.put("type", "integer");
		pageSize.put("format", "int32");
		pageSize.put("description", "每页数量");
		node.set("pageSize", pageSize);
		
		ObjectNode page = JacksonUtil.get().createNode();
		page.put("type", "integer");
		page.put("format", "int32");
		page.put("description", "当前页");
		node.set("page", page);
		
		ObjectNode rowCount = JacksonUtil.get().createNode();
		rowCount.put("type", "integer");
		rowCount.put("format", "int32");
		rowCount.put("description", "总数");
		node.set("rowCount", rowCount);
		
		ObjectNode pageCount = JacksonUtil.get().createNode();
		pageCount.put("type", "integer");
		pageCount.put("format", "int32");
		pageCount.put("description", "总页数");
		node.set("pageCount", pageCount);
		
		ObjectNode hasPrevious = JacksonUtil.get().createNode();
		hasPrevious.put("type", "boolean");
		hasPrevious.put("description", "是否有上一页");
		node.set("hasPrevious", hasPrevious);
		
		ObjectNode hasNext = JacksonUtil.get().createNode();
		hasNext.put("type", "boolean");
		hasNext.put("description", "是否有下一页");
		node.set("hasNext", hasNext);
		
		ObjectNode data = JacksonUtil.get().createNode();
		data.put("type", "array");
		data.set("description", body.get("description"));
		data.set("items", body.get("items"));
		
		node.set("data", data);
		
		ObjectNode result = JacksonUtil.get().createNode();
		result.put("type", "object");
		result.set("properties", node);
		return getNode(result);
	}
	
	private JsonNode createTokenNode()throws Exception{
		ObjectNode node = JacksonUtil.get().createNode();
		node.put("name", "token");
		node.put("in", "header");
		node.put("type", "string");
		node.put("required", true);
		node.put("description", "token");
		return node;
	}
}
