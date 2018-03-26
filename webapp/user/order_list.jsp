<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

			<%@ include file="/user/header.jsp" %>


		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>共${pb.count }个订单
					<table class="table table-bordered">
					<c:forEach var="order" items="${pb.list }">
						<tbody>
							<tr class="success">
								<th colspan="5">订单编号:${order.oid } 
								<c:if test="${order.state==0 }"><a href="orderServlet?method=payOrderByOid&oid=${order.oid }">未付款</a></c:if>
								<c:if test="${order.state==1 }">已发货</c:if>
								<c:if test="${order.state==2 }">已收货</c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach var="orderitem" items="${order.orderitem }">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${orderitem.p.pimage }" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${orderitem.p.pname }</a>
								</td>
								<td width="20%">
									￥${orderitem.p.shop_price }
								</td>
								<td width="10%">
									${orderitem.count }
								</td>
								<td width="15%">
									<span class="subtotal">￥${orderitem.count * orderitem.p.shop_price }</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<li <c:if test="${pb.currentPage==1 }">class="disabled"</c:if>><a href="orderServlet?method=findOrderByPage&currentPage=${pb.currentPage==1?1:pb.currentPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
					<c:forEach var="i" begin="1" end="${pb.totalPage }">
						<c:if test="${i==pb.currentPage }">
							<li class="active"><a href="javascript:void(0)">${i }</a></li>
						</c:if>
						<c:if test="${i!=pb.currentPage }">
							<li ><a href="orderServlet?method=findOrderByPage&currentPage=${i }">${i }</a></li>
						</c:if>
					</c:forEach>
					<li <c:if test="${pb.currentPage==pb.totalPage }">class="disabled"</c:if>>
						<a href="orderServlet?method=findOrderByPage&currentPage=${pb.currentPage==pb.totalPage?pb.totalPage:pb.currentPage+1 }" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
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