package com.ideabobo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.ideabobo.util.*;
import com.ideabobo.util.wxlogin.WxLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ideabobo.model.Dbservice;
import com.ideabobo.model.Dbtablemapping;
import com.ideabobo.service.DatabaseService;

@CrossOrigin(origins="*")
@Controller
@RequestMapping(value = "/database")
public class DatabaseController {

    @Autowired
	private DatabaseService databaseService;

	/**
	 * 获取数据库某个表的列表数据
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public List<Map<String,Object>> list(HttpServletRequest req) {
		//实例化一个Dbservice对象来操作数据库
    	Dbservice dbm = new Dbservice(databaseService);
    	//获取传输过来的表名然后通过表前缀转换成真实的表名
    	String table = Dbservice.getTableName(req.getParameter("table"));
    	//通过表明实例话一个数据表对象
    	Object tableObj = Dbtablemapping.getModelByTable(table);
    	if (tableObj==null){
    		return null;
		}
    	//用前端传过来的数据来填充数据表对象
    	Object objectObj = Common.getByRequest(tableObj, req, false);
    	//Robj robj = new Robj();
		//定义一个空的列表用来接收数据库查询的数据
    	List<Map<String,Object>> list = null;
    	try {
    		//准备排序语句
			String ordersql = null;
			String sort = req.getParameter("sort");
			String order = req.getParameter("order");
			//用前端传过来的参数组装排序语句
			if (StringUtil.isNotNullOrEmpty(order)&&StringUtil.isNotNullOrEmpty(sort)){
				ordersql = " order by "+sort+" "+order;
			}
    		//通过数据库实例,和数据表对象来组装的到一个sql语句
			String sql = dbm.list(table,objectObj,ordersql);
			//通过执行sql语句得到数据集
			list = databaseService.find(sql);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	//把数据集返回到前端
        return list;
    }

	/**
	 * 调用list接口一致只是返回方式不一样
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String listJ(HttpServletRequest req) {
    	List<Map<String,Object>> list = list(req);
        return renderJsonp(list, req);
    }

	/**
	 *查询一个数据库集,操作方式和list接口一直,区别在于这里返回的是单个对象
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/find", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public Map<String,Object> find(HttpServletRequest req) {
    	Dbservice dbm = new Dbservice(databaseService);
    	String table = Dbservice.getTableName(req.getParameter("table"));
    	Object objectObj = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
    	//Robj robj = new Robj();
    	List<Map<String,Object>> list = null;
    	try {
			String sql = dbm.list(table,objectObj,true);
			list = databaseService.find(sql);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	if (list!=null && list.size()>0) {
    		return list.get(0);
		}else{
			return null;
		}
        
    }

	
	/**
	 * 调用findj通过jsonp返回给客户端
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String findJ(HttpServletRequest req) {
		return renderJsonp(find(req), req);
    }

	/**
	 * 分页查询数据
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listPage", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public Page listPage(HttpServletRequest req) {
		//实例化一个Dbservice对象来操作数据库
    	Dbservice dbm = new Dbservice(databaseService);
		//获取传输过来的表名然后通过表前缀转换成真实的表名
    	String table = Dbservice.getTableName(req.getParameter("table"));
    	//实例化一个page对象
    	Page page = new Page();
    	//Robj robj = new Robj();
    	try {
			//通过表明实例话一个数据表对象
    		Object model = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
    		page.model = model;
    		//获取排序字段
    		String order = req.getParameter("order");
    		//获取排序方式
			String sort = req.getParameter("sort");
			//获取显示第几页
			String pageNo = req.getParameter("page");
			//获取每页显示多少数据
			String pageSize = req.getParameter("rows");
			if (pageSize==null || pageSize.equals("")){
				pageSize = req.getParameter("limit");
			}
			//调用数据库工具返回page对象
			page = dbm.getByPage(page, table,sort,order,pageNo,pageSize);
	    	//robj.setData(page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}  	
        return page;
    }

	/**
	 * 通过sql语句返回分页对象
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listPageSql", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Page listPageSql(HttpServletRequest req) {
		//实例化一个Dbservice对象来操作数据库
		Dbservice dbm = new Dbservice(databaseService);
		//获取前端传过来的查询语句
		String sql = req.getParameter("sql");
		//实例化一个page对象
		Page page = new Page();
		//Robj robj = new Robj();
		try {
			page.model = "sql";
			//获取排序字段
			String order = req.getParameter("order");
			//获取排序方式
			String sort = req.getParameter("sort");
			//获取显示第几页
			String pageNo = req.getParameter("page");
			//获取每页显示多少数据
			String pageSize = req.getParameter("rows");
			if (pageSize==null || pageSize.equals("")){
				pageSize = req.getParameter("limit");
			}
			//调用数据库工具返回page对象
			page = dbm.getByPageSql(page, sql,sort,order,pageNo,pageSize);
			//robj.setData(page);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 通listPage返回jsonp数据
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listPageJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String listPageJ(HttpServletRequest req) {
    	Dbservice dbm = new Dbservice(databaseService);
    	String table = Dbservice.getTableName(req.getParameter("table"));
    	Page page = new Page();
    	//Robj robj = new Robj();
    	try {
    		Object model = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
    		page.model = model;
			page = dbm.getByPage(page, table,null,null,null,null);
	    	//robj.setData(page);
		} catch (Exception e) {
			e.printStackTrace();
		}  	
        return renderJsonp(page, req);
    }

	/**
	 * 保存数据的同上传文件
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveWithFile", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String saveWithFile(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
    	Dbservice dbm = new Dbservice(databaseService);
		String rstr = "0";
    	try {
    		//判断文件域里面是否又文件,有文件就上传然后得到文件名
    		String fileNames=null;
    		if(files!=null && files.length>0){
    			long fn = files[0].getSize();
    			if(fn>1){
    				fileNames = Common.copyFile2Upload(files);
    			}
    			
    		}
    		String tableReq = req.getParameter("table");
    		String fileField = req.getParameter("fileName");
			//获取传输过来的表名然后通过表前缀转换成真实的表名
        	String table = Dbservice.getTableName(tableReq);
			//通过表明实例话一个数据表对象
        	Object model = Common.getByRequestSetfile(Dbtablemapping.getModelByTable(table),fileField,fileNames, req, false);
			//通过数据库实例,和数据表对象来组装的到一个sql语句
    		String sql = dbm.save(model, table);
    		//执行sql语句操作数据库
    		databaseService.executeAction(sql);
			/**
			 * 判断是否插入操作,如果是返回插入的id
			 */
			String id = req.getParameter("id");

			if(id==null || id.equals("")){
				List<Map<String, Object>> rlist = databaseService.find("select LAST_INSERT_ID() as lastId");
				if(rlist!=null){
					rstr = rlist.get(0).get("lastId")+"";
				}
			}else{
				rstr = id;
			}
		} catch (Exception e) {
			
			return "操作失败"+e.getMessage();
		}
    	
    	return rstr;
        
    }

	/**
	 * 上传文件
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/upload", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.OPTIONS })
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
    	String fileNames=null;
    	try {
    		if(files!=null && files.length>0){
    			fileNames = Common.copyFile2Upload(files);
    		}
    		
		} catch (Exception e) {
		}
    	return fileNames;
        
    }

	/**
	 * 按照编辑器的格式上传视频
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editorUploadVideo", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String editorUploadVideo(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
		String fileNames=null;
		String jsonobj = null;
		try {
			if(files!=null && files.length>0){
				fileNames = Common.copyFile2Upload(files);
				ArrayList<String> nl = new ArrayList<String>();
				String[] fns = fileNames.split(",");
				String url = "upload/"+fns[0];
				jsonobj = "{\"errno\":0,\"data\":{\"url\":\""+url+"\"}}";
			}

		} catch (Exception e) {
		}
		return jsonobj;

	}
	/**
	 * 按照编辑器的格式上传视频
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editorUploadVideoJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String editorUploadVideoJ(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
		String fileNames=null;
		String jsonobj = null;
		try {
			if(files!=null && files.length>0){
				fileNames = Common.copyFile2Upload(files);
				ArrayList<String> nl = new ArrayList<String>();
				String[] fns = fileNames.split(",");
				String url = fns[0];
				jsonobj = "{\"errno\":0,\"data\":{\"url\":\""+url+"\"}}";
			}

		} catch (Exception e) {
		}
		return jsonobj;

	}

	/**
	 * 按照编辑器的格式上传图片
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editorUpload", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String editorUpload(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
		String fileNames=null;
		EditorUpload eu = new EditorUpload();
		eu.setErrno(0);
		try {
			if(files!=null && files.length>0){
				fileNames = Common.copyFile2Upload(files);
				ArrayList<String> nl = new ArrayList<String>();
				String[] fns = fileNames.split(",");
				for (int i = 0; i < fns.length; i++) {
					nl.add("upload/"+fns[i]);
				}
				eu.setData(nl);
			}

		} catch (Exception e) {
		}
		return JSON.toJSONString(eu);

	}
	/**
	 * 按照编辑器的格式上传图片
	 * @param files
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editorUploadJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String editorUploadJ(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest req) {
		String fileNames=null;
		EditorUpload eu = new EditorUpload();
		eu.setErrno(0);
		try {
			if(files!=null && files.length>0){
				fileNames = Common.copyFile2Upload(files);
				ArrayList<String> nl = new ArrayList<String>();
				String[] fns = fileNames.split(",");
				for (int i = 0; i < fns.length; i++) {
					nl.add(fns[i]);
				}
				eu.setData(nl);
			}

		} catch (Exception e) {
		}
		return JSON.toJSONString(eu);

	}

	/**
	 * 工具方法,可以生成二维码
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/createQrcode", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String createQrcode(HttpServletRequest req) {
    	String fileNames="";
    	try {
    		String path = ResourceUtils.getURL("classpath:").getPath();
            String destPath = path+File.separator+"static"+File.separator+"upload"+File.separator;
            String content = req.getParameter("code");
        	fileNames=QRCodeUtil.encode(content, null, destPath, true);
		} catch (Exception e) {
			
		}
    	return fileNames;
        
    }
	/**
	 * 工具方法,可以生成二维码
	 * @param req
	 * @return
	 */
    @RequestMapping(value = "/createQrcodeJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String createQrcodeJ(HttpServletRequest req) {
    	String fileNames="";
    	try {
    		String path = ResourceUtils.getURL("classpath:").getPath();
            String destPath = path+File.separator+"static"+File.separator+"upload"+File.separator;
            String content = req.getParameter("code");
        	fileNames=QRCodeUtil.encode(content, null, destPath, true);
		} catch (Exception e) {
			
		}
    	return renderJsonpString(fileNames, req);
    }

	/**
	 * 返回jsonp格式数据字符串
	 * @param str
	 * @param req
	 * @return
	 */
    private String renderJsonpString(String str,HttpServletRequest req){
    	Map<String,String> obj = new HashMap<>();
		obj.put("info", str);
		String callbackFunctionName = req.getParameter("callback");	
		String json = JSON.toJSONString(obj);
		String r = callbackFunctionName+"("+json+")";
		return r;
    }

	/**
	 * 返回jsonp格式对象
	 * @param obj
	 * @param req
	 * @return
	 */
	private String renderJsonp(Object obj,HttpServletRequest req){
		String callbackFunctionName = req.getParameter("callback");	
		String json = JSON.toJSONString(obj);
		String r = callbackFunctionName+"("+json+")";
		return r;
    }

	/**
	 * 执行更新的sql语句
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateSqlJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String updateSqlJ(HttpServletRequest req) {
    	String sql = req.getParameter("sql");
    	try {
    		databaseService.executeAction(sql);
		} catch (Exception e) {
			
			return renderJsonpString("0", req);
		}
    	
    	return renderJsonpString("1", req);
        
    }


	/**
	 * 执行查询的sql语句
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listSqlJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String listSqlJ(HttpServletRequest req) {
    	String sql = req.getParameter("sql");
    	List<Map<String,Object>> list = null;
    	try {
			list = databaseService.find(sql);
		} catch (Exception e) {
			
			e.printStackTrace();
		}  	
        return renderJsonp(list, req);
    }
    
    
    @RequestMapping(value = "/updateSql", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String updateSql(HttpServletRequest req) {
    	String sql = req.getParameter("sql");
    	try {
    		databaseService.executeAction(sql);
		} catch (Exception e) {
			
			return "操作失败"+e.getMessage();
		}
    	
    	return "操作成功";
        
    }

	@RequestMapping(value = "/listSql", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<Map<String,Object>> listSql(HttpServletRequest req) {
		String sql = req.getParameter("sql");
		List<Map<String,Object>> list = null;
		try {
			list = databaseService.find(sql);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 保存数据
	 * @param req
	 * @return
	 */
    @RequestMapping(value = "/save", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String save(HttpServletRequest req) {
    	//实例化一个数据库操作工具实例
    	Dbservice dbm = new Dbservice(databaseService);
    	//Robj robj = new Robj();
    	String tableReq = req.getParameter("table");
		//获取传输过来的表名然后通过表前缀转换成真实的表名
    	String table = Dbservice.getTableName(tableReq);
		//通过表明实例化一个数据表对象
    	Object model = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
    	String rstr = "";
    	try {
			//通过数据库实例,和数据表对象来组装的到一个sql语句
    		String sql = dbm.save(model, table);
    		databaseService.executeAction(sql);
			/**
			 * 判断是否插入操作,如果是返回插入的id
			 */
			String id = req.getParameter("id");

			if(id==null || id.equals("")){
				List<Map<String, Object>> rlist = databaseService.find("select LAST_INSERT_ID() as lastId");
				if(rlist!=null){
					rstr = rlist.get(0).get("lastId")+"";
				}
			}else{
				rstr = id;
			}
		} catch (Exception e) {
			
			return "操作失败"+e.getMessage();
		}
    	
    	return rstr;
        
    }

	/**
	 * 通save方法,返回jsonp格式
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String saveJ(HttpServletRequest req) {
    	String rstr = save(req);
    	return renderJsonpString(rstr, req);
    }

    
    @RequestMapping(value = "/delete", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String delete(HttpServletRequest req) {
    	Dbservice dbm = new Dbservice(databaseService);
    	//Robj robj = new Robj();
    	String table = Dbservice.getTableName(req.getParameter("table"));
    	Object model = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
		try {
    		String sql = dbm.delete(table,model);
    		databaseService.executeAction(sql);
		} catch (Exception e) {
			return "操作失败"+e.getMessage();
		}
    	
    	return "操作成功";
    }

    @RequestMapping(value = "/deleteJ", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
    @ResponseBody
    public String deleteJ(HttpServletRequest req) {
    	delete(req);
    	return renderJsonpString("1", req);
    }

	/**
	 * 获取登录的用户信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest req) {
        Dbservice dbm = new Dbservice(databaseService);
        String table = Dbservice.getTableName("user");
        Object objectObj = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
        //Robj robj = new Robj();
        List<Map<String, Object>> list = null;
        try {
            String sql = dbm.list(table, objectObj, true);
            list = databaseService.find(sql);
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            Map<String, Object> userinfo = list.get(0);
            userinfo.put("roles", userinfo.get("roletype").toString());
            return userinfo;
        } else {
            return null;
        }

    }

	/**
	 * 登录方法
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "application/json; charset=utf-8", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest req) {
        Dbservice dbm = new Dbservice(databaseService);
        String table = Dbservice.getTableName("user");
        Object objectObj = Common.getByRequest(Dbtablemapping.getModelByTable(table), req, false);
        //Robj robj = new Robj();
        List<Map<String, Object>> list = null;
        try {
            String sql = dbm.list(table, objectObj, true);
            list = databaseService.find(sql);
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            Map<String, Object> userinfo = list.get(0);
            userinfo.put("token", userinfo.get("id").toString());
            return userinfo;
        } else {
            return null;
        }

    }

	/**
	 * 注销登录方法
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/logout", produces = "application/json; charset=utf-8", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest req) {
        Map<String, Object> r = new HashMap<>();
        r.put("code", 200);
        return r;
    }

	/**
	 * 小程序调用接口获取电话号码
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getWxPhoneNumber", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getWxPhoneNumber(HttpServletRequest req) {
		// 1.请求微信接口服务，获取accessToken
		JSONObject accessTokenJson = WxLoginUtil.getAccessToken(Common.getProperty("app_id"), Common.getProperty("app_secret"));
		String accessToken = accessTokenJson.get("access_token",String.class);
		// 2.请求微信接口服务，获取用户手机号信息
		String code = req.getParameter("code");
		JSONObject phoneNumberJson = WxLoginUtil.getPhoneNumber(code, accessToken);
		String json = JSON.toJSONString(phoneNumberJson);
		return json;
	}

	/**
	 * 小程序授权登录的方法
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/wxlogin", produces = "text/plain; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String wxlogin(HttpServletRequest req) {
		String appid = Common.getProperty("app_id");
		String app_secret = Common.getProperty("app_secret");
		// 2.请求微信接口服务，获取用户手机号信息
		String code = req.getParameter("code");
		String baseUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+app_secret+"&js_code="+code+"&grant_type=authorization_code";
		String body = HttpUtil.createGet(baseUrl).execute().body();
		return body;
	}

}