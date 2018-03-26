package com.product.web.servlet;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.service.ProductService;
import com.product.service.entity.ProductServiceEntity;
import com.product.web.base.BaseServlet;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPage = 1;
		int pageSize = 6;
		//接收页码
		String curr = req.getParameter("page");
		if(curr!=null){
			currentPage = Integer.parseInt(curr);
		}
		//接收每页显示条数
		String pSize = req.getParameter("rows");
		if(pSize!=null){
			pageSize = Integer.parseInt(pSize);
		}
		ProductService ps = new ProductServiceEntity();
		PageBean<Product> pb = ps.findByPage(null, currentPage, pageSize);
		
		//{"total":51,"rows":[{pid:"1001",pname:"iphonex"}，{pid:"1002",pname:"华为"}]}
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", pb.getCount());
		map.put("rows", pb.getList());
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"cid","market_price","pdate","pdesc","pflag"});
		String json = JSONObject.fromObject(map, jsonConfig ).toString();
		resp.getWriter().write(json);
	}
	public void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			//封装请求参数
			Product p = new Product();
			Map<String, Object> map = new HashMap<>();
			
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List<FileItem> fileItems = upload.parseRequest(req);
			
			for (FileItem fileItem : fileItems) {
				if(fileItem.isFormField()){ //普通表单项
					String name = fileItem.getFieldName(); //得到name属性值
					String value = fileItem.getString("UTF-8"); //得到value属性值
					map.put(name, value);
				}else{ //文件上传表单项 
					String name = fileItem.getFieldName(); //得到name属性值
					String value = fileItem.getName();  //得到文件名
					
					String fileName = UUID.randomUUID().toString().replace("-", "")+"."+FilenameUtils.getExtension(value);
					map.put(name, "products/1/"+fileName);
					
					//创建目录对象
					String realPath = this.getServletContext().getRealPath("/products/1");
					File storeDir = new File(realPath);
					if(!storeDir.exists()){
						storeDir.mkdirs();
					}
					//创建文件对象
					File file = new File(storeDir, fileName);
					//写入磁盘
					fileItem.write(file);
					//删除临时文件
					fileItem.delete();
				}
			}
			
			BeanUtils.populate(p, map);
			p.setPid(UUID.randomUUID().toString().replace("-", ""));
			p.setPdate(new Date().toLocaleString());
			//调用业务
			ProductService ps = new ProductServiceEntity();
			ps.addProduct(p);
			
			//响应数据
			Map<String, Object> msgMap = new HashMap<>();
			msgMap.put("msg", "添加成功");
			
			String json = JSONObject.fromObject(msgMap).toString();
			resp.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
