package com.product.dao.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.product.dao.OrderDao;
import com.product.domain.Order;
import com.product.domain.Orderitem;
import com.product.domain.Product;
import com.product.util.C3P0util;
import com.product.util.ConnectionManager;

public class OrderDaoEntity implements OrderDao {

	@Override
	public void inserOrder(Order o) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
		qr.update(ConnectionManager.getConnection(),sql,o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getUid());
	}

	@Override
	public void inserOrderitem(Orderitem oi) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values (?,?,?,?)";
		qr.update(ConnectionManager.getConnection(),sql,oi.getItemid(),oi.getCount(),oi.getPid(),oi.getOid());
		
	}

	@Override
	public List<Order> findOrderByUid(String uid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		//首先根据uid查出所有的order订单对象
		String sql = "select * from orders where uid=?";
		List<Order> orderlist = qr.query(sql, new BeanListHandler<>(Order.class),uid);
		//因为表数据中没有order类中的属性orderitem  所以根据两表联查获得orderitem对象的集合
		sql = "SELECT oi.itemid,oi.count,oi.pid,oi.oid FROM orders o, orderitem oi WHERE o.oid=oi.oid and o.oid=?";
		for (Order order : orderlist) {
			//得到orderitem对象集合   
			List<Orderitem> orderitems = qr.query(sql, new BeanListHandler<>(Orderitem.class),order.getOid());
			//因为前台需要Product的数据 所以根据orderitem对象的pid查出Product对象
			for (Orderitem oi : orderitems) {
				//根据orderitem对象得到pid
				String pid = oi.getPid();
				//根据pid查出Product对象
				Product q = qr.query("select * from product where pid=?", new BeanHandler<>(Product.class),pid);
				//将Product对象设置到orderitem对象中
				oi.setP(q);
			}
			//将数据封装好的orderitem对象集合放入order对象中
			order.setOrderitem(orderitems);
		}
		return orderlist;
	}

	@Override
	public int findAllOrder(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "select count(*) from orders where 1=1 ";
		List list = new ArrayList<>();
		if(uid!=null){
			sql += " and  uid=?";
			list.add(uid);
		}
		Long l = (Long) qr.query(sql, new ScalarHandler(),list.toArray());
		return l.intValue();
	}

	@Override
	public List<Order> findOrderPage(String uid, int currentPage, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		//首先根据uid查出所有的order订单对象
		String sql = "select * from orders where 1=1 ";
//		List<Order> orderlist = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currentPage-1)*pageSize,pageSize);
		List list = new ArrayList<>();
		if(uid!=null){
			sql += " and  uid=?";
			list.add(uid);
		}
		sql +=" LIMIT ?,?";
		list.add((currentPage-1)*pageSize);
		list.add(pageSize);
		List<Order> orderlist = qr.query(sql,new BeanListHandler<Order>(Order.class),list.toArray());
		//因为表数据中没有order类中的属性orderitem  所以根据两表联查获得orderitem对象的集合
		sql = "SELECT oi.itemid,oi.count,oi.pid,oi.oid FROM orders o, orderitem oi WHERE o.oid=oi.oid and o.oid=?";
		for (Order order : orderlist) {
			//得到orderitem对象集合   
			List<Orderitem> orderitems = qr.query(sql, new BeanListHandler<>(Orderitem.class),order.getOid());
			//因为前台需要Product的数据 所以根据orderitem对象的pid查出Product对象
			for (Orderitem oi : orderitems) {
				//根据orderitem对象得到pid
				String pid = oi.getPid();
				//根据pid查出Product对象
				Product q = qr.query("select * from product where pid=?", new BeanHandler<>(Product.class),pid);
				//将Product对象设置到orderitem对象中
				oi.setP(q);
			}
			//将数据封装好的orderitem对象集合放入order对象中
			order.setOrderitem(orderitems);
		}
		return orderlist;
	}

	@Override
	public Order payOrderByOid(String oid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order o = qr.query(sql, new BeanHandler<>(Order.class),oid);
		sql = "SELECT oi.itemid,oi.count,oi.pid,oi.oid FROM orders o, orderitem oi WHERE o.oid=oi.oid and o.oid=?";
		List<Orderitem> orderitems = qr.query(sql, new BeanListHandler<>(Orderitem.class),o.getOid());
		//因为前台需要Product的数据 所以根据orderitem对象的pid查出Product对象
		for (Orderitem oi : orderitems) {
			//根据orderitem对象得到pid
			String pid = oi.getPid();
			//根据pid查出Product对象
			Product q = qr.query("select * from product where pid=?", new BeanHandler<>(Product.class),pid);
			//将Product对象设置到orderitem对象中
			oi.setP(q);
		}
		//将数据封装好的orderitem对象集合放入order对象中
		o.setOrderitem(orderitems);
		return o;
	}

	@Override
	public void update(Order o) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "UPDATE orders SET ordertime=?,total=?,state=?,address=?,NAME=?,telephone=?,uid=? WHERE oid=?";
		qr.update(sql, o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getUid(),o.getOid());
	}
}

