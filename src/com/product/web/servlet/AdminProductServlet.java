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
		//����ҳ��
		String curr = req.getParameter("page");
		if(curr!=null){
			currentPage = Integer.parseInt(curr);
		}
		//����ÿҳ��ʾ����
		String pSize = req.getParameter("rows");
		if(pSize!=null){
			pageSize = Integer.parseInt(pSize);
		}
		ProductService ps = new ProductServiceEntity();
		PageBean<Product> pb = ps.findByPage(null, currentPage, pageSize);
		
		//{"total":51,"rows":[{pid:"1001",pname:"iphonex"}��{pid:"1002",pname:"��Ϊ"}]}
		
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
			
			//��װ�������
			Product p = new Product();
			Map<String, Object> map = new HashMap<>();
			
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List<FileItem> fileItems = upload.parseRequest(req);
			
			for (FileItem fileItem : fileItems) {
				if(fileItem.isFormField()){ //��ͨ����
					String name = fileItem.getFieldName(); //�õ�name����ֵ
					String value = fileItem.getString("UTF-8"); //�õ�value����ֵ
					map.put(name, value);
				}else{ //�ļ��ϴ����� 
					String name = fileItem.getFieldName(); //�õ�name����ֵ
					String value = fileItem.getName();  //�õ��ļ���
					
					String fileName = UUID.randomUUID().toString().replace("-", "")+"."+FilenameUtils.getExtension(value);
					map.put(name, "products/1/"+fileName);
					
					//����Ŀ¼����
					String realPath = this.getServletContext().getRealPath("/products/1");
					File storeDir = new File(realPath);
					if(!storeDir.exists()){
						storeDir.mkdirs();
					}
					//�����ļ�����
					File file = new File(storeDir, fileName);
					//д�����
					fileItem.write(file);
					//ɾ����ʱ�ļ�
					fileItem.delete();
				}
			}
			
			BeanUtils.populate(p, map);
			p.setPid(UUID.randomUUID().toString().replace("-", ""));
			p.setPdate(new Date().toLocaleString());
			//����ҵ��
			ProductService ps = new ProductServiceEntity();
			ps.addProduct(p);
			
			//��Ӧ����
			Map<String, Object> msgMap = new HashMap<>();
			msgMap.put("msg", "��ӳɹ�");
			
			String json = JSONObject.fromObject(msgMap).toString();
			resp.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
