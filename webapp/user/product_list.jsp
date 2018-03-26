<%@page import="com.product.domain.Product"%>
<%@page import="com.product.service.ProductService"%>
<%@page import="com.product.service.entity.ProductServiceEntity"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>

	<head>
	<base href="${pageContext.request.contextPath }/">
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
	<%@ include file="/user/header.jsp" %>


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
				</ol>
			</div>
<c:forEach var="p" items="${pb.list }">
			<div class="col-md-2">
				<a href="product_info.htm">
					<img src="${p.pimage }" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="productServlet?method=findProductById&pid=${p.pid }" style='color:green' title="${p.pname }">${fn:substring(p.pname,0,12) }...</a></p>
				<p><font color="#FF0000">商城价：&yen;${p.shop_price }</font></p>
			</div>
</c:forEach>
			

		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
			
				<li <c:if test="${pb.currentPage==1 }">class="disabled"</c:if>><a href="productServlet?method=findByPage&cid=${cid }&currentPage=${pb.currentPage==1?1:pb.currentPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				
				
				
				<!-- 无敌分页 -->
				<c:if test="${pb.currentPage>2 }"><li ><a href="#">...</a></li></c:if>
				<c:forEach var="i" begin="${pb.currentPage-1 }" end="${pb.currentPage+1 }">
					<%-- <c:if test="${pb.currentPage-1==i }">hidden="hidden"</c:if> --%>
					<li <c:if test="${pb.currentPage==i }">class="active"</c:if>>
						<a <c:if test="${i==pb.totalPage+1 }">hidden="hidden"</c:if>
							<c:if test="${i==0 }">hidden="hidden"</c:if>
							href="productServlet?method=findByPage&cid=${cid }&currentPage=${i }">
							${i }
						</a>
					</li>
				</c:forEach>
				<c:if test="${pb.currentPage<pb.totalPage-1 }"><li ><a href="#">...</a></li></c:if>
				
				
				
				
				
				<li <c:if test="${pb.currentPage==pb.totalPage }">class="disabled"</c:if>>
					<a href="productServlet?method=findByPage&cid=${cid }&currentPage=${pb.currentPage==pb.totalPage? pb.totalPage:pb.currentPage+1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">
				<ul style="list-style: none;">
<%
	Cookie[] cookies = request.getCookies();
	Cookie history = null;
	for(int i=0;cookies!=null&&i<cookies.length;i++){
		if("history".equals(cookies[i].getName())){
			history = cookies[i];
			break;
		}
	}
	if(history==null){
		out.print("<h1>您还没有浏览过商品！</h1>");
	}else{
		String[] ids = history.getValue().split("-");
		ProductService ps = new ProductServiceEntity();
		for(String id : ids){
			Product p = ps.findProductById(id);
			%>
<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="<%=p.getPimage() %>" width="130px" height="130px" /></li>
			<%
		}
	}
%>
				</ul>

			</div>
		</div>
		<div style="margin-top:50px;">
			<img src="./image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>

</html>